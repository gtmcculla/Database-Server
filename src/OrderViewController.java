import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderViewController {
    private JTextField orderIDTF;
    private JTextField orderDateTF;
    private JTextField customerIDTF;
    private JTextField totalCostTF;
    private JTextField totalTaxTF;
    private JButton loadOrderButton;
    private JButton saveOrderButton;
    private JPanel mainPanel;
    private JButton cancelOrderButton;

    private Client client;

    public OrderViewController(Client client) {
        this.client = client;

        loadOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderID = orderIDTF.getText();
                Message message = new Message(Message.LOAD_ORDER, orderID);
                client.sendMessage(message);
            }
        });

        saveOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order order = new Order();

                order.setOrderID(Integer.parseInt(orderIDTF.getText()));
                order.setDate(orderDateTF.getText());
                order.setCustomerID(Integer.parseInt(customerIDTF.getText()));
                order.setTotalCost(Double.parseDouble(totalCostTF.getText()));
                order.setTotalTax(Double.parseDouble(totalTaxTF.getText()));

                Gson gson = new Gson();

                String orderString = gson.toJson(order);

                Message message = new Message(Message.SAVE_ORDER, orderString);
                client.sendMessage(message);
            }
        });

        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderID = orderIDTF.getText();
                Message message = new Message(Message.DELETE_ORDER, orderID);
                client.sendMessage(message);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void updateOrderInfo(Order order) {
        orderIDTF.setText(String.valueOf(order.getOrderID()));
        orderDateTF.setText(order.getDate());
        customerIDTF.setText(String.valueOf(order.getCustomerID()));
        totalCostTF.setText(String.valueOf(order.getTotalCost()));
        totalTaxTF.setText(String.valueOf(order.getTotalTax()));
    }
}
