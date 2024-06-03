/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Requests;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.RequestManager;
/**
 *
 * @author Dennis
 */
public class AddRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
       HttpSession session = request.getSession();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        RequestManager manager;
        try{
            manager = new RequestManager();
            manager.doRequest(name, email);
            List<Requests> reqs = (List<Requests>) session.getAttribute("reqs");
            reqs = manager.getRequests();
            session.setAttribute("reqs", reqs);
            request.getRequestDispatcher("choose_donor.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddRequestServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddRequestServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
