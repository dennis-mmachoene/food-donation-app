/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Donations;
import entity.Organization;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.SummaryManager;

/**
 *
 * @author Dennis
 */
public class GetOrgs extends HttpServlet {

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException{
       try{
           List<Organization> orgs = new SummaryManager().organizations();
           List<Donations> listings = new SummaryManager().listings();
           request.setAttribute("orgs", orgs);
           request.setAttribute("listings", listings);
           request.getRequestDispatcher("orgs_summary.jsp").forward(request, response);
       } catch (SQLException ex) {
           Logger.getLogger(GetOrgs.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(GetOrgs.class.getName()).log(Level.SEVERE, null, ex);
       }
               
   }
}
