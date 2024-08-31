import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class SavedPasswords implements ActionListener {

    // -------- Declaring the components -----------
    JFrame frame = new JFrame();
    JPanel buttonPanel = new JPanel();
    JTable table;
    Connection con;
    JScrollPane scrollPane;

    DefaultTableModel model = new DefaultTableModel();

    JButton deleteButton = new JButton();
    JButton closeButton = new JButton();

    // ---------- Setting properties for the frame ---------
    private void initFrame() {
        frame.setTitle("All Passwords");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setLocation(500, 150);
    }

    // ---------- Setting properties for the bottom panel ---------
    private void initPanel() {
        buttonPanel.setPreferredSize(new Dimension(300, 50));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    }


    // ---------- Setting properties for the buttons ---------
    private void initButtons() {
        deleteButton.setText("Delete");
        deleteButton.setPreferredSize(new Dimension(200, 40));
        deleteButton.setFocusable(false);

        closeButton.setText("Close");
        closeButton.setPreferredSize(new Dimension(200, 40));
        closeButton.setFocusable(false);
    }

    // -------- adding action listener to buttons for functioning ----------
    private void addActionListenerToButtons() {
        deleteButton.addActionListener(this);
        closeButton.addActionListener(this);
    }


    private void createTable() {
        // ----- creating the table model ----
        model.addColumn("Purpose");
        model.addColumn("Passwords");

    }


    private void fetch(){
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "root", "Sql@1234");
                PreparedStatement ps = con.prepareStatement("Select * from passwords");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String purpose = rs.getString(1);
                    String pass = rs.getString(2);
                    String row[] = {purpose, pass};
                    model.addRow(row);
                    // ----- A blueprint for table ----------
                }
                con.close();
            } catch (Exception e) {
                System.out.println("\n\nException Occurred: " + e);
            }

        // ------- creating the table using the model ----------
        table = new JTable(model);
//        table.setBackground(new Color(37, 37, 38));
//        table.setGridColor(new Color(75, 0, 130));
        table.setFont(new Font("Calibri", Font.PLAIN, 20));
        table.setForeground(Color.BLACK);
        table.setRowHeight(30);
//        table.setBackground(Color.BLACK);
//        table.setForeground(Color.WHITE);


        // --------- aligning the data in center ------------
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Center-align the text in the "Age" column (column index 1)
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

    }


    // ---------- deleting the contact with name passed to function --------
    private void deletePasswordFromDB(String passToDelete) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "root", "Sql@1234");
            PreparedStatement ps = con.prepareStatement("Delete from passwords where password = ?");
            ps.setString(1, passToDelete);

            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println("\n\nException Occurred: " + e);
        }

    }


    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == deleteButton) {
            int selectedRow = table.getSelectedRow();

            if (selectedRow >= 0) {
                String selectedPass = (String)(table.getValueAt(selectedRow, 1));
                deletePasswordFromDB(selectedPass);      // deleting from Database
                model.removeRow(selectedRow);          // removing from table
                JOptionPane.showMessageDialog(null, "Selected password deleted successfully");

            } else {
                JOptionPane.showMessageDialog(frame, "Please select a password to delete.");
            }
        }

        else if(ae.getSource() == closeButton) {
            try {
                con.close();
            }
            catch(Exception e){
                System.out.println("\n\nException Occurred: " + e);
            }
            frame.dispose();
        }
    }



    private void addElementsToPanel() {
        buttonPanel.add(closeButton);
        buttonPanel.add(deleteButton);
    }


    // ----------- adding buttonPanel to frame, set visibility to true and pack -------
    private void addElementsToFrame() {
        frame.add(buttonPanel, BorderLayout.SOUTH);
//        createTable();

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }


    // --------- class default constructor ----------
    SavedPasswords() {
//      establishConnectionWithDB();
        createTable();
        fetch();
        initFrame();
        initPanel();

        initButtons();
        addActionListenerToButtons();


        addElementsToPanel();
        addElementsToFrame();

        // -------------- closing the connection when programme terminates -----------

    }


    public static void main(String[] args) {
        new SavedPasswords();
    }
}

