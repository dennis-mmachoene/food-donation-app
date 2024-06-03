/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.OrganizationsManager;

/**
 *
 * @author Dennis
 */
@MultipartConfig
public class RegisterOrgServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String org_name = request.getParameter("org_name");
        String description = request.getParameter("description");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String website = request.getParameter("url");

        Part part = request.getPart("image");

        byte[] image = getPartBytes(part);

        try {
            OrganizationsManager manager = new OrganizationsManager();

            if (manager.addAndOrg(org_name, description, phone, email, address, website, image)) {
                String message = "Organisation was successfully registered on the system. Check your emails for login details";
                request.setAttribute("message", message);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                String message = "An issue encountered please try again later";
                request.setAttribute("message", message);
                request.getRequestDispatcher("register_org.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegisterOrgServlet.class.getName()).log(Level.SEVERE, null, ex);
            String message = "Database Error";
                request.setAttribute("message", message);
                request.getRequestDispatcher("register_org.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterOrgServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private byte[] getPartBytes(Part part) throws IOException {

        InputStream is = part.getInputStream();
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        return buffer;
    }

}
