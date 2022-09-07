import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class CreateNewOrderViewController {
    private final Client client;
    private JPanel mainPanel;
    private JTextField orderIDTF;
    private JLabel OrderID;
    private JTextField productTF;
    private JTextField quantityTF;
    private JButton addProductButton;
    private JList orderLineList;
    private JButton createOrderButton;

    private DefaultListModel<OrderLine> listModel;

    private int orderId;
    private Order order;

    public CreateNewOrderViewController(Client client) {
        this.client = client;

        // this is just a quick way to generate the order id
        this.orderId = new Random().nextInt(100000);
        this.orderIDTF.setText(""+orderId);
        this.orderIDTF.setEditable(false);

        this.order = new Order();
        order.setOrderID(orderId);

        listModel = new DefaultListModel<>();


        this.orderLineList.setModel(listModel);
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productID = productTF.getText();
                Message message = new Message(Message.LOAD_PRODUCT_TO_ADD, productID);
                client.sendMessage(message);
            }
        });

        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double totalTax = order.getTotalCost()*0.09;
                order.setTotalTax(totalTax);
                order.setDate("04/29/22");
                order.setCustomerID(client.getUser().getUserID());

                Message message = new Message(Message.SAVE_ORDER, new Gson().toJson(order));
                client.sendMessage(message);

                for (int i=0; i<listModel.getSize(); i++) {
                    OrderLine element = listModel.getElementAt(i);
                    Message orderLineMessage = new Message(Message.SAVE_ORDER_LINE, new Gson().toJson(element));
                    client.sendMessage(orderLineMessage);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void updateOrder(Product product) {
        int quantity = Integer.parseInt(this.quantityTF.getText());

        // TODO: Check quantity
        double totalPrice = quantity*product.getPrice();

        product.setQuantity(product.getQuantity()-quantity);

        // simple solution
        Message message = new Message(Message.SAVE_PRODUCT, new Gson().toJson(product));
        client.sendMessage(message);

        OrderLine orderLine = new OrderLine();
        orderLine.setOrderID(order.getOrderID());
        orderLine.setQuantity(quantity);
        orderLine.setProductID(product.getProductID());
        orderLine.setCost(totalPrice);
        listModel.addElement(orderLine);
        this.order.setTotalCost(this.order.getTotalCost()+totalPrice);
    }
}
