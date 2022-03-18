import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TutorApp implements ActionListener {

    private JFrame frame = new JFrame();
    private JButton get_patient_report;
    private int id_Tutor_user;
    private JPanel p = new JPanel();

    TutorApp() {
        this.id_Tutor_user = Login.getId_user_connected();
        DisplayTutorApp(id_Tutor_user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //action when the button is clicked
        if (e.getSource() == get_patient_report) {
            try {
                Connection con = Main.ConnectionTODB("oop_uml","root","root");

                PreparedStatement ps = con.prepareStatement("SELECT id_user FROM Tutor WHERE id_tutor='"+id_Tutor_user+"'");

                ResultSet rs = ps.executeQuery();
                rs.next();
                int id_pupil = rs.getInt(1);
                GetHistoryTreatmentsUser(id_pupil);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    private void DisplayTutorApp(int id_Tutor_user) {

            JPanel panel_principal = new JPanel();
            panel_principal.setLayout(new GridLayout(3, 1));
            get_patient_report = new JButton("Get your patient report");
            get_patient_report.addActionListener(this);
            get_patient_report.setFont(new Font("Verdana", Font.PLAIN, 28));

            panel_principal.add(get_patient_report);

            JLabel label = new JLabel("Your patient hasn't taken the following treatments : ",SwingConstants.CENTER);
            label.setFont(new Font("Verdana", Font.PLAIN, 28));
            panel_principal.add(label);

            panel_principal.add(CheckEmailPupilMissedTreatments());
            frame.add(panel_principal);
            frame.setVisible(true);
            frame.setSize(800, 800);
            frame.setTitle("Guardian App");
            frame.setBackground(Color.GRAY);
            frame.setResizable(false);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for the exit cross

    }

    private JPanel CheckEmailPupilMissedTreatments() {
        try {
            Connection con = Main.ConnectionTODB("oop_uml","root","root");

            PreparedStatement ps = con.prepareStatement("SELECT mail,Dosage,Name,Date_hour\n" +
                    "FROM Treatment_list  INNER JOIN Dosing_Time ON Treatment_list.idDate = Dosing_Time.idDate\n" +
                    "INNER JOIN Users ON Users.id_user = Treatment_list.id_user\n" +
                    "WHERE Treatment_list.id_user=(SELECT id_user FROM Tutor WHERE id_tutor='"+id_Tutor_user+"')\n" +
                    "AND taken='0' AND Date_hour < CURRENT_TIMESTAMP();");

            //table
            JLabel h1 = new JLabel("Your pupil mail", SwingConstants.CENTER);
            JLabel h2 = new JLabel("Dosage", SwingConstants.CENTER);
            JLabel h3 = new JLabel("Treatment", SwingConstants.CENTER);
            JLabel h4 = new JLabel("Date", SwingConstants.CENTER);
            JLabel h5 = new JLabel("Hour", SwingConstants.CENTER);
            p.add(h1);
            p.add(h2);
            p.add(h3);
            p.add(h4);
            p.add(h5);
            // create a line border with the specified color and width
            Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);


            JLabel l1 = null, l2 = null, l3 = null, l4 = null, l5 = null;
            p.setLayout(new GridLayout(1, 5));
            int row = 2;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String mail_pupil = rs.getString(1);
                String dosage_treatment = rs.getString(2);
                String name_treatment = rs.getString(3);
                String date = rs.getString(4).split(" ")[0];
                String hour = (rs.getString(4).split(" ")[1].split(":")[0])+"h"+(rs.getString(4).split(" ")[1].split(":")[1]);

                l1 = new JLabel("", SwingConstants.CENTER);
                l1.setText(mail_pupil);
                l2 = new JLabel("", SwingConstants.CENTER);
                l2.setText(dosage_treatment);
                l3 = new JLabel("", SwingConstants.CENTER);
                l3.setText(name_treatment);
                l4 = new JLabel("", SwingConstants.CENTER); //date
                l4.setText(date);
                l5 = new JLabel("", SwingConstants.CENTER); // hour
                l5.setText(hour);

                l1.setBorder(border);
                l2.setBorder(border);
                l3.setBorder(border);
                l4.setBorder(border);
                l5.setBorder(border);

                p.add(l1);
                p.add(l2);
                p.add(l3);
                p.add(l4);
                p.add(l5);
                p.setLayout(new GridLayout(row, 6));
                row++;
            }
            p.setBackground(Color.white); //set the color of the window's background
            return p;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return p;
    }

    private static void GetHistoryTreatmentsUser(int id_user){

        try{
            Connection con = Main.ConnectionTODB("oop_uml","root","root");

            PreparedStatement ps = con.prepareStatement("SELECT  Treatment_list.Name,Treatment_list.Remaining_Days , Treatment_list.Dosage, Dosing_Time.Date_hour, Dosing_Time.taken\n" +
                    "FROM Treatment_list INNER JOIN Dosing_Time ON Treatment_list.idDate = Dosing_Time.idDate\n" +
                    "WHERE id_user='"+id_user+"';");
            ;
            ResultSet rs = ps.executeQuery(); //execute the sql query reading all the datas in the table product

            //for file writing
            FileWriter fstream = new FileWriter("History_treatment_user_"+id_user+".txt");
            BufferedWriter content_file = new BufferedWriter(fstream);


            String l1; //Treatment Name
            String l2; //Remaining days
            String l3; //Dosage
            String l4; //Date
            String l5; //Hour
            String l6; //taken


            while (rs.next()) {
                l1 = rs.getString(1);
                l2 = rs.getString(2);
                l3 = rs.getString(3);

                String datetime = rs.getString(4);
                String[] tab = datetime.split(" ");
                l4 = tab[0];
                l5 = (tab[1].substring(0, 5).split(":")[0] + "h" + tab[1].substring(0, 5).split(":")[1]);
                String taken = rs.getString(5);
                if (taken.equalsIgnoreCase("0")) {
                    l6 = "no";
                } else if (taken.equalsIgnoreCase("1")) {
                    l6 = "yes";
                } else {
                    l6 = "error";
                }

                content_file.write(String.format("\n"+l1+"  "+l2+"  "+l3+"  "+l4+"  "+l5+"  "+l6));
            }
            content_file.close();

            //then add the line in the file


            con.close();
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}