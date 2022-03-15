import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static  void main(String args[]){
        //GUI launchPage = new GUI(); //create an instance of the GUI starting on the main page
        Login launchPage = new Login();
        //GetHistoryTreatmentsUser(1);
    }

    public static Connection ConnectionTODB(String nameDB, String user, String password){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nameDB+"?characterEncoding=utf8", user, password);
            return con;

        }catch (Exception e) {
            System.out.println(e);
        }
        return null;
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