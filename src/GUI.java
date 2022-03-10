//import com.sun.javafx.css.Stylesheet;
//import com.sun.javafx.css.parser.CSSParser;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GUI implements ActionListener  {

    private JLabel label_greetings, next_appointment;
    private JFrame frame = new JFrame();//the frame is the window
    private JPanel panel;
    private JButton treatment, tutor, appointments, help;

    //constructor
    public GUI() {

        try {
            String date_next_appointment=null;
            String[] hour_next_appointment=null;
            String doctor_next_appointment=null;
            int id_user=1;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_uml?characterEncoding=utf8","root","root");

            PreparedStatement ps = con.prepareStatement("SELECT date_appointment,  Name_doctor\n" +
                    "FROM Medical_Appointments\n" +
                    "WHERE id_user='"+id_user+"' AND date_appointment >= CURRENT_TIMESTAMP()\n" +
                    "ORDER BY date_appointment ASC LIMIT 1;");
            ResultSet rs = ps.executeQuery(); //execute the sql query reading all the datas in the table product
            while(rs.next()) {
                String result = rs.getString(1);
                String[] tab_result = result.split(" ");

                date_next_appointment = tab_result[0];
                hour_next_appointment = tab_result[1].substring(0,5).split(":");
                doctor_next_appointment = rs.getString(2);
            }


            //Beginning of the GUI
            //PANEL
            panel = new JPanel();

            panel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            JLabel label = new JLabel("Your next appointment is on : " + date_next_appointment
                    +"\n At "+ hour_next_appointment[0] + "h" + hour_next_appointment[1]
                    +"\n With Doctor : " + doctor_next_appointment, SwingConstants.CENTER);
            label.setFont(new Font("Verdana", Font.PLAIN, 18));

            //ImageIcon icon_next_appointment = new ImageIcon("..\\Images\\schedule.png");
            ImageIcon icon_next_appointment = new ImageIcon("D:\\Efrei\\L3\\semestre 6\\OO_UML\\Project_V2\\DoctoApp\\Images\\schedule.png");
            label.setIcon(icon_next_appointment);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 100;      //make this component tall
            c.weightx = 1.0;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(20,20,20,20);  //top padding
            panel.add(label, c);

            treatment = new JButton();
            treatment.addActionListener(this);
            //ImageIcon icon_treatment = new ImageIcon("..\\Images\\pilule.png");
            ImageIcon icon_treatment = new ImageIcon("D:\\Efrei\\L3\\semestre 6\\OO_UML\\Project_V2\\DoctoApp\\Images\\pilule.png");
            treatment.setIcon(icon_treatment);
            c.weightx = 0.5;
            c.weighty = 1.0;
            c.gridwidth = 1;
            //c.ipady = 100;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 1;
            c.insets = new Insets(0,20,0,20);  //top padding
            panel.add(treatment, c);

            JLabel treatment_label = new JLabel("Access treatment");
            treatment_label.setHorizontalAlignment(JLabel.CENTER);
            treatment_label.setFont(new Font("Verdana", Font.PLAIN, 18));
            c.weightx = 0.5;
            c.weighty = 1.0;
            c.gridwidth = 1;
            //c.ipady = 100;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 2;
            c.insets = new Insets(0,20,0,20);  //top padding
            panel.add(treatment_label, c);


            tutor = new JButton();
            tutor.addActionListener(this);
            //ImageIcon icon_tutor = new ImageIcon("..\\Images\\tutor.png");
            ImageIcon icon_tutor = new ImageIcon("D:\\Efrei\\L3\\semestre 6\\OO_UML\\Project_V2\\DoctoApp\\Images\\tutor.png");
            tutor.setIcon(icon_tutor);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.weighty = 1.0;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 1;
            c.insets = new Insets(0,20,0,20);  //top padding
            panel.add(tutor, c);

            JLabel tutor_label = new JLabel("Access guardian/tutor");
            tutor_label.setHorizontalAlignment(JLabel.CENTER);
            tutor_label.setFont(new Font("Verdana", Font.PLAIN, 18));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.weighty = 1.0;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 2;
            c.insets = new Insets(0,20,0,20);  //top padding
            panel.add(tutor_label,c);


            appointments = new JButton();
            appointments.addActionListener(this);
            //ImageIcon icon_appointments = new ImageIcon("..\\Images\\appointment.png");
            ImageIcon icon_appointments = new ImageIcon("D:\\Efrei\\L3\\semestre 6\\OO_UML\\Project_V2\\DoctoApp\\Images\\appointment.png");
            appointments.setIcon(icon_appointments);
            c.weightx = 0.5;
            c.weighty = 1.0;
            c.gridwidth = 1;
            //c.ipady = 100;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 3;
            c.insets = new Insets(0,20,0,20);  //top padding
            panel.add(appointments, c);

            JLabel appointments_label = new JLabel("Consult appointments");
            appointments_label.setHorizontalAlignment(JLabel.CENTER);
            appointments_label.setFont(new Font("Verdana", Font.PLAIN, 18));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.weighty = 1.0;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 4;
            c.insets = new Insets(0,20,0,20);  //top padding
            panel.add(appointments_label,c);


            help = new JButton();
            help.addActionListener(this);
            //ImageIcon icon_help = new ImageIcon("..\\Images\\help.png");
            ImageIcon icon_help = new ImageIcon("D:\\Efrei\\L3\\semestre 6\\OO_UML\\Project_V2\\DoctoApp\\Images\\help.png");
            help.setIcon(icon_help);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.weighty = 1.0;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 3;
            c.insets = new Insets(0,20,0,20);  //top padding
            panel.add(help, c);

            JLabel help_label = new JLabel("If you need help");
            help_label.setHorizontalAlignment(JLabel.CENTER);
            help_label.setFont(new Font("Verdana", Font.PLAIN, 18));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.weighty = 1.0;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 4;
            c.insets = new Insets(0,20,0,20);  //top padding
            panel.add(help_label,c);


            //and finaly we had the main panel to the frame
            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for the exit cross

            frame.setTitle("Menu"); //set the title of the page
            //frame.pack(); //set the window to match a certain size
            frame.setSize(900,900);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(true);

        }catch(Exception e) {
            System.out.println(e);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //action when the button is clicked
        if(e.getSource() == treatment){
            //close the actual frame instance
            frame.dispose(); //will close the actual frame
            TreatmentWindow the_treatment_window = new TreatmentWindow();
        }
        else if(e.getSource() == tutor){
            frame.dispose();
            TutorWindow the_tutor_window = new TutorWindow();
        }
        else if(e.getSource() == appointments){
            frame.dispose();
            AppointmentsWindow the_appointments_window = new AppointmentsWindow();
        }
        else if(e.getSource() == help){
            frame.dispose();
            HelpWindow the_help_window = new HelpWindow();
        }


    }

}



