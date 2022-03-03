import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Frame;

public class GUI implements ActionListener  {

    private Label label_greetings;
    private JFrame frame = new JFrame();//the frame is the window
    private JPanel panel;
    private JButton treatment, tutor, appointments, help;

    //constructor
    public GUI() {

        //we create the element we wants then add them to the pannel or the frame
        //PANEL
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        panel.setLayout(new GridLayout(2,2));
        panel.setBackground(Color.WHITE);


        label_greetings = new Label("Click the icons");
        label_greetings.setFont(new Font("Arial", Font.PLAIN, 20));
        label_greetings.setSize(500, 20);
        frame.add(label_greetings);

        treatment = new JButton();
        treatment.addActionListener(this);
        treatment.setSize(50,50);
        ImageIcon icon_treatment = new ImageIcon("Images\\pilule.png");
        treatment.setIcon(icon_treatment);
        panel.add(treatment);

        tutor = new JButton();
        tutor.addActionListener(this);
        ImageIcon icon_tutor = new ImageIcon("Images\\tutor.png");
        tutor.setIcon(icon_tutor);
        panel.add(tutor);

        appointments = new JButton();
        appointments.addActionListener(this);
        ImageIcon icon_appointments = new ImageIcon("Images\\appointment.png");
        appointments.setIcon(icon_appointments);
        panel.add(appointments);

        help = new JButton();
        help.addActionListener(this);
        ImageIcon icon_help = new ImageIcon("Images\\help.png");
        help.setIcon(icon_help);
        panel.add(help);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for the exit cross

        frame.setTitle("Main Page"); //set the title of the page
        frame.pack(); //set the window to match a certain size
        frame.setSize(600,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);
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

