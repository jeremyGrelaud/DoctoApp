import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Register implements ActionListener {

    private int id_user_connected;

    //initialize button, panel, label, and text field
    JButton register_button;
    JPanel panel;
    JLabel userLabel, passLabel, tutorLabel;
    JTextField textField1, textField2, textField3;
    private JFrame frame = new JFrame();//the frame is the window

    //calling constructor
    Register() {
        DisplayRegister();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == register_button) {
            ValidateRegister();
        }
    }

    private void DisplayRegister(){

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
        c.insets = new Insets(0, 20, 20, 20);  //top padding
        panel.add(userLabel, c);


        //create text field to get username from the user
        textField1 = new JTextField(15);    //set length of the text
        textField1.setFont(new Font("Verdana", Font.PLAIN, 28));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 20, 20, 20);  //top padding
        panel.add(textField1, c);

        //create label for password
        passLabel = new JLabel("", SwingConstants.CENTER);
        passLabel.setText("Password");      //set label value for textField2
        passLabel.setFont(new Font("Verdana", Font.PLAIN, 28));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 20, 20, 20);  //top padding
        panel.add(passLabel, c);


        //create text field to get password from the user
        textField2 = new JPasswordField(15);    //set length for the password
        textField2.setFont(new Font("Verdana", Font.PLAIN, 28));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0, 20, 20, 20);  //top padding
        panel.add(textField2, c);


        //create button to register as a new user
        register_button = new JButton("Register"); //set label to button
        register_button.setFont(new Font("Verdana", Font.PLAIN, 28));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(20, 20, 20, 20);  //top padding
        panel.add(register_button, c);

        //perform action on button click
        //add action listener to button
        register_button.addActionListener(this);

        frame.add(panel);
        frame.setSize(900, 600); //size of the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Register");         //set title to the login form
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for the exit cross
    }

    private void ValidateRegister(){
        String email = textField1.getText();
        String password = textField2.getText();

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_uml?characterEncoding=utf8", "root", "root");
            //product_inventory is database name, root is username and the password is also root

            Statement statement = con.createStatement();

            //check if the id already exists
            ResultSet rs = statement.executeQuery("SELECT max(id_user)\n" +
                    "                    FROM users;");
            rs.next();
            int id_new_user_registered = rs.getInt(1)+1;

            //insert and create user in the database

            statement.executeUpdate("INSERT INTO Users VALUES ('" + id_new_user_registered + "','" + email + "','" + password + "');");
            con.close();
            //pop up that informs the user
            //JOptionPane.showMessageDialog(frame, "Your journey begins now");

            System.out.println(id_new_user_registered);
            frame.dispose(); //dispose of the frame
            //launch menu page
            GUI menu_window = new GUI(id_new_user_registered);

        }catch(Exception ex){
            System.out.println(ex);
        }
    }
}