import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpWindow implements ActionListener {

    private JFrame frame = new JFrame();
    private JButton button_return_to_menu;
    private int id_user;
    private JLabel content;
    HelpWindow(int id_user) {
        this.id_user=id_user;
        //creating a button allowing to go back to the main menu
        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //action when the button is clicked
        if (e.getSource() == button_return_to_menu) {
            frame.dispose();
            GUI menu_window = new GUI(this.id_user);
        }
    }

    private void Display_help(int id_user)
    {
        this.content = new JLabel("This is the help page of Doctoapp !\n\n" + 
        "Doctoapp allow you to stay in contact with a guardian for your treatment and with doctors.\n" +
        "Go to Treatment to see your treatment and check that you take it, if you forgot, a notification will be sent to your guardian.\n" +
        "You can also manage your schedule of doctor appoinment on the section my appointments" +
        "Finaly you can access to your guardian's informations by the guardian section" +
        "If you have any suggestion or feedback, please contact us at jeremy.grelot@efrei.net");
        button_return_to_menu = new JButton("Return to menu");
        button_return_to_menu.setSize(200, 80);
        button_return_to_menu.setLocation(300, 50);
        button_return_to_menu.addActionListener(this);

        frame.add(this.content);
        frame.add(button_return_to_menu);
        frame.setLayout(null);
        frame.setVisible(true);

        frame.setSize(800, 800);
        frame.setTitle("Help");
        frame.setBackground(Color.GRAY);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for the exit cross
    }
}

