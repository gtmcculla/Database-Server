import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageCustomerInfoViewController {
    private JPasswordField passwordTF;
    private JTextField paymentTF;
    private JTextField addressTF;
    private JTextField phoneTF;
    private JTextField usernameTF;
    private JTextField emailTF;
    private JButton LOADButton;
    private JButton UPDATEButton;
    private JPanel mainPanel;
    private JTextField fullNameTF;

    private Client client;

    public ManageCustomerInfoViewController(Client client) {
        LOADButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = String.valueOf(passwordTF.getPassword());
                Message message = new Message(Message.CUSTOMER_INFO, password);
                client.sendMessage(message);
            }
        });

        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer changeCustomer = new Customer();
                changeCustomer.setPassword(passwordTF.getText());
                changeCustomer.setUsername(usernameTF.getText());
                changeCustomer.setFullName(fullNameTF.getText());
                changeCustomer.setEmail(emailTF.getText());
                changeCustomer.setPaymentMethod(paymentTF.getText());
                changeCustomer.setAddress(addressTF.getText());
                changeCustomer.setPhoneNumber(phoneTF.getText());

                Gson gson = new Gson();

                String changeCustomerString = gson.toJson(changeCustomer);
                Message message = new Message(Message.UPDATE_CUSTOMER_INFO, changeCustomerString);
                client.sendMessage(message);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void showCustomerInfo(Customer customer) {
        usernameTF.setText(customer.getUsername());
        fullNameTF.setText(customer.getFullName());
        emailTF.setText(customer.getEmail());
        paymentTF.setText(customer.getPaymentMethod());
        addressTF.setText(customer.getAddress());
        phoneTF.setText(customer.getPhoneNumber());
    }
}
