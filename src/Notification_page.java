import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Notification_page implements ActionListener {

    private JFrame frame = new JFrame();
    private JButton button_return_to_menu;
    private JButton button_confirm;

    private Boolean treatment_taken;

    public Notification_page(String[] treatment_infos){

        this.treatment_taken = false;

        String remaining_days = treatment_infos[0];
        String treatment_dosage = treatment_infos[1];
        String treatment_name = treatment_infos[2];
        String year = treatment_infos[3];
        String month = treatment_infos[4];
        String day = treatment_infos[5];
        String hour = treatment_infos[6];
        String hour_format = hour.split(":")[0]+"h"+hour.split(":")[1];

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel label0 = new JLabel(year+"/"+month+"/"+day +" at " + hour_format, SwingConstants.CENTER);
        label0.setFont(new Font("Verdana", Font.PLAIN, 48));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0,20,20,20);  //top padding
        panel.add(label0, c);

        JLabel label1 = new JLabel("It is time to take your "+ treatment_name +" treatment", SwingConstants.CENTER);
        label1.setFont(new Font("Verdana", Font.PLAIN, 30));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0,20,20,20);  //top padding
        panel.add(label1, c);

        JLabel label2 = new JLabel("Remaining days : "+ remaining_days, SwingConstants.CENTER);
        label2.setFont(new Font("Verdana", Font.PLAIN, 30));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0,20,20,20);  //top padding
        panel.add(label2, c);

        JLabel label3 = new JLabel("Dosage : "+ treatment_dosage, SwingConstants.CENTER);
        label3.setFont(new Font("Verdana", Font.PLAIN, 30));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0,20,20,20);  //top padding
        panel.add(label3, c);


        //creating a button to confirm that we took our treatment
        button_confirm = new JButton("Confirm the take of the treatment");
        button_confirm.addActionListener((ActionListener) this);
        button_confirm.setFont(new Font("Verdana", Font.PLAIN, 20));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 100;      //make this component tall
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(0,20,20,20);  //top padding
        panel.add(button_confirm, c);




        //creating a button allowing to go back to the main menu
        button_return_to_menu = new JButton("Return to menu");
        button_return_to_menu.addActionListener((ActionListener) this);
        button_return_to_menu.setFont(new Font("Verdana", Font.PLAIN, 20));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 100;      //make this component tall
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(0,20,20,20);  //top padding
        panel.add(button_return_to_menu, c);


        frame.add(panel);
        frame.setVisible(true);

        frame.setSize(800, 800);
        frame.setTitle(treatment_name +" treatment");
        frame.setBackground(Color.GRAY);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for the exit cross

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //action when the button is clicked
        if (e.getSource() == button_return_to_menu) {
            frame.dispose();
            GUI menu_window = new GUI();
        }
        if(e.getSource() == button_confirm){
            this.treatment_taken = true;
            frame.dispose();
            GUI menu_window = new GUI();
            //et il faudrait qu'on retourne un truc qui dit que le traitment a été pris ...
        }
    }

    public Boolean getTreatment_taken() {
        return treatment_taken;
    }
}
