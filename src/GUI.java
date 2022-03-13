import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class GUI implements ActionListener {

    private JFrame frame = new JFrame();//the frame is the window
    private JPanel panel;
    private JButton treatment, tutor, appointments, help;
    private JLabel label_next_appointment;

    private int id_user;

    //constructor
    public GUI(int id_user) {
        this.id_user = id_user;
        DisplayGUI(id_user);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //action when the button is clicked
        if (e.getSource() == treatment) {
            //close the actual frame instance
            frame.dispose(); //will close the actual frame
            TreatmentWindow the_treatment_window = new TreatmentWindow(this.id_user);
        } else if (e.getSource() == tutor) {
            frame.dispose();
            TutorWindow the_tutor_window = new TutorWindow(this.id_user);
        } else if (e.getSource() == appointments) {
            frame.dispose();
            AppointmentsWindow the_appointments_window = new AppointmentsWindow(this.id_user);
        } else if (e.getSource() == help) {
            frame.dispose();
            HelpWindow the_help_window = new HelpWindow(this.id_user);
        }
    }

    public Boolean CreateNotification(String[] treatment_infos) {
        String treatment_name = treatment_infos[2];
        Boolean taken = false;
        int choice = JOptionPane.showConfirmDialog(null, "Click ok to check your treatment", treatment_name + " treatment", JOptionPane.YES_OPTION);
        if (choice == 0) {
            frame.dispose();
            Notification_page new_notif_window = new Notification_page(treatment_infos, this.id_user);
            taken = new_notif_window.getTreatment_taken();
        }
        //return 0 for yes
        //1 for no
        //-1 if closed
        return taken;

    }

    //public Boolean SendEmail(String GuardianEmail){
   // }

    private void DisplayGUI(int id_user){
        try {
            String date_next_appointment = null;
            String[] hour_next_appointment = null;
            String doctor_next_appointment = null;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_uml?characterEncoding=utf8", "root", "root");

            PreparedStatement ps = con.prepareStatement("SELECT date_appointment,  Name_doctor\n" +
                    "FROM Medical_Appointments\n" +
                    "WHERE id_user='" + id_user + "' AND date_appointment >= CURRENT_TIMESTAMP()\n" +
                    "ORDER BY date_appointment ASC LIMIT 1;");
            ResultSet rs = ps.executeQuery(); //execute the sql query reading all the datas in the table product
            while (rs.next()) {
                String result = rs.getString(1);
                String[] tab_result = result.split(" ");

                date_next_appointment = tab_result[0];
                hour_next_appointment = tab_result[1].substring(0, 5).split(":");
                doctor_next_appointment = rs.getString(2);
            }


            //Beginning of the GUI
            //PANEL
            panel = new JPanel();

            panel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            if (date_next_appointment != null && hour_next_appointment != null && doctor_next_appointment != null) {
                label_next_appointment = new JLabel("Your next appointment is on : " + date_next_appointment
                        + "\n At " + hour_next_appointment[0] + "h" + hour_next_appointment[1]
                        + "\n With Doctor : " + doctor_next_appointment, SwingConstants.CENTER);
                label_next_appointment.setFont(new Font("Verdana", Font.PLAIN, 18));
            } else {
                label_next_appointment = new JLabel("You don't have any appointment", SwingConstants.CENTER);
                label_next_appointment.setFont(new Font("Verdana", Font.PLAIN, 18));
            }

            //ImageIcon icon_next_appointment = new ImageIcon("..\\Images\\schedule.png");
            ImageIcon icon_next_appointment = new ImageIcon("D:\\Efrei\\L3\\semestre 6\\OO_UML\\Project_V2\\DoctoApp\\Images\\schedule.png");
            label_next_appointment.setIcon(icon_next_appointment);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 100;      //make this component tall
            c.weightx = 1.0;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(20, 20, 20, 20);  //top padding
            panel.add(label_next_appointment, c);

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
            c.insets = new Insets(0, 20, 0, 20);  //top padding
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
            c.insets = new Insets(0, 20, 0, 20);  //top padding
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
            c.insets = new Insets(0, 20, 0, 20);  //top padding
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
            c.insets = new Insets(0, 20, 0, 20);  //top padding
            panel.add(tutor_label, c);


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
            c.insets = new Insets(0, 20, 0, 20);  //top padding
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
            c.insets = new Insets(0, 20, 0, 20);  //top padding
            panel.add(appointments_label, c);


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
            c.insets = new Insets(0, 20, 0, 20);  //top padding
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
            c.insets = new Insets(0, 20, 0, 20);  //top padding
            panel.add(help_label, c);


            //and finaly we had the main panel to the frame
            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for the exit cross

            frame.setTitle("Menu"); //set the title of the page
            //frame.pack(); //set the window to match a certain size
            frame.setSize(900, 900);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(true);

            //we check if we have to notify the user of one of his treatment
            CheckTreatmentsStatus();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void CheckTreatmentsStatus(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_uml?characterEncoding=utf8", "root", "root");
            /***WE now have to check if the user got treatments to take today to make notifications if needed*/

            PreparedStatement ps2 = con.prepareStatement("SELECT Remaining_Days, Dosage, Name,  Date_hour\n" +
                    "FROM Treatment_list INNER JOIN Dosing_Time ON Treatment_list.idDate = Dosing_Time.idDate\n" +
                    "WHERE id_user='" + id_user + "' AND Date_hour >= Date(now()) AND Date_hour <= DATE_ADD(CURDATE(),INTERVAL 1 DAY)\n" +
                    "ORDER BY Date_hour ASC;");
            ResultSet rs2 = ps2.executeQuery(); //execute the sql query reading all the datas in the table product

            java.util.List<String[]> tab_todays_treatements = new ArrayList<>();
            int i = 0;
            while (rs2.next()) {
                String[] tab_one_treatment = new String[7];

                String remaining_days = rs2.getString(1);
                String treatment_dosage = rs2.getString(2);
                String treatment_name = rs2.getString(3);
                String[] date_treament = rs2.getString(4).split(" ")[0].split("-");
                String hour_treatment = rs2.getString(4).split(" ")[1].substring(0, 5);
                //System.out.println(remaining_days+" "+treatment_dosage+" "+treatment_name+" "+date_treament[0]+" "+date_treament[1]+" "+date_treament[2]+" "+hour_treatment);
                tab_one_treatment[0] = remaining_days;
                tab_one_treatment[1] = treatment_dosage;
                tab_one_treatment[2] = treatment_name;
                tab_one_treatment[3] = date_treament[0];
                tab_one_treatment[4] = date_treament[1];
                tab_one_treatment[5] = date_treament[2];
                tab_one_treatment[6] = hour_treatment;
                i++;
                tab_todays_treatements.add(tab_one_treatment);
            }


            DateTimeFormatter actual_time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            LocalDateTime now = LocalDateTime.now();
            //System.out.println(actual_time.format(now));

            //at the end i gives the number of treatments of today
            //all contained in the list tab_todays_treatements
            for (int j = 0; j < i; j++) {
                Boolean treatment_taken = false;
                //if it's time create notification
                //create a notification
                String[] tab = tab_todays_treatements.get(j);
                String Date = tab[3] + "/" + tab[4] + "/" + tab[5] + " " + tab[6];
                // we now have Date at the same formart than actual_time.format(now)

                if (actual_time.format(now).matches(Date)) {
                    treatment_taken = CreateNotification(tab_todays_treatements.get(j));

                }

                if (!treatment_taken) {
                    //send an email to the tutor
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}



