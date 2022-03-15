import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpWindow implements ActionListener {

    private JFrame frame = new JFrame();
    private JButton button_return_to_menu;
    private int id_user;

    private JPanel panel = new JPanel();
    HelpWindow(int id_user) {
        this.id_user=id_user;
        //creating a button allowing to go back to the main menu
        Display_help(id_user);
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
        GridLayout layout = new GridLayout(8,1);
        panel.setLayout(layout);
        panel.setBounds(0,300,800,800);

        JLabel content1 = new JLabel("This is the help page of Doctoapp ! Doctoapp allow you to stay in contact with",SwingConstants.CENTER);
        JLabel content2 = new JLabel("a guardian for your treatment and with doctors. Go to Treatment to see your",SwingConstants.CENTER);
        JLabel content3 = new JLabel("treatment and check that you take it, if you forgot, a notification will",SwingConstants.CENTER);
        JLabel content4 = new JLabel("be sent to your guardian. You can also manage your schedule of doctor ",SwingConstants.CENTER);
        JLabel content5 = new JLabel("appoinment on the section my appointments Finaly you can access to your ",SwingConstants.CENTER);
        JLabel content6 = new JLabel( "guardian's informations by the guardian section If you have any suggestion" ,SwingConstants.CENTER);
        JLabel content7 = new JLabel("or feedback, please contact us at jeremy.grelot@efrei.net",SwingConstants.CENTER);

        content1.setFont(new Font("Verdana",Font.PLAIN,18));
        content2.setFont(new Font("Verdana",Font.PLAIN,18));
        content3.setFont(new Font("Verdana",Font.PLAIN,18));
        content4.setFont(new Font("Verdana",Font.PLAIN,18));
        content5.setFont(new Font("Verdana",Font.PLAIN,18));
        content6.setFont(new Font("Verdana",Font.PLAIN,18));
        content7.setFont(new Font("Verdana",Font.PLAIN,18));


        button_return_to_menu = new JButton("Return to menu");
        button_return_to_menu.addActionListener(this);

        panel.add(button_return_to_menu);
        panel.add(content1);
        panel.add(content2);
        panel.add(content3);
        panel.add(content4);
        panel.add(content5);
        panel.add(content6);
        panel.add(content7);


        frame.add(panel);
        frame.setSize(800, 800);
        frame.setTitle("Help");
        frame.setBackground(Color.GRAY);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for the exit cross
    }
}

