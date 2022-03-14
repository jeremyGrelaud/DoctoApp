import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TutorWindow implements ActionListener {

    private JFrame frame = new JFrame();
    private JButton button_return_to_menu,modify_tutor;
    private int id_user;
    private JPanel p = new JPanel();

    TutorWindow(int id_user) {
        this.id_user = id_user;
        DisplayTutor(id_user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //action when the button is clicked
        if (e.getSource() == button_return_to_menu) {
            frame.dispose();
            GUI menu_window = new GUI(this.id_user);
        }
        if(e.getSource() == modify_tutor){
            frame.dispose();
            TutorForm tutorForm = new TutorForm(this.id_user);
        }
    }

    private void DisplayTutor(int id_user) {
        try {
            Connection con = Main.ConnectionTODB("oop_uml","root","root");

            PreparedStatement ps = con.prepareStatement("SELECT Tutor.mail_tutor, Tutor.Name\n" +
                    "FROM Users INNER JOIN Tutor ON Tutor.id_user = Users.id_user\n" +
                    "WHERE Users.id_user='" + id_user + "';");
            ResultSet rs = ps.executeQuery(); //execute the sql query reading all the datas in the table product

            //header of our table
            JLabel h1 = new JLabel("Guardian mail", SwingConstants.CENTER);
            JLabel h2 = new JLabel("Guardian Name", SwingConstants.CENTER);
            p.add(h1);
            p.add(h2);

            // create a line border with the specified color and width
            Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);

            //while loop to add each datas of each rows/products in the database
            JLabel l1 = null, l2 = null;
            p.setLayout(new GridLayout(1, 2));
            int row = 2;
            while (rs.next()) {
                l1 = new JLabel("", SwingConstants.CENTER);
                l1.setText(rs.getString(1));
                l2 = new JLabel("", SwingConstants.CENTER);
                l2.setText(rs.getString(2));

                l1.setBorder(border);
                l2.setBorder(border);

                p.add(l1);
                p.add(l2);
                p.setLayout(new GridLayout(row, 2));
                row++;
            }

            //creating a button allowing to go back to the main menu
            button_return_to_menu = new JButton("Return to menu");
            button_return_to_menu.setSize(200, 80);
            button_return_to_menu.setLocation(230, 50);
            button_return_to_menu.addActionListener(this);
            frame.add(button_return_to_menu);

            modify_tutor = new JButton("Modify Guardian info");
            modify_tutor.setSize(200, 80);
            modify_tutor.setLocation(230, 350);
            modify_tutor.addActionListener(this);
            frame.add(modify_tutor);


            p.setBackground(Color.white); //set the color of the window's background
            p.setBounds(150, 150, 350, 150); //set the coordinates of the pannel = gridlayout
            frame.add(p);

            frame.setLayout(null);
            frame.setVisible(true);

            frame.setSize(650, 500);
            frame.setTitle("Guardian Information");
            frame.setBackground(Color.GRAY);
            frame.setResizable(false);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for the exit cross
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //private void ModifyTutorInfos(){
    //}
}

