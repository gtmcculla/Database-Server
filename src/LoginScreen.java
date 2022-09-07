import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class LoginScreen {
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel mainPanel;
    private JTextField usernameTF;
    private JPasswordField passwordTF;
    private JButton loginButton;
    private JButton signUpButton;
    private Client client;

    private OrderViewController orderViewController;
    private ProductViewController productViewController;

    public LoginScreen(Client client){
        this.client = client;

        // time to code what the login button does
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Product product = new Product();
//
//                product.setProductID(Integer.parseInt(productIDTF.getText()));
//                product.setName(productNameTF.getText());
//                product.setPrice(Double.parseDouble(productPriceTF.getText()));
//                product.setQuantity(Double.parseDouble(productQuantityTF.getText()));
//
//                Gson gson = new Gson();
//
//                String productString = gson.toJson(product);
//
//                Message message = new Message(Message.SAVE_PRODUCT, productString);
//                client.sendMessage(message);

                String username = usernameTF.getText();
                // TODO: encode and not just string
                String password = new String(passwordTF.getPassword());
                Account account = new Account(username, password);

                if(usernameTF.getText().equals("admin")){
                    //this.productViewController = new ProductViewController(this);
                    System.out.println("MANAGER VIEW!!!");
                    System.out.println(usernameTF.getText());
//                    loginButton.addActionListener(new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            JFrame frame = new JFrame("Manager View");
//                            frame.setContentPane(orderViewController.getMainPanel());
//                            frame.setMinimumSize(new Dimension(800, 400));
//                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                            frame.pack();
//                            frame.setVisible(true);
//                        }
//                    });
                }
                else{
                    //this.orderViewController = new OrderViewController(this);
                    System.out.println("CUSTOMER VIEW!!!");
                    System.out.println(usernameTF.
                            getText());
//                    loginButton.addActionListener(new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            JFrame frame = new JFrame("Customer View");
//                            frame.setContentPane(orderViewController.getMainPanel());
//                            frame.setMinimumSize(new Dimension(800, 400));
//                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                            frame.pack();
//                            frame.setVisible(true);
//                        }
//                    });
                }

                Gson gson = new Gson();

                String accountString = gson.toJson(account);
                Message message = new Message(Message.LOGIN, accountString);
                client.sendMessage(message);

            }
        });
    }
}
