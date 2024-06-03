/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Donor;
import entity.Requests;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.RequestManager;

/**
 *
 * @author Dennis
 */
public class DonationRequestServlet extends HttpServlet {

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException{
       HttpSession session = request.getSession();
       
       Donor donor = (Donor) session.getAttribute("donor");
       String email = donor.getEmail_address();
       RequestManager manager;
       
       try{
           manager = new RequestManager();
           
           List<Requests> reqs = manager.getRequests(email);
           session.setAttribute("reqs", reqs);
           request.getRequestDispatcher("requests.jsp").forward(request, response);
       } catch (SQLException ex) {
           Logger.getLogger(DonationRequestServlet.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(DonationRequestServlet.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
}
