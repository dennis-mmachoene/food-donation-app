/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Shift;
import entity.Volunteer;
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
import manager.VolunteerAccountManager;

/**
 *
 * @author Dennis
 */
public class SignUpForShiftServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String index = request.getParameter("index");
        String op = request.getParameter("op");
        int shiftNumber = Integer.valueOf(index);

        HttpSession session = request.getSession();
        Volunteer vol = (Volunteer) session.getAttribute("volunteer");
        String email = vol.getEmail_address();

        VolunteerAccountManager manager;

        if (op.equals("signup")) {
            try {
                manager = new VolunteerAccountManager();

                manager.markShiftUnAV(shiftNumber, email);
                String message = "Shift succesfully signed up for";
                List<Shift> shift = (List<Shift>) session.getAttribute("shifts");

                shift = new VolunteerAccountManager().getShifts();

                List<Shift> signedShifts = (List<Shift>) session.getAttribute("signedShifts");
                signedShifts = manager.getSignedShifts(email);
                session.setAttribute("signedShifts", signedShifts);
                session.setAttribute("shifts", shift);
                request.setAttribute("message", message);
                request.getRequestDispatcher("homefeed.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(SignUpForShiftServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SignUpForShiftServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (op.equals("complete")) {

            try {
                manager = new VolunteerAccountManager();
                manager.completedShift(email, shiftNumber);
                  List<Shift> shift = (List<Shift>) session.getAttribute("shifts");

                shift = new VolunteerAccountManager().getShifts();

                List<Shift> signedShifts = (List<Shift>) session.getAttribute("signedShifts");
                signedShifts = manager.getSignedShifts(email);
                session.setAttribute("signedShifts", signedShifts);
                session.setAttribute("shifts", shift);
                
                request.getRequestDispatcher("homefeed.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(SignUpForShiftServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SignUpForShiftServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                manager = new VolunteerAccountManager();
                manager.cancelShift(email, shiftNumber);
                  List<Shift> shift = (List<Shift>) session.getAttribute("shifts");

                shift = new VolunteerAccountManager().getShifts();

                List<Shift> signedShifts = (List<Shift>) session.getAttribute("signedShifts");
                signedShifts = manager.getSignedShifts(email);
                session.setAttribute("signedShifts", signedShifts);
                session.setAttribute("shifts", shift);
                
                request.getRequestDispatcher("homefeed.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(SignUpForShiftServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SignUpForShiftServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
