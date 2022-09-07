import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordViewController {
    private JTextField userIDTF;
    private JTextField passwordTF;
    private JButton changePasswordButton;
    private JPanel mainPanel;

    private Client client;

    public ChangePasswordViewController(Client client) {
    this.client = client;
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();

                user.setUserID(Integer.parseInt(userIDTF.getText()));
                user.setPassword(passwordTF.getText());
                Gson gson = new Gson();

               String passwordString = gson.toJson(user);
               Message message = new Message(Message.CHANGE_PASSWORD_REQUEST, passwordString);
               client.sendMessage(message);
            }
        });
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
