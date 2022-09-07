import com.google.gson.Gson;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Base64;

public class Client {
    private JTextArea messageTextArea;
    private JPanel mainPanel;
    private JButton manageInfoButton;
    private JButton manageOrderButton;
    private JButton createOrderButton;
    private JButton searchProductButton;


    private SecretKey secretKey;

    private byte[] initializationVector;


    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private Gson gson;

    private Worker worker;

    private ProductViewController productViewController;
    private productCustomerView productCustomerView;
    private LoginScreen loginScreen;
    private CustomerViewController customerViewController;
    private ManageCustomerInfoViewController manageCustomerInfoViewController;
    private OrderViewController orderViewController;


    /* New code for the final project*/

    private LoginViewController loginViewController;
    private CreateNewOrderViewController createNewOrderViewController;
    private JFrame mainFrame;
    private User user;

    public Client() {
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 12002);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // send the secret key
            secretKey = KeyService.createAESKey();

            String keyString = KeyService.convertSecretKeyToString(secretKey);

            dataOutputStream.writeUTF(keyString);

            // send the initialization vector

            initializationVector = KeyService.createInitializationVector();

            String vectorString = Base64.getEncoder().encodeToString(initializationVector);

            dataOutputStream.writeUTF(vectorString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }





        gson = new Gson();

        worker = new Worker();
        Thread workerThread = new Thread(worker);
        workerThread.start();

        this.createNewOrderViewController = new CreateNewOrderViewController(this);
        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Create Order");
                frame.setContentPane(createNewOrderViewController.getMainPanel());
                frame.setMinimumSize(new Dimension(800, 400));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

        this.orderViewController = new OrderViewController(this);
        manageOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Create Order");
                frame.setContentPane(orderViewController.getMainPanel());
                frame.setMinimumSize(new Dimension(800, 400));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

        this.manageCustomerInfoViewController = new ManageCustomerInfoViewController(this);
        manageInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Customer Info");
                frame.setContentPane(manageCustomerInfoViewController.getMainPanel());
                frame.setMinimumSize(new Dimension(800, 500));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        this.productCustomerView = new productCustomerView(this);
        searchProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Customer Info");
                frame.setContentPane(productCustomerView.getMainPanel());
                frame.setMinimumSize(new Dimension(800, 500));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        // TODO:
        // 1. search products - assignment 2
        // 2. manage user information  - the copy of ProductViewCotroller
        // 3. order history
        // + display a list of all the order associate with the userid
        // + similar to of OrderViewController
        // + cancel order


        this.loginViewController = new LoginViewController(this);
        this.mainFrame = new JFrame("Main Window");
    }

    public void sendMessage(Message message) {

        String str = gson.toJson(message);
        try {

            // Encrypting the message
            // using the symmetric key
            byte[] cipherText
                    = KeyService.do_AESEncryption(
                    str,
                    secretKey,
                    initializationVector);

            String cipherTextString = Base64.getEncoder().encodeToString(cipherText);

            dataOutputStream.writeUTF(cipherTextString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public LoginViewController getLoginViewController() {
        return loginViewController;
    }

    public void setLoginViewController(LoginViewController loginViewController) {
        this.loginViewController = loginViewController;
    }

    public User getUser() {
        return user;
    }


    private class Worker implements Runnable {

        @Override
        public void run() {
            while (true) {
                String replyString = null;
                try {
                    replyString = dataInputStream.readUTF();

                    byte[] decode = Base64.getDecoder().decode(replyString);

                    replyString
                            = KeyService.do_AESDecryption(
                            decode,
                            secretKey,
                            initializationVector);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Message message = gson.fromJson(replyString, Message.class);

                processMessage(message);

            }
        }
    }

    private void processMessage(Message message) {
        messageTextArea.append(gson.toJson(message) + "\n");
        switch (message.getId()) {
            case Message.LOAD_PRODUCT_REPLY: {
                Product product = gson.fromJson(message.getContent(), Product.class);
                productViewController.updateProductInfo(product);
                break;
            }
            case Message.LOAD_PRODUCT_AS_CUSTOMER_REPLY: {
                Product product = gson.fromJson(message.getContent(), Product.class);
                productCustomerView.updateProductInfo(product);
                break;
            }

            case Message.LOAD_PRODUCT_REPLY_TO_ADD: {
                Product product = gson.fromJson(message.getContent(), Product.class);
                createNewOrderViewController.updateOrder(product);
                break;
            }

            case Message.LOAD_CUSTOMER_REPLY: {
                Customer customer = gson.fromJson(message.getContent(), Customer.class);
                customerViewController.updateCustomerInfo(customer);
                break;
            }
            case Message.LOAD_ORDER_REPLY: {
                Order order = gson.fromJson(message.getContent(), Order.class);
                orderViewController.updateOrderInfo(order);
                break;
            }

            case Message.LOGIN_RESPONSE_SUCCESS: {
                User user = gson.fromJson(message.getContent(), User.class);
                System.out.println(user);
                this.user = user;

                if (user.getIsManager()) {
                    this.productViewController = new ProductViewController(this);
                    this.mainFrame.setTitle("Manage Product");
                    this.mainFrame.setContentPane(productViewController.getMainPanel());
                    productViewController.getMainPanel().updateUI();

                } else {
                    this.mainFrame.setTitle("Customer View");
                    this.mainFrame.setContentPane(this.mainPanel);
                    this.mainPanel.updateUI();
                    // 1. Manage User Info (change customer infor, login infor)
                    // 2. Create a new Order (search product, create new order)
                    // 3. Order History (cancel, see, change)
                }
            }
            case Message.CUSTOMER_INFO_REPLY: {
                Customer customer = gson.fromJson(message.getContent(),Customer.class);
                manageCustomerInfoViewController.showCustomerInfo(customer);
                break;
            }

            // TODO: you have to handle the scenario where is user infor in invalid
            default:
                return;
        }

    }

    public static void main(String[] args) {

        Client client = new Client();


        client.getMainFrame().setTitle("Login");
        client.getMainFrame().setContentPane(client.getLoginViewController().getMainPanel());
        client.getMainFrame().setMinimumSize(new Dimension(800, 400));
        client.getMainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.getMainFrame().pack();
        client.getMainFrame().setVisible(true);
    }
}
