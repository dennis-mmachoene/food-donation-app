package manager;

import encryption.PasswordManager;
import entity.Donor;
import entity.Volunteer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginManager {

    private Connection connection;
    private PasswordManager pec;

    public LoginManager() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = getConnection();
        pec = new PasswordManager();
    }

    private List<String> getUsername(String usertype) throws SQLException {
        List<String> usernames = new ArrayList<>();
        try (PreparedStatement ps = usertype.equals("donor")
                ? connection.prepareStatement("SELECT USERNAME FROM DONOR_LOGIN")
                : connection.prepareStatement("SELECT USERNAME FROM VOLUNTEER_LOGIN")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("USERNAME");
                    usernames.add(username);
                }
            }
        }
        return usernames;
    }

    private List<String> getPassowrds(String usertype) throws SQLException, Exception {
        List<String> passwords = new ArrayList<>();
        try (PreparedStatement ps = usertype.equals("donor")
                ? connection.prepareStatement("SELECT PASSWORD FROM DONOR_LOGIN")
                : connection.prepareStatement("SELECT PASSWORD FROM VOLUNTEER_LOGIN")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String password = pec.decrypt(rs.getString("PASSWORD"));
                    passwords.add(password);

                }
            }
        }
        return passwords;
    }
    public String getEmail(String usertype, String username) throws SQLException{
        String email = "";
        
        if(usertype.equals("donor")){
            PreparedStatement ps = connection.prepareStatement("SELECT EMAIL_ADDRESS FROM DONOR_LOGIN WHERE USERNAME = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                email = rs.getString("EMAIL_ADDRESS");
            }
        }
        else{
            PreparedStatement ps = connection.prepareStatement("SELECT EMAIL_ADDRESS FROM VOLUNTEER_LOGIN WHERE USERNAME = ? ");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                email = rs.getString("EMAIL_ADDRESS");
            }
        }
        return email;
    }

    public boolean checkUsername(String usertype, Donor donor, Volunteer vol) throws SQLException {
        List<String> usernames = getUsername(usertype);

        if (usertype.equals("donor")) {
            for (String user_name : usernames) {
                if (user_name.equals(donor.getUsername())) {
                    return true;
                }
            }
        } else {
            for (String user_name : usernames) {
                if (user_name.equals(vol.getUsername())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean authenticateLogin(String usertype, Donor donor, Volunteer vol) throws SQLException, Exception {
        List<String> passwords = getPassowrds(usertype);

        if (usertype.equals("donor")) {
            for (String pass_word : passwords) {
                if (pass_word.equals(donor.getPassword())) {
                    return true;
                }
            }
        } else {
            for (String pass_word : passwords) {
                if (pass_word.equals(vol.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }
    

    private Connection getConnection() throws SQLException{
       return DriverManager.getConnection("jdbc:mysql://192.168.231.179:3306/donnationappassesment5?useSSL=false&&allowPublicKeyRetrieval=true", "app", "123");
 }

    
}
