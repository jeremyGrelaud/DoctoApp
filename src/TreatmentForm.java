import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TreatmentForm implements ActionListener{
    private JFrame frame = new JFrame();
    private JPanel panel;
    private JButton Submit, button_return_to_treatment;
    private int id_user;
    private JLabel Name_label, Dosage_Label, remaining_days_Label, date_hour_Label;
    private JTextField tf_name, tf_dosage, tf_remaining_days;
    private Font font = new Font("Verdana",Font.PLAIN,28);
    private JFormattedTextField tf_date_hour;

    public TreatmentForm(){
        this.id_user = Login.getId_user_connected();
        DisplayTreatmentForm(id_user);
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
        if (e.getSource() == button_return_to_treatment) {
            frame.dispose();
            TreatmentWindow treatmentWindow = new TreatmentWindow();
        }
    }

    private void DisplayTreatmentForm(int id_user){
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
        //Create Date appointment textfield
        tf_date_hour = new JFormattedTextField(("yyyy-MM-dd HH:mm"));
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(date);
        tf_date_hour.setColumns(20);
        tf_date_hour.setText(dateString);
        tf_date_hour.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(0,20,20,20);
        panel.add(tf_date_hour,c);


        //Create submit Button
        Submit = new JButton("Add treatment");
        Submit.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(20,20,20,20);
        panel.add(Submit,c);

        //Create return to menu Button
        button_return_to_treatment = new JButton("Return to treatments");
        button_return_to_treatment.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(20,20,20,20);
        panel.add(button_return_to_treatment,c);

        //perform action
        Submit.addActionListener(this);
        button_return_to_treatment.addActionListener(this);


        frame.add(panel);
        frame.setSize(900,600); //set the size
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Treatment Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // for the exit cross


    }

    public void AddTreatment(int id_user, String Name, String Dosage, String RemainingDays, String DateHour){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/oop_uml?characterEncoding=utf8";
            String user = "root";
            String password = "root";
            Connection con = DriverManager.getConnection(url,user,password);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT Max(idTreatment)\n" +
                    "FROM Treatment_list;\n");
            int actual_max_id_treatment_DB = -1;
            while (rs.next()) {
                actual_max_id_treatment_DB = Integer.parseInt(rs.getString(1));
            }

            rs = stmt.executeQuery("SELECT Max(idDate)\n" +
                    "FROM Dosing_Time;\n");
            int actual_max_id_date_DB = -1;
            while (rs.next()) {
                actual_max_id_date_DB = Integer.parseInt(rs.getString(1));
            }

            if(actual_max_id_treatment_DB!=-1 && actual_max_id_date_DB!=-1){
                /**Then insert data in DB*/
                Statement statement = con.createStatement();
                //create date
                int date_id = (actual_max_id_date_DB+1);
                statement.executeUpdate("INSERT INTO Dosing_Time VALUES('"+date_id+"','"+DateHour+"','0')");

                //then create treatment
                statement.executeUpdate("INSERT INTO Treatment_list VALUES ('" + (actual_max_id_treatment_DB+1) + "','" + Integer.parseInt(RemainingDays) + "','" + Dosage + "','"+Name+ "','"+id_user+ "','"+date_id+"');");

                //pop up that informs the user
                JOptionPane.showMessageDialog(frame, "Treatment added");
                frame.dispose();
                TreatmentWindow treatmentWindow = new TreatmentWindow();
            }
            else{
                JOptionPane.showMessageDialog(frame, "Error in the process");
            }


            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
