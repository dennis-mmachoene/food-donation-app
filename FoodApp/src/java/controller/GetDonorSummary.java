/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Donations;
import entity.Donor;
import entity.DonorDetails;
import entity.ProfilePicture;
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
public class GetDonorSummary extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        SummaryManager manager;
        
        try{
            manager = new SummaryManager();
            List<Donor> donor = manager.getDonors();
            List<DonorDetails> details = manager.getDonorDetails();
            List<ProfilePicture> pps = manager.getPPs();
            List<Donations> donations = manager.listings();
            
            request.setAttribute("donations", donations);
            request.setAttribute("donor", donor);
            request.setAttribute("details", details);
            request.setAttribute("pps", pps);
            request.getRequestDispatcher("donor_summary.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(GetDonorSummary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetDonorSummary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
