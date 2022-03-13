import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TreatmentForm implements ActionListener{
    private JFrame frame = new JFrame();
    private JPanel panel;
    private JButton Submit, button_return_to_menu;
    private int id_user;
    private JLabel Name_label, Dosage_Label, remaining_days_Label, date_hour_Label;
    private JTextField tf_name, tf_dosage, tf_remaining_days, tf_date_hour;
    private Font font = new Font("Verdana",Font.PLAIN,28);

    public TreatmentForm(int id_user){
        this.id_user = id_user;
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Create Name label
        Name_label = new JLabel("",SwingConstants.CENTER);
        Name_label.setText("Name");
        Name_label.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0,20,20,20);
        panel.add(Name_label,c);

        //Create Name textfield
        tf_name = new JTextField(50);
        tf_name.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0,20,20,20);
        panel.add(tf_name,c);
        
        // Create Dosage label
        Dosage_Label = new JLabel("",SwingConstants.CENTER);
        Dosage_Label.setText("Dosage");
        Dosage_Label.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0,20,20,20);
        panel.add(Dosage_Label,c);

        //Create Dosage textfield
        tf_dosage = new JTextField(50);
        tf_dosage.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0,20,20,20);
        panel.add(tf_dosage,c);

        //Create remaining_date label
        remaining_days_Label = new JLabel("",SwingConstants.CENTER);
        remaining_days_Label.setText("Remaining Days");
        remaining_days_Label.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0,20,20,20);
        panel.add(remaining_days_Label,c);

        //Create remaining days textfield
        tf_remaining_days = new JTextField(10);
        tf_remaining_days.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(0,20,20,20);
        panel.add(tf_remaining_days,c);

        //Create date hour Label
        date_hour_Label = new JLabel("",SwingConstants.CENTER);
        date_hour_Label.setText("Date-Hour");
        date_hour_Label.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0,20,20,20);
        panel.add(date_hour_Label,c);

        //Create Date Hour textfield
        tf_date_hour = new JTextField(20);
        tf_date_hour.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(0,20,20,20);
        panel.add(tf_date_hour,c);

        //Create submit Button
        Submit = new JButton("Submit");
        Submit.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(20,20,20,20);
        panel.add(Submit,c);

        //Create return to menu Button
        button_return_to_menu = new JButton("Return to menu");
        button_return_to_menu.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(20,20,20,20);
        panel.add(button_return_to_menu,c);

        //perform action
        Submit.addActionListener(this);
        button_return_to_menu.addActionListener(this);

        frame.add(panel);
        frame.setSize(900,600); //set the size
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Treatment Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // for the exit cross
    }

    //define abstract method actionPerformed() which will be called on button click
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == Submit){
            String Namevalue = tf_name.getText();
            String Dosagevalue = tf_dosage.getText();
            String Remaining_Days_value = tf_remaining_days.getText();
            String DateHour_value = tf_date_hour.getText();

            AddTreatment(id_user, Namevalue,Dosagevalue,Remaining_Days_value, DateHour_value);

        }
        if (e.getSource() == button_return_to_menu) {
            frame.dispose();
            GUI menu_window = new GUI(this.id_user);
        }
    }

    public void AddTreatment(int id_user, String Name, String Dosage, String RemainingDays, String DateHour){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/oop_uml?characterEncoding=utf8";
            String user = "root";
            String password = "root";
            Connection con = DriverManager.getConnection(url,user,password);
            Statement stmt = con.createStatement();
            String query = ""; // query to implement
            stmt.executeQuery(query);
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
