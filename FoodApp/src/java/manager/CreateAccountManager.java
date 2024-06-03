package manager;

import encryption.PasswordEncrypterContoller;
import encryption.PasswordManager;
import entity.Donor;
import entity.Volunteer;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CreateAccountManager {

    private PasswordManager pec;
    private Connection connection;

    public CreateAccountManager() throws SQLException ,ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
         /*Class.forName("org.apache.derby.jdbc.ClientDriver");*/
        connection = getConnection();
        pec = new PasswordManager(); // Initialize PasswordEncrypterContoller
    }

    public boolean addNewUser(String usertype, Donor donor, Volunteer volunteer) throws SQLException, Exception {

        boolean isAdded = false;

        if (usertype.equals("donor")) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO DONOR_LOGIN VALUES(?,?,?,?)")) {
                ps.setString(1, donor.getEmail_address());
                ps.setString(2, donor.getUsername());
                ps.setString(3, pec.encrypt(donor.getPassword()));
                ps.setTimestamp(4, Timestamp.from(Instant.now()));

                int i = ps.executeUpdate();
                if (i > 0) {
                    isAdded = true;
                }
            }
        } else {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO VOLUNTEER_LOGIN VALUES(?,?,?,?)")) {
                ps.setString(1, volunteer.getEmail_address());
                ps.setString(2, volunteer.getUsername());
                ps.setString(3, pec.encrypt(volunteer.getPassword()));
                ps.setTimestamp(4, Timestamp.from(Instant.now()));
                int i = ps.executeUpdate();
                if (i > 0) {
                    isAdded = true;
                }
            }
        }
        return isAdded;
    }

    public List<String> getEmails(String usertype) throws SQLException {
        List<String> emails = new ArrayList<>();
        try (PreparedStatement ps = usertype.equals("donor") ?
                connection.prepareStatement("SELECT EMAIL_ADDRESS FROM DONOR_LOGIN") :
                connection.prepareStatement("SELECT EMAIL_ADDRESS FROM VOLUNTEER_LOGIN")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String email = rs.getString("EMAIL_ADDRESS");
                    emails.add(email);
                }
            }
        }
        return emails;
    }
  public List<String> getUsername(String usertype ) throws SQLException{
      List<String> usernames = new ArrayList<>();
      try (PreparedStatement ps = usertype.equals("donor") ?
                connection.prepareStatement("SELECT USERNAME FROM DONOR_LOGIN") :
                connection.prepareStatement("SELECT USERNAME FROM VOLUNTEER_LOGIN")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("USERNAME");
                    usernames.add(username);
                }
            }
      }
      return usernames;
  }
   private Connection getConnection() throws SQLException{
  return DriverManager.getConnection("jdbc:mysql://192.168.231.179:3306/donnationappassesment5?useSSL=false&&allowPublicKeyRetrieval=true", "app", "123");
  }
  /*private Connection getConnection() throws SQLException{
  return DriverManager.getConnection("jdbc:derby://192.168.231.179:1527/FoodAppDB;ssl=basic", "app", "123");
  }*/
}
