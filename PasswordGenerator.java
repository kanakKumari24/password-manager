import javax.swing.*;
import java.sql.PreparedStatement;
import java.util.Random;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.*;
//import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.security.SecureRandom;

public class PasswordGenerator implements ActionListener{
    JFrame frame = new JFrame();
    private JTextField passwordField;
    private JTextField lengthField;
    JTextField purposeField;
    JPanel panel = new JPanel();
    JButton generateButton = new JButton("Generate Password");
    JButton save = new JButton("Save");
    String password ="";
    Connection con;

    public void actionPerformed(ActionEvent e) {
        String purposeText="";
        if (e.getSource() == generateButton) {

            if (lengthField.getText().isBlank()) {
                JOptionPane.showMessageDialog(frame, "Input must be a number");
            }
            else if(purposeField.getText().isBlank()){
                JOptionPane.showMessageDialog(frame, "Purpose cannot be empty");
            }
            else {
                purposeField.setEditable(false);
                int length = Integer.parseInt(lengthField.getText());
                if (length < 8) {
                    JOptionPane.showMessageDialog(frame, "Password length should be 8 or more");
                } else {
                    password = generatePassword(length);
                    passwordField.setText(password);
                }
            }
        }

        else if(e.getSource() == save) {
            if (passwordField.getText() == null) {
                JOptionPane.showMessageDialog(frame, "No password is generated");
            } else {
                purposeText = purposeField.getText();
                savePassword(purposeText, password);
                JOptionPane.showMessageDialog(frame, "Password Saved");
                purposeField.setEditable(true);
                purposeField.setText(null);
                passwordField.setText(null);
                lengthField.setText(null);
            }
        }
    }

    private void savePassword(String purposeText, String pass){
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Password_manager", "root", "Sql@1234");
            PreparedStatement ps = con.prepareStatement("Insert into passwords(PURPOSE, PASSWORD) values(?, ?)");
            ps.setString(1, purposeText);
            ps.setString(2, pass);
            int n = ps.executeUpdate();
            System.out.println(n + " Password(s) saved");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public PasswordGenerator() {
        frame.setTitle("Password Generator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setLocation(500, 150);

        JLabel lengthLabel = new JLabel("Password Length:");
        lengthLabel.setBounds(75, 50, 120, 25);
        panel.add(lengthLabel);

        lengthField = new JTextField();
        lengthField.setBounds(210, 50, 50, 25);
        panel.add(lengthField);


        JLabel purposeLabel = new JLabel("Purpose:");
        purposeLabel.setBounds(75, 90, 120, 25);
        panel.add(purposeLabel);

        purposeField = new JTextField();
        purposeField.setBounds(210, 90, 120, 25);
        panel.add(purposeField);


        generateButton.setBounds(120, 130, 150, 25);
        generateButton.addActionListener(this);
        panel.add(generateButton);

        JLabel passwordLabel = new JLabel("Generated Password:");
        passwordLabel.setBounds(70, 90, 150, 250);
        panel.add(passwordLabel);

        passwordField = new JTextField();
        passwordField.setBounds(210, 200, 120, 25);
        passwordField.setEditable(false);
        panel.add(passwordField);


        save.setBounds(120, 250, 150, 25);
        save.addActionListener(this);
        panel.add(save);

        panel.setPreferredSize(new Dimension(420,420));
        panel.setLayout(null);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private String generatePassword(int l) {
        password = "";
        char capital[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        
        char small[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        char number[] = {'0','1','2','3','4','5','6','7','8','9'};
        char symbols[] = {'@','#','$','*','^','!','.','_','&','%'};
        Random random = new Random();

        int temp = l/4;
        while(temp > 0){
            password += capital[random.nextInt(capital.length)];
            password += small[random.nextInt(small.length)];
            password += number[random.nextInt(number.length)];
            password += symbols[random.nextInt(symbols.length)];
            temp--;
        }
        temp = l % 4;
        while(temp > 0){
            password += small[random.nextInt(small.length)];
            temp--;
        }

        return password;
    }

    public static void main(String[] args) {
        new PasswordGenerator();
    }
}