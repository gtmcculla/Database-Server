import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewUserViewController {
    private JTextField usernameTF;
    private JPasswordField passwordTF;
    private JTextField displayNameTF;
    private JTextField userIDTF;
    private JButton createButton;
    private JPanel mainPanel;

    private Client client;

 public NewUserViewController(Client client) {

     createButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             User newUser = new User();

             newUser.setUsername(usernameTF.getText());
             newUser.setDisplayName(displayNameTF.getText());
             newUser.setPassword(passwordTF.getText());
             newUser.setUserID(Integer.parseInt(userIDTF.getText()));
             Gson gson = new Gson();

             String newUserString = gson.toJson(newUser);

             Message message = new Message(Message.NEW_USER,newUserString);
             client.sendMessage(message);
         }
     });
 }
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
