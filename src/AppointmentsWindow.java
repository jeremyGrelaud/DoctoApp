import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AppointmentsWindow implements ActionListener {

    private JFrame frame = new JFrame();
    private JButton button_return_to_menu;

    AppointmentsWindow() {

        JPanel p = new JPanel();
        try {
            int user_id=1;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_uml?characterEncoding=utf8","root","root");

            PreparedStatement ps = con.prepareStatement("SELECT date_appointment, Name_doctor, Adress FROM Medical_Appointments WHERE id_user='"+user_id+"';");
            ResultSet rs = ps.executeQuery(); //execute the sql query reading all the datas in the table product

            //header of our table
            JLabel h1 = new JLabel("Date", SwingConstants.CENTER);
            JLabel h2 = new JLabel("Hour", SwingConstants.CENTER);
            JLabel h3 = new JLabel("Doctor", SwingConstants.CENTER);
            JLabel h4 = new JLabel("Adress", SwingConstants.CENTER);
            p.add(h1);p.add(h2);p.add(h3);p.add(h4);

            // create a line border with the specified color and width
            Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);

            //while loop to add each datas of each rows/products in the database
            JLabel l1 =null,l2=null,l3=null,l4=null;
            p.setLayout(new GridLayout(1,4));
            int row=2;
            while(rs.next()) {
                l1 = new JLabel("",SwingConstants.CENTER);
                l2 = new JLabel("",SwingConstants.CENTER);
                String datetime = rs.getString(1);
                String[] tab_date = datetime.split(" ");
                l1.setText(tab_date[0]);
                l2.setText(tab_date[1]);
                l3 = new JLabel("",SwingConstants.CENTER);
                l3.setText(rs.getString(2));
                l4 = new JLabel("",SwingConstants.CENTER);
                l4.setText(rs.getString(3));


                // set the border of this component
                l1.setBorder(border);
                l2.setBorder(border);
                l3.setBorder(border);
                l4.setBorder(border);


                p.add(l1);p.add(l2);p.add(l3);p.add(l4);
                p.setLayout(new GridLayout(row,4));
                row++;
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
            frame.setTitle("Future Appointments");
            frame.setBackground(Color.GRAY);
            frame.setResizable(false);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for the exit cross


        }catch(Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //action when the button is clicked
        if (e.getSource() == button_return_to_menu) {
            frame.dispose();
            GUI menu_window = new GUI();
        }
    }
}

