import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login implements ActionListener {

    private int id_user_connected;

    //initialize button, panel, label, and text field
    private JButton login_button, register_button;
    private JPanel panel;
    private JLabel userLabel, passLabel;
    private JTextField textFieldEmail, textFieldPassword;
    private JFrame frame = new JFrame();//the frame is the window

    //calling constructor
    Login() {
        DisplayLogin();
    }


    public int getId_user_connected(){
        return this.id_user_connected;
    }

    //define abstract method actionPerformed() which will be called on button click
    public void actionPerformed(ActionEvent e){

        if (e.getSource() == login_button) {
            ValidateLogin();
        }
        if (e.getSource() == register_button) {
            frame.dispose(); //dispose of the frame
            //launch register page
            Register register_window = new Register();
        }
    }

    private void ValidateLogin(){
        String mailValue = textFieldEmail.getText();        //get user entered username from the textField1
        String passValue = textFieldPassword.getText();        //get user entered pasword from the textField2

        try {
            Connection con = Main.ConnectionTODB("oop_uml","root","root");

            PreparedStatement ps = con.prepareStatement("SELECT id_user, password\n" +
                    "            FROM users\n" +
                    "            WHERE mail='"+mailValue+"';");
            ResultSet rs = ps.executeQuery(); //execute the sql query reading all the datas in the table product

            while(rs.next()) {
                String id = rs.getString(1);
                if(passValue.matches(rs.getString(2))){

                    this.id_user_connected = Integer.parseInt(id);
                    frame.dispose(); //dispose of the frame
                    //launch register page
                    GUI menu_window = new GUI(this.id_user_connected);
                }
                else{
                    //show error message
                    JOptionPane.showMessageDialog(null, "invalid password");
                }

            }
        } catch(Exception ae) {
            System.out.println(ae);
        }
    }

    private void DisplayLogin(){
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //create label for username
        userLabel = new JLabel("", SwingConstants.CENTER);
        userLabel.setText("Email");      //set label value for textField1
        userLabel.setFont(new Font("Verdana", Font.PLAIN, 28));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0,20,20,20);  //top padding
        panel.add(userLabel, c);


        //create text field to get username from the user
        textFieldEmail = new JTextField(15);    //set length of the text
        textFieldEmail.setFont(new Font("Verdana", Font.PLAIN, 28));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0,20,20,20);  //top padding
        panel.add(textFieldEmail, c);

        //create label for password
        passLabel = new JLabel("", SwingConstants.CENTER);
        passLabel.setText("Password");      //set label value for textField2
        passLabel.setFont(new Font("Verdana", Font.PLAIN, 28));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0,20,20,20);  //top padding
        panel.add(passLabel, c);


        //create text field to get password from the user
        textFieldPassword = new JPasswordField(15);    //set length for the password
        textFieldPassword.setFont(new Font("Verdana", Font.PLAIN, 28));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0,20,20,20);  //top padding
        panel.add(textFieldPassword, c);

        //create submit button to login
        login_button = new JButton("Login"); //set label to button
        login_button.setFont(new Font("Verdana", Font.PLAIN, 28));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(20,20,20,20);  //top padding
        panel.add(login_button, c);

        //create button to register as a new user
        register_button = new JButton("Register"); //set label to button
        register_button.setFont(new Font("Verdana", Font.PLAIN, 28));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(20,20,20,20);  //top padding
        panel.add(register_button, c);

        //perform action on button click
        login_button.addActionListener(this);     //add action listener to button
        register_button.addActionListener(this);

        frame.add(panel);
        frame.setSize(900,600); //size of the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Login");         //set title to the login form
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for the exit cross
    }
}
