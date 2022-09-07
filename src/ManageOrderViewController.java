import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageOrderViewController {
    private JTextField customerIDTF;
    private JTable orderTable;
    private JButton searchButton;
    private JPanel mainPanel;
    private DefaultTableModel tableModel;
    //USED FOR CUSTOMER FINDING THEIR ORDERS

    public ManageOrderViewController() {
        tableModel = new DefaultTableModel();
        orderTable.setModel(tableModel);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

}
