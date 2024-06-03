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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.SummaryManager;

/**
 *
 * @author Dennis
 */
public class GetSummaryServlet extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        SummaryManager manager;
        
        try{
            manager = new SummaryManager();
            
            int totalDonors = manager.getTotalDonor();
            int totalVolunteers = manager.getTotalVol();
            int totalOrganitions = manager.getTotalOrgs();
            int totalListings = manager.getTotalListings();
            int totalUsers = totalDonors+totalOrganitions+totalVolunteers;
            request.setAttribute("users", totalUsers);
            request.setAttribute("donors", totalDonors);
            request.setAttribute("volunteers", totalVolunteers);
            request.setAttribute("orgs", totalOrganitions);
            request.setAttribute("listings", totalListings);
            request.getRequestDispatcher("report.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(GetSummaryServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetSummaryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
