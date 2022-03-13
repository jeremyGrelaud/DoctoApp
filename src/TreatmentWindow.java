import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TreatmentWindow implements ActionListener {

    private JFrame frame = new JFrame();
    private JButton button_return_to_menu;
    private int id_user;
    private JPanel p = new JPanel();
    TreatmentWindow(int id_user) {
        this.id_user = id_user;
        DisplayTreatments(id_user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //action when the button is clicked
        if (e.getSource() == button_return_to_menu) {
            frame.dispose();
            GUI menu_window = new GUI(this.id_user);
        }
    }

    private void DisplayTreatments(int id_user){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_uml?characterEncoding=utf8", "root", "root");

            PreparedStatement ps = con.prepareStatement("SELECT  Treatment_list.Name,Treatment_list.Remaining_Days , Treatment_list.Dosage, Dosing_Time.Date_hour, Dosing_Time.taken\n" +
                    "FROM Treatment_list INNER JOIN Dosing_Time ON Treatment_list.idDate = Dosing_Time.idDate\n" +
                    "WHERE id_user='" + id_user + "';");
            ;
            ResultSet rs = ps.executeQuery(); //execute the sql query reading all the datas in the table product

            //header of our table
            JLabel h1 = new JLabel("Treatment Name", SwingConstants.CENTER);
            JLabel h2 = new JLabel("Remaining days", SwingConstants.CENTER);
            JLabel h3 = new JLabel("Dosage", SwingConstants.CENTER);
            JLabel h4 = new JLabel("Date", SwingConstants.CENTER);
            JLabel h5 = new JLabel("Hour", SwingConstants.CENTER);
            JLabel h6 = new JLabel("taken", SwingConstants.CENTER);
            p.add(h1);
            p.add(h2);
            p.add(h3);
            p.add(h4);
            p.add(h5);
            p.add(h6);

            // create a line border with the specified color and width
            Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);

            //while loop to add each datas of each rows/products in the database
            JLabel l1 = null, l2 = null, l3 = null, l4 = null, l5 = null, l6 = null;
            p.setLayout(new GridLayout(1, 6));
            int row = 2;
            while (rs.next()) {
                l1 = new JLabel("", SwingConstants.CENTER);
                l1.setText(rs.getString(1));
                l2 = new JLabel("", SwingConstants.CENTER);
                l2.setText(rs.getString(2));
                l3 = new JLabel("", SwingConstants.CENTER);
                l3.setText(rs.getString(3));
                l4 = new JLabel("", SwingConstants.CENTER); //date
                l5 = new JLabel("", SwingConstants.CENTER); // hour
                String datetime = rs.getString(4);
                String[] tab = datetime.split(" ");
                l4.setText(tab[0]);
                l5.setText(tab[1].substring(0, 5).split(":")[0] + "h" + tab[1].substring(0, 5).split(":")[1]);

                l6 = new JLabel("", SwingConstants.CENTER);
                String taken = rs.getString(5);
                if (taken.equalsIgnoreCase("0")) {
                    l6.setText("no");
                } else if (taken.equalsIgnoreCase("1")) {
                    l6.setText("yes");
                } else {
                    l6.setText("error");
                }

                l1.setBorder(border);
                l2.setBorder(border);
                l3.setBorder(border);
                l4.setBorder(border);
                l5.setBorder(border);
                l6.setBorder(border);

                p.add(l1);
                p.add(l2);
                p.add(l3);
                p.add(l4);
                p.add(l5);
                p.add(l6);
                p.setLayout(new GridLayout(row, 6));
                row++;
            }

         }catch(Exception e) {
            System.out.println(e);
        }

        //creating a button allowing to go back to the main menu
        button_return_to_menu = new JButton("Return to menu");
        button_return_to_menu.setSize(200, 80);
        button_return_to_menu.setLocation(350, 50);
        button_return_to_menu.addActionListener(this);
        frame.add(button_return_to_menu);


        p.setBackground(Color.white); //set the color of the window's background
        p.setBounds(150,150,600,600); //set the coordinates of the pannel = gridlayout
        frame.add(p);

        frame.setLayout(null);
        frame.setVisible(true);

        frame.setSize(900, 900);
        frame.setTitle("List of treatments");
        frame.setBackground(Color.GRAY);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for the exit cross

    }
}

