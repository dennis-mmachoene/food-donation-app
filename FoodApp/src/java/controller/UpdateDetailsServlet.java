/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Donor;
import entity.DonorDetails;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.DonorAccountManager;

/**
 *
 * @author Dennis
 */
public class UpdateDetailsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("op");
        if (op.equals("add_details")) {
            String names = request.getParameter("names");

            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            HttpSession session = request.getSession();

            Donor donor = (Donor) session.getAttribute("donor");
            String email = donor.getEmail_address();

            DonorDetails details = new DonorDetails(email, names, phone, address);

            DonorAccountManager manager;
            session.setAttribute("donorDetails", details);
            try {
                manager = new DonorAccountManager();

                manager.addDetails(details);
                request.getRequestDispatcher("account.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UpdateDetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UpdateDetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String names = request.getParameter("names");

            String phone = request.getParameter("phone");
            String address = request.getParameter("address");

            if (names.equals("") && phone.equals("") && address.equals("")) {
                request.getRequestDispatcher("account.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession();
                DonorDetails details = (DonorDetails) session.getAttribute("donorDetails");

                if (!names.equals("")) {
                    details.setNames(names);
                }
                if (!phone.equals("")) {
                    details.setPhone(phone);
                }
                if (!address.equals("")) {
                    details.setAddress(address);
                }
                DonorAccountManager manager;
                
                try{
                    manager = new DonorAccountManager();
                    
                    manager.updateDetails(details);
                    session.setAttribute("donorDetails", details);
                    request.getRequestDispatcher("account.jsp").forward(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateDetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UpdateDetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }
}
