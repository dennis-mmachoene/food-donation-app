/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Donor;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.DonorAccountManager;

@MultipartConfig
public class UploadPictureServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession();
        Donor donor = (Donor) session.getAttribute("donor");
        Part part = request.getPart("profile_pic");
        byte[] image = getPartBytes(part);
        String email = donor.getEmail_address();
        
        DonorAccountManager manager;
        
        try{
            manager = new DonorAccountManager();
            
            manager.uploadPP(email, image);
            byte[] dp = manager.getDP(email);
            session.setAttribute("image", dp);
            request.getRequestDispatcher("account.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UploadPictureServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UploadPictureServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private byte[] getPartBytes(Part part) throws IOException{
        InputStream is = part.getInputStream();
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        return buffer;
    }
}
