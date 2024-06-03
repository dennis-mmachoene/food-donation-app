/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;
import encryption.PasswordManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AdminLogin {
    private Connection connection;
    
    public AdminLogin() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = getConnection();
    }
    private List<String> getUsernames() throws SQLException{
        List<String> usernames = new ArrayList<>();
        
        PreparedStatement ps = connection.prepareStatement("SELECT USERNAME FROM ADMIN_LOGIN");
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            usernames.add(rs.getString("USERNAME"));
        }
        return usernames;
    }
    private List<String> getPasswords() throws SQLException, Exception{
        List<String> passwords = new ArrayList<>();
        
        PreparedStatement ps = connection.prepareStatement("SELECT PASSWORD FROM ADMIN_LOGIN");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            passwords.add(new PasswordManager().decrypt(rs.getString("PASSWORD")));
        }
        return passwords;
    }
    
    public boolean checkUsername(String username) throws SQLException{
        for(String user_name : getUsernames()){
            if(user_name.equals(username)){
                return true;
            }
        }
        return false;
    }
    public boolean checkPassword(String password) throws SQLException, Exception{
        for(String pass_word: getPasswords()){
            if(pass_word.equals(password)){
                return true;
            }
        }
        return false;
    }
    private Connection getConnection() throws SQLException{
       return DriverManager.getConnection("jdbc:mysql://192.168.231.179:3306/donnationappassesment5?useSSL=false&&allowPublicKeyRetrieval=true", "app", "123");
 }
}
