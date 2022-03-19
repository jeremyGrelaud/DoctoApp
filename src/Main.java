import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static  void main(String args[]){
        //create an instance of the Login class creating the login page
        Login launchPage = new Login();
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
}