/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import encryption.PasswordManager;
import entity.Organization;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrganizationsManager {

    private Connection connection;

    public OrganizationsManager() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = getConnection();
    }

    public boolean addAndOrg(String name, String description, String phone, String email, String address, String url, byte[] logo)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO FOOD_ORGANIZATIONS VALUES(?,?,?,?,?,?,?,?)");
        ps.setString(1, name);
        ps.setString(2, description);
        ps.setString(3, phone);
        ps.setString(4, email);
        ps.setString(5, address);
        ps.setString(6, url);
        ps.setBytes(7, logo);
        ps.setTimestamp(8, Timestamp.from(Instant.now()));
        int i = ps.executeUpdate();

        if (i > 0) {
            try {
                PreparedStatement pps = connection.prepareStatement("INSERT INTO ORG_LOGIN VALUES(?,?,?)");
                pps.setString(1, email);
                pps.setString(2, email);
                pps.setString(3, new PasswordManager().encrypt(email));
                pps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return i > 0;
    }

    public List<Organization> getOrganizations() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM FOOD_ORGANIZATIONS");

        List<Organization> organizations = new ArrayList();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String name = rs.getString("NAME");
            String description = rs.getString("DESCRIPTION");
            String phone = rs.getString("PHONE");
            String email = rs.getString("EMAIL");
            String address = rs.getString("ADDRESS");
            String link = rs.getString("LINK");
            byte[] image = rs.getBytes("LOGO_IMAGE");

            organizations.add(new Organization(name, description, phone, email, address, link, image));
        }
        return organizations;
    }

    public Organization getOrganization(String e_mail) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM FOOD_ORGANIZATIONS WHERE EMAIL = ?");
        ps.setString(1, e_mail);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String name = rs.getString("NAME");
            String description = rs.getString("DESCRIPTION");
            String phone = rs.getString("PHONE");
            String email = rs.getString("EMAIL");
            String address = rs.getString("ADDRESS");
            String link = rs.getString("LINK");
            byte[] image = rs.getBytes("LOGO_IMAGE");

            return new Organization(name, description, phone, email, address, link, image);
        }
        return null;
    }

    public List<String> OrganizationNames() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT NAME FROM FOOD_ORGANIZATIONS");
        List<String> names = new ArrayList<>();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String name = rs.getString("NAME");
            names.add(name);
        }
        return names;
    }

   private Connection getConnection() throws SQLException{
       return DriverManager.getConnection("jdbc:mysql://192.168.231.179:3306/donnationappassesment5?useSSL=false&&allowPublicKeyRetrieval=true", "app", "123");
 }


    public String org_address(String org_name) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT ADDRESS FROM FOOD_ORGANIZATIONS WHERE NAME = ?");
        ps.setString(1, org_name);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getString("ADDRESS");
        }
        return null;
    }

    public boolean checkUsername(String username) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT USERNAME FROM ORG_LOGIN");
        List<String> usernames = new ArrayList<>();

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String user_name = rs.getString("USERNAME");
            usernames.add(user_name);

        }

        for (String user_name : usernames) {
            if (user_name.equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPassword(String password) throws SQLException, Exception {
        PreparedStatement ps = connection.prepareStatement("SELECT PASSWORD FROM ORG_LOGIN");
        List<String> passwords = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String pass_word = new PasswordManager().decrypt(rs.getString("PASSWORD"));
            passwords.add(pass_word);
        }

        for (String pass_word : passwords) {
            if (pass_word.equals(password)) {
                return true;
            }
        }
        return false;
    }
}
