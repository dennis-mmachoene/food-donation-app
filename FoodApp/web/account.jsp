<%@page import="entity.Donor"%>
<%@page import="entity.DonorDetails" %>
<%@page import="entity.Donations"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Base64"%>
<%@page import="java.util.Date" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                margin: 0;
                padding: 0;
            }
            .container {
                max-width: 800px;
                margin: 0 auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .profile-pic {
                text-align: center;
                margin-bottom: 20px;
            }
            .profile-pic img {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                border: 3px solid #fff;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .profile-pic form {
                margin-top: 10px;
            }
            .logout-btn {
                background-color: #dc3545;
                border: none;
                color: white;
                padding: 8px 16px;
                border-radius: 5px;
                text-decoration: none;
                cursor: pointer;
            }
            .logout-btn:hover {
                background-color: #c82333;
            }
            .personal-details {
                padding: 20px;
                background-color: #f7f7f7;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
            }
            .personal-details p {
                margin: 5px 0;
            }
            .details-btn,
            .update-btn {
                background-color: #007bff;
                border: none;
                color: white;
                padding: 8px 16px;
                border-radius: 5px;
                text-decoration: none;
                cursor: pointer;
            }
            .details-btn:hover,
            .update-btn:hover {
                background-color: #0056b3;
            }
            .listings-container {
                background-color: #f7f7f7;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            h1 {
                margin-top: 0;
            }
            .listing {
                background-color: #fff;
                padding: 15px;
                border-radius: 5px;
                margin-bottom: 15px;
                box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
            }
            table {
                width: 100%;
            }
            td {
                vertical-align: top;
                padding: 5px;
            }
            .food-image {
                max-width: 100%;
                height: auto;
            }
            .button,
            .update-btn,
            .details-btn,
            .edit-btn,
            .logout-btn {
                display: inline-block;
                padding: 8px 16px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                text-align: center;
                text-decoration: none;
            }

            .button {
                background-color: #007bff;
                color: white;
            }

            .update-btn,
            .details-btn {
                background-color: #28a745;
                color: white;
            }

            .edit-btn {
                background-color: #ffc107;
                color: #333;
            }

            .logout-btn {
                background-color: #dc3545;
                color: white;
            }

            /* Hover effects */
            .button:hover,
            .update-btn:hover,
            .details-btn:hover,
            .edit-btn:hover,
            .logout-btn:hover {
                opacity: 0.8;
            }

            /* Aligning buttons */
            .profile-actions {
                text-align: center;
                margin-top: 10px;
            }

            .profile-actions form,
            .profile-actions a {
                margin-right: 10px;
            }

            .personal-details form,
            .personal-details form input[type="submit"] {
                display: inline-block;
                margin-right: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <% 
                Donor donor = (Donor) session.getAttribute("donor");
                String name = "";
                String phone = "";
                String address = "";

                String img = "CSS/Pictures/images.png";  

                DonorDetails details = (DonorDetails) session.getAttribute("donorDetails");
                if(details != null){
                    name = details.getNames();
                    phone = details.getPhone();
                    address = details.getAddress();
                }
                byte[] dp = (byte[]) session.getAttribute("image");
            
    if(dp != null){
    img = "data:image/*;base64," + Base64.getEncoder().encodeToString(dp);
                }
            %>
            <div class="profile-pic">

                <img src="<%=img%>">
                <div class="profile-actions">
                    <form action="choose_pp.jsp" method="POST">
                        <input type="submit" value="Update Profile Picture" class="update-btn"/>
                    </form>
                    <p></p>
                    <a href="index.jsp" class="update-btn">LOG OUT</a>
                </div>
                <p><%=donor.getUsername()%></p>
            </div>
            <div class="personal-details">
                <p><strong>Name:</strong> <%=name%></p>
                <p><strong>Email:</strong> <%=donor.getEmail_address()%></p>
                <p><strong>Phone:</strong> <%=phone%></p>
                <p><strong>Address:</strong> <%=address%></p>
                <% if(name.equals("") || phone.equals("") || address.equals("")){ %>
                <form action="add_details.jsp" method="POST">
                    <input type="submit" value="ADD DETAILS" class="details-btn"/>
                </form>
                <% }else if(!(name.equals("") || phone.equals("") || address.equals(""))){ %>
                <form action="update_details.jsp" method="POST">
                    <input type="hidden" name="op" value="update_details"/>
                    <input type="submit" value="UPDATE DETAILS" class="update-btn"/>
                </form>
                <%}%>
                <form action="DeleteAccountServlet.do" method="GET">
                    <input type="submit" value="DELETE ACCOUNT" class="update-btn">
                </form>
            </div>
            <div class="listings-container">
                <h1>Donation Listings</h1>
                <%
                List<Donations> list = (List<Donations>) session.getAttribute("listings");
                for (Donations listi : list) {
                Donations listing = listi;
                    String email = listing.getEmail();
                    String pickup_location = listing.getPickup_location();
                    String items = listing.getItems();
                    String to_location = listing.getTo_location();
                    String pickup_time = listing.getPickup_time();
                    String pickup_date = listing.getPickup_date();
                    String image = "data:image/*;base64," + Base64.getEncoder().encodeToString(listing.getFoodImage());
                    Integer index = listing.getIndex();
                    Boolean collected = listing.getCollected();
%>
                <div class="listing">
                    <table>
                        <tr>
                            <td><p><strong>To:</strong> <%= to_location %></p>
                                <p><strong>Donor Email:</strong> <%= email %></p>
                               
                                <p><strong>Items:</strong> <%= items %></p>
                                 <p><strong>Pickup Location:</strong> <%= pickup_location %></p>
                                 <p><strong>Pickup date: <%=pickup_date%></strong></p>
                                <p><strong>Pickup Time:</strong> <%= pickup_time %></p>
                            </td>
                            <td><img src="<%= image %>" alt="Food Image" class="food-image"/></td>
                        </tr>
                    </table>
                        <%if(collected){
                        %> <h4>Package has been collected.</h4>
                            <%}else{%>
                            <h4>Package is pending collection.</h4>
                    <form action="DonationListServlet.do" method="POST">
                        <input type="hidden" name="op" value="collected"/>
                        <input type="hidden" name="index" value="<%=index%>"/>
                        <input type="submit" value="MARK COLLECTED" class="edit-btn"/>
                    </form>
                </div>
                <% } }%>
            </div>
            <a href="newsfeed.jsp">Back</a>
        </div>
    </body>
</html>
