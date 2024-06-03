package manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import encryption.PasswordManager;

public class PasswordDBManager {

    private Connection connection;

    public PasswordDBManager() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = getConnection();
    }

    public boolean checkEmail(String usertype, String checkEmail) throws SQLException {
        List<String> emails = new ArrayList<>();
        try (PreparedStatement ps = usertype.equals("donor")
                ? connection.prepareStatement("SELECT EMAIL_ADDRESS FROM DONOR_LOGIN")
                : connection.prepareStatement("SELECT EMAIL_ADDRESS FROM VOLUNTEER_LOGIN")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String email = rs.getString("EMAIL_ADDRESS");
                    emails.add(email);
                }
            }
        }
        for (String email : emails) {
            if (email.equals(checkEmail)) {
                return true;
            }
        }
        return false;
    }

    public void update(String usertype, String email, String password) throws SQLException, Exception {
        if (usertype.equals("donor")) {
            PreparedStatement ps = connection.prepareStatement("UPDATE DONOR_LOGIN SET PASSWORD = ? WHERE EMAIL_ADDRESS = ?");
            ps.setString(1, new PasswordManager().encrypt(password));
            ps.setString(2, email);
            ps.executeUpdate();
        } else {
            PreparedStatement ps = connection.prepareStatement("UPDATE VOLUNTEER_LOGIN SET PASSWORD = ? WHERE EMAIL_ADDRESS = ?");
            ps.setString(1, new PasswordManager().encrypt(password));
            ps.setString(2, email);
            ps.executeUpdate();
        }
    }

   private Connection getConnection() throws SQLException{
       return DriverManager.getConnection("jdbc:mysql://192.168.231.179:3306/donnationappassesment5?useSSL=false&&allowPublicKeyRetrieval=true", "app", "123");
 }

}
