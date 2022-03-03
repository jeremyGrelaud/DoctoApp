import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TutorWindow implements ActionListener {

    private JFrame frame = new JFrame();
    private JButton button_return_to_menu;

    TutorWindow() {
        //creating a button allowing to go back to the main menu
        button_return_to_menu = new JButton("Return to menu");
        button_return_to_menu.setSize(200, 80);
        button_return_to_menu.setLocation(300, 50);
        button_return_to_menu.addActionListener(this);
        frame.add(button_return_to_menu);


        frame.add(button_return_to_menu);
        frame.setLayout(null);
        frame.setVisible(true);

        frame.setSize(800, 800);
        frame.setTitle("Table Product");
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
    }
}

