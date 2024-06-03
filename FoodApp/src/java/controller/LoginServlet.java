/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Donations;
import entity.Donor;
import entity.DonorDetails;
import entity.Organization;
import entity.ProfilePicture;
import entity.Requests;
import entity.Shift;
import entity.Volunteer;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import manager.LoginManager;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.AdminLogin;
import manager.DonorAccountManager;
import manager.ListingsManager;
import manager.OrganizationsManager;
import manager.RequestManager;
import manager.VolunteerAccountManager;

/**
 *
 * @author Dennis
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String usertype = request.getParameter("selectedUserType");

        switch (usertype) {
            case "donor": {
                Donor donor = new Donor(username, password);
                LoginManager manager;
                try {
                    manager = new LoginManager();
                    if (manager.checkUsername(usertype, donor, null)) {
                        if (manager.authenticateLogin(usertype, donor, null)) {
                            String email = manager.getEmail(usertype, donor.getUsername());
                            donor = new Donor(username, email, password);
                            List<Organization> organizations = new OrganizationsManager().getOrganizations();
                            DonorDetails donorDetails = new DonorAccountManager().getDetails(email);
                            List<Donations> listings = new ListingsManager().getListings(email);
                            List<String> org_names = new OrganizationsManager().OrganizationNames();

                            byte[] dp = new DonorAccountManager().getDP(email);
                            session.setAttribute("org_names", org_names);
                            session.setAttribute("image", dp);
                            session.setAttribute("listings", listings);
                            session.setAttribute("donor", donor);
                            session.setAttribute("donorDetails", donorDetails);
                            session.setAttribute("orgs", organizations);
                            request.getRequestDispatcher("newsfeed.jsp").forward(request, response);
                        } else {
                            String message = "Incorrect password";
                            request.setAttribute("message", message);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    } else {
                        String message = "The username doesn't exist, Create an account instead";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    String message = "Database error";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("index.jsp").forward(request, response);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case "volunteer": {
                Volunteer vol = new Volunteer(username, password);
                LoginManager manager;
                try {
                    manager = new LoginManager();
                    if (manager.checkUsername(usertype, null, vol)) {
                        if (manager.authenticateLogin(usertype, null, vol)) {
                            String email = manager.getEmail(usertype, vol.getUsername());
                            vol = new Volunteer(username, email, password);
                            List<Shift> shift = new VolunteerAccountManager().getShifts();
                            List<Shift> signedShifts = new VolunteerAccountManager().getSignedShifts(email);

                            List<DonorDetails> donors = new VolunteerAccountManager().donors();
                            List<ProfilePicture> pps = new VolunteerAccountManager().pps();
                            session.setAttribute("signedShifts", signedShifts);
                            session.setAttribute("donors", donors);
                            session.setAttribute("pps", pps);
                            session.setAttribute("shifts", shift);
                            session.setAttribute("volunteer", vol);
                            request.getRequestDispatcher("homefeed.jsp").forward(request, response);
                        } else {
                            String message = "Incorrect password";
                            request.setAttribute("message", message);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    } else {
                        String message = "The username doesn't exist, Create an account instead";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    String message = "Database error";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("index.jsp").forward(request, response);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case "beneficiary":
                OrganizationsManager org_man;
                try {
                    org_man = new OrganizationsManager();

                    if (org_man.checkUsername(username)) {
                        if (org_man.checkPassword(password)) {
                             
                            Organization org = new OrganizationsManager().getOrganization(username);
                            String to_location = org.getName();
                            List<Donations> listings = new ListingsManager().getDonationsByOrgName(to_location);
                              List<DonorDetails> donors = new VolunteerAccountManager().donors();
                            List<ProfilePicture> pps = new VolunteerAccountManager().pps();
                            List<Requests> reqs = new RequestManager().getRequests();
                            session.setAttribute("reqs", reqs);
                            session.setAttribute("donors", donors);
                            session.setAttribute("pps", pps);
                            session.setAttribute("listings", listings);
                            
                            session.setAttribute("org", org);
                            
                            request.getRequestDispatcher("org_homepage.jsp").forward(request, response);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                    String message = "Database error";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            default:
                AdminLogin admin;
                try {
                    admin = new AdminLogin();
                    if (admin.checkUsername(username)) {
                        if (admin.checkPassword(password)) {
                            request.getRequestDispatcher("admin_page.jsp").forward(request, response);
                        } else {
                            String message = "Incorrect password";
                            request.setAttribute("message", message);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    } else {
                        String message = "No such admin";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                    String message = "Database error";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                break;
        }
    }
}
