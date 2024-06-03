/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import manager.PasswordDBManager;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dennis
 */
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("op");
        PasswordDBManager manager;
        if(op.equals("check_email")){
            String usertype = request.getParameter("user_type");
            String email = request.getParameter("email");
            
            try{
                manager = new PasswordDBManager();
                
                if(manager.checkEmail(usertype, email)){
                    request.setAttribute("email", email);
                    request.setAttribute("usertype", usertype);
                    request.getRequestDispatcher("new_password_entry.jsp").forward(request, response);
                }else{
                    String message = "No " + usertype + " with that email ("+ email+")";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String usertype = request.getParameter("usertype");
             try{
                 manager = new PasswordDBManager();
                  
                 manager.update(usertype, email, password);
                 request.getRequestDispatcher("index.jsp").forward(request, response);
             } catch (SQLException ex) {
                Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }catch(Exception e){
                
            }
        }
    }
}
