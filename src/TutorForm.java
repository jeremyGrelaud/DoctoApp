import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TutorForm implements ActionListener{
    private JFrame frame = new JFrame();
    private JPanel panel;
    private JButton Submit, button_return_to_tutor;
    private int id_user;
    private JLabel NameTutor_label, MailTutor_label;
    private JTextField NameTutor_tf, MailTutor_tf;
    private Font font = new Font("Verdana",Font.PLAIN,28);

    public TutorForm(int id_user){
        this.id_user = id_user;
        DisplayTutorForm(id_user);
    }

    private void DisplayTutorForm(int id_user){

        try{


            panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            //Create NameTutor label
            NameTutor_label = new JLabel("",SwingConstants.CENTER);
            NameTutor_label.setText("Tutor Name");
            NameTutor_label.setFont(font);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(0,20,20,20);
            panel.add(NameTutor_label,c);

            //Create NameTutor textfield
            NameTutor_tf = new JTextField(20);
            NameTutor_tf.setFont(font);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 0;
            c.insets = new Insets(0,20,20,20);
            panel.add(NameTutor_tf,c);

            //Create MailTutor label
            MailTutor_label = new JLabel("",SwingConstants.CENTER);
            MailTutor_label.setText("Tutor Mail");
            MailTutor_label.setFont(font);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 1;
            c.insets = new Insets(0,20,20,20);
            panel.add(MailTutor_label,c);

            //Create MailTutor textfield
            MailTutor_tf = new JTextField(50);
            MailTutor_tf.setFont(font);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 1;
            c.insets = new Insets(0,20,20,20);
            panel.add(MailTutor_tf,c);

            //Create Submit Button
            Submit = new JButton("Save");
            Submit.setFont(font);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 2;
            c.insets = new Insets(20,20,20,20);
            panel.add(Submit,c);

            //Create return to menu Button
            button_return_to_tutor = new JButton("Return to Guardian");
            button_return_to_tutor.setFont(font);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 3;
            c.insets = new Insets(20,20,20,20);
            panel.add(button_return_to_tutor,c);

            //perform action
            Submit.addActionListener(this);
            button_return_to_tutor.addActionListener(this);

            frame.add(panel);
            frame.setSize(900,600); //set the size
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setTitle("Tutor Form");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // for the exit cross

            //get the actual info of tutor
            Connection con = Main.ConnectionTODB("oop_uml","root","root");

            PreparedStatement ps = con.prepareStatement("SELECT Tutor.mail_tutor, Tutor.Name\n" +
                    "FROM Users INNER JOIN Tutor ON Tutor.id_user = Users.id_user\n" +
                    "WHERE Users.id_user='" + id_user + "';");
            ResultSet rs = ps.executeQuery(); //execute the sql query reading all the datas in the table product
            while (rs.next()) {
                MailTutor_tf.setText(rs.getString(1));
                NameTutor_tf.setText(rs.getString(2));
            }


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //define abstract method actionPerformed() which will be called on button click
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == Submit){
            String NameTutor_value = NameTutor_tf.getText();
            String MailTutor_value = MailTutor_tf.getText();

            AddTutor(id_user,NameTutor_value,MailTutor_value);
        }
        if (e.getSource() == button_return_to_tutor) {
            frame.dispose();
            TutorWindow tutorWindow = new TutorWindow(this.id_user);
        }
    }

    public void AddTutor(int id_user,String NameTutor,String MailTutor){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/oop_uml?characterEncoding=utf8";
            String user = "root";
            String password = "root";
            Connection con = DriverManager.getConnection(url,user,password);
            Statement stmt = con.createStatement();
            String query = "UPDATE Tutor \n" +
                    "SET mail_tutor = '"+ MailTutor +"', Name = '"+NameTutor+"'\n" +
                    "WHERE id_user = '"+id_user+"';";
            stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(frame, "Info updated");
            con.close();
            frame.dispose();
            TutorWindow tutorWindow = new TutorWindow(this.id_user);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}

