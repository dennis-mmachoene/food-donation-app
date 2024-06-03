/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Dennis
 */
public class CheckUserTypeServlet extends HttpServlet {

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException{
       HttpSession session =request.getSession(true);
       
       String usertype = request.getParameter("selectedUserType");
       String url = "";
       request.setAttribute("usertype", usertype);
       
       switch (usertype) {
           case "donor":
               url = "create_donor_account.jsp";
               break;
           case "beneficiary":
               url = "register_org.jsp";
               break;
           case "volunteer":
               url = "create_volunteer_account.jsp";
               break;
           default:
               String message = "You cannot create an account as an administrator";
               request.setAttribute("message", message);
               url = "index.jsp";
               break;
       }
       request.getRequestDispatcher(url).forward(request, response);
   }
}
