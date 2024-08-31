import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


class MainScreen implements ActionListener{
    JFrame frame = new JFrame();
    JLabel label = new JLabel();
    JPanel panel = new JPanel();
    JButton button = new JButton();
    JButton button2 = new JButton();
    ImageIcon image = new ImageIcon();
    JLabel name = new JLabel();
    JLabel uid = new JLabel();

    void frame(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //frame.setSize(520, 520);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.getContentPane().setBackground(Color.gray);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(500,100);
    }

    void button(){
        button.setVisible(true);
        button.setText("GENERATE PASSWORD");
        //button.setSize(123, 123);
        //button.setLocation(42, 42);
        button.setBounds(150,225,200,55);
        button.addActionListener(this);
        button.setFocusable(false);
    }
    void button2(){
        button2.setVisible(true);
        button2.setText("SAVED PASSWORD");
        button2.setBounds(150,300,200,55);
        button2.addActionListener(this);
        button2.setFocusable(false);
    }
    public void actionPerformed(ActionEvent x){
        if(x.getSource() == button){
            new PasswordGenerator();
        }
        else if(x.getSource() == button2){
//            JOptionPane.showMessageDialog(frame, "it is in development stage");
            new SavedPasswords();
        }
    }
    void text(){
        name.setText("by Kanak Kumari");
        name.setVisible(true);
        name.setBounds(150,100,250,55);
        name.setFont(new Font("Courier New",Font.ITALIC, 20));
        name.setForeground(Color.BLACK);

        uid.setText("UID: ");
        uid.setVisible(true);
        uid.setBounds(150,125,200,55);
        uid.setFont(new Font("Courier New",Font.ITALIC, 20));
        uid.setForeground(Color.BLACK);
    }
    void label(){
        label.setText(" PASSWORD GENERATOR AND MANAGER ");
        label.setVisible(true);
        label.setSize(1000,100);
        label.setIcon(image);
        label.setFont(new Font("Courier New",Font.ITALIC, 24));
        label.setForeground(Color.BLACK);
        //label.setHorizontalTextPosition();
        //label.add(button);

    }
    void panel(){
        panel.setPreferredSize(new Dimension(520,520));
        //panel.setBackground(Color.black);
        panel.setLayout(null);
        panel.add(button);
        panel.add(button2);
        panel.add(label);
        panel.add(name);
        panel.add(uid);
    }
    public MainScreen(){
        label();
        text();
        button();
        button2();
        panel();
        frame();
    }
    public static void main(String []args){
        new MainScreen();
    }
}