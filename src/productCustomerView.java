import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class productCustomerView {
        private JTextField productIDTF;
        private JTextField productNameTF;
        private JTextField productPriceTF;
        private JTextField productQuantityTF;
        private JButton LOADButton;
        private JPanel mainPanel;

    private Client client;

        public productCustomerView(Client client) {
            this.client = client;

            LOADButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String productID = productIDTF.getText();
                    Message message = new Message(Message.LOAD_PRODUCT_AS_CUSTOMER, productID);
                    client.sendMessage(message);
                }
            });
        }

        public JPanel getMainPanel() {
            return mainPanel;
        }

        public void updateProductInfo(Product product) {
            productIDTF.setText(String.valueOf(product.getProductID()));
            productNameTF.setText(product.getName());
            productPriceTF.setText(String.valueOf(product.getPrice()));
            productQuantityTF.setText(String.valueOf(product.getQuantity()));
        }
    }


