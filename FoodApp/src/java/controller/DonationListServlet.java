package controller;

import entity.Donations;
import entity.Donor;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.ListingsManager;

@MultipartConfig
public class DonationListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String op = request.getParameter("op");
        if(op.equals("add")){
        String pickup_location = request.getParameter("pickup-location");
        String items = request.getParameter("items");
        String to_location = request.getParameter("beneficiary");
        String ddate = request.getParameter("pickup_date");
        
        String pickup_time = request.getParameter("pickup-time");

        Part imageFile = request.getPart("image");
        String fileName = imageFile.getSubmittedFileName();
        byte[] image = imageBytes(imageFile);
        Donor donor = (Donor) session.getAttribute("donor");

        String email = donor.getEmail_address();

        try {
            ListingsManager manager = new ListingsManager();
            manager.addDonationList(email, to_location, items, pickup_location, ddate, pickup_time, image);

            List<Donations> listings = manager.getListings(email);
            session.setAttribute("listings", listings);
            request.getRequestDispatcher("newsfeed.jsp").forward(request, response);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }   catch (ClassNotFoundException ex) {
                Logger.getLogger(DonationListServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
    }else if(op.equals("collected")){
            String i = request.getParameter("index");
            int index = Integer.valueOf(i);
            
            try{
                ListingsManager manager = new ListingsManager();
                manager.update(index);
                Donor donor = (Donor) session.getAttribute("donor");
                String email = donor.getEmail_address();
                  List<Donations> listings = (List<Donations>) session.getAttribute("listings");
                  listings = new ListingsManager().getListings(email);
                session.setAttribute("listings", listings);
                request.getRequestDispatcher("account.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(DonationListServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DonationListServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
             String i = request.getParameter("index");
            int index = Integer.parseInt(i);
            String username = request.getParameter("username");
            try{
                ListingsManager manager = new ListingsManager();
                manager.received(index);
               List<Donations> listings = (List<Donations>) session.getAttribute("listings");
                  listings = manager. getDonationsByOrgName(username);
                session.setAttribute("listings", listings);
                request.getRequestDispatcher("org_homepage.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(DonationListServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DonationListServlet.class.getName()).log(Level.SEVERE, null, ex);
            } 
            }
    }

    private byte[] imageBytes(Part part) throws IOException {
        InputStream is = part.getInputStream();

        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        return buffer;
    }
}
