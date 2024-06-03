package controller;

import entity.Donor;
import entity.Volunteer;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manager.CreateAccountManager;
import java.sql.SQLException;

/**
 *
 * @author Dennis
 */
public class CreateAccountServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usertype = request.getParameter("usertype");
        String username = request.getParameter("username");
        String email = request.getParameter("email-address");
        String password = request.getParameter("password");
        System.out.println(usertype);
        CreateAccountManager cam;
        boolean emailRegistered = false;
     
        try{
            cam = new CreateAccountManager();
            
            if(usertype.equals("donor")){
                for(String dbEmail: cam.getEmails(usertype)){
                    if(dbEmail.equals(email)){
                        emailRegistered = true;
                    }
                }
                
                if(emailRegistered){
                    String message = "The email address is already associated with another donor account.";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("create_donor_account.jsp").forward(request, response);
                }else{
                    
                    Donor donor = new Donor(username, email, password);
                    
                    if(cam.addNewUser(usertype, donor, null)){
                        String message = "Account was succesfully created.";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }else{
                        String message = "An issue was encountered while creating your account. Please try again later";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("create_donor_account.jsp").forward(request, response);
                    }
                }
            }else{
                for(String dbEmail: cam.getEmails(usertype)){
                    if(dbEmail.equals(email)){
                        emailRegistered = true;
                    }
                }
                if(emailRegistered){
                    String message = "The email address is already associated with another volunteer account";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("create_volunteer_account.jsp").forward(request, response);
                }else{
                    Volunteer vol = new Volunteer(username, email, password);
                    
                    if(cam.addNewUser(usertype, null, vol)){
                        String message = "Account was succesfully created";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }else{
                       String message = "An issue was encountered while creating your account. Please try again later";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("create_volunteer_account").forward(request, response);
                    }
                }
            }
        }catch(SQLException ex){
            
            String message = "An error was encountered within the database, please try again later";
            request.setAttribute("message", message);
            ex.printStackTrace();
            if(usertype.equals("donor")){
                request.getRequestDispatcher("create_donor_account.jsp").forward(request, response);
            }else{
                request.getRequestDispatcher("create_volunteer_account.jsp").forward(request, response);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
