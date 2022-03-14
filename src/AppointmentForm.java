import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class AppointmentForm implements ActionListener{
    private JFrame frame = new JFrame();
    private JPanel panel;
    private JButton Submit, button_return_to_appointment;
    private int id_user;
    private JLabel NameDoctor_label, Adress_label, Date_label;
    private JTextField NameDoctor_tf, Adress_tf;
    private Font font = new Font("Verdana",Font.PLAIN,28);
    private JFormattedTextField Date_tf;

    public AppointmentForm(int id_user){
        this.id_user = id_user;
        DisplayAppointmentForm(id_user);
    }

    //define abstract method actionPerformed() which will be called on button click
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == Submit){
            String NameDoctor_value = NameDoctor_tf.getText();
            String Adress_value = Adress_tf.getText();
            String Date_value = Date_tf.getText();

            AddAppointment(id_user,NameDoctor_value,Adress_value,Date_value);
        }
        if (e.getSource() == button_return_to_appointment) {
            frame.dispose();
            AppointmentsWindow appointmentsWindow = new AppointmentsWindow(this.id_user);
        }
    }

    private void DisplayAppointmentForm(int id_user){
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Create NameDoctor label
        NameDoctor_label = new JLabel("",SwingConstants.CENTER);
        NameDoctor_label.setText("Doctor Name");
        NameDoctor_label.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0,20,20,20);
        panel.add(NameDoctor_label,c);

        //Create NameDoctor texfield
        NameDoctor_tf = new JTextField(20);
        NameDoctor_tf.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0,20,20,20);
        panel.add(NameDoctor_tf,c);

        //Create Adress label
        Adress_label = new JLabel("",SwingConstants.CENTER);
        Adress_label.setText("Adress");
        Adress_label.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0,20,20,20);
        panel.add(Adress_label,c);

        //Create Adress textfield
        Adress_tf = new JTextField(50);
        Adress_tf.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0,20,20,20);
        panel.add(Adress_tf,c);

        //Create Date appointment label
        Date_label = new JLabel("",SwingConstants.CENTER);
        Date_label.setText("Date");
        Date_label.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0,20,20,20);
        panel.add(Date_label,c);

        //Create Date appointment textfield
        Date_tf = new JFormattedTextField(("yyyy-MM-dd HH:mm"));
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(date);
        Date_tf.setColumns(20);
        Date_tf.setText(dateString);
        Date_tf.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(0,20,20,20);
        panel.add(Date_tf,c);

        //Create Submit Button
        Submit = new JButton("Add appointment");
        Submit.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(20,20,20,20);
        panel.add(Submit,c);

        //Create return to menu Button
        button_return_to_appointment = new JButton("Return to appointments");
        button_return_to_appointment.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(20,20,20,20);
        panel.add(button_return_to_appointment,c);

        //perform action
        Submit.addActionListener(this);
        button_return_to_appointment.addActionListener(this);


        frame.add(panel);
        frame.setSize(900,600); //set the size
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Appointment Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // for the exit cross
    }

    private void AddAppointment(int id_user, String NameDoctor, String Adress, String Date){
        try{
            String url = "oop_uml";
            String user = "root";
            String password = "root";
            Connection con = Main.ConnectionTODB(url,user,password);
            PreparedStatement ps = con.prepareStatement("SELECT Max(id_appointment)\n" +
                    "FROM Medical_Appointments;\n");


            ResultSet rs = ps.executeQuery(); //execute the sql query reading all the datas in the table product
            int actual_max_id_DB = -1;
            while (rs.next()) {
                actual_max_id_DB = Integer.parseInt(rs.getString(1));
            }

            if(actual_max_id_DB!=-1){
                //System.out.println(Date_tf.getText());
                /**Then insert data in DB*/
                Statement statement = con.createStatement();
                statement.executeUpdate("INSERT INTO Medical_Appointments VALUES ('" + (actual_max_id_DB+1) + "','" + Date + "','" + NameDoctor + "','"+Adress+ "','"+id_user+ "');");

                //pop up that informs the user
                JOptionPane.showMessageDialog(frame, "Appointment added");
                frame.dispose();
                AppointmentsWindow appointmentsWindow = new AppointmentsWindow(this.id_user);
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
