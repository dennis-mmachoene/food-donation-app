<%@page import="entity.Donations" %>
<%@page import="entity.Organization" %>
<%@page import="java.util.Base64" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        header {
            background-color: #333;
            color: #fff;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .app-name {
            font-size: 24px;
            font-weight: bold;
            margin: 0;
        }
        .user-info {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .username {
            font-size: 18px;
        }
        .profile-pic img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            object-fit: cover;
        }
        main {
            flex: 1;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }
        .listing {
            background-color: #fff;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            width: 100%;
            max-width: 600px;
        }
        table {
            width: 100%;
        }
        td {
            vertical-align: top;
            padding: 10px;
        }
        .food-image {
            max-width: 100px;
            border-radius: 8px;
        }
        .edit-btn, .logout-btn, .donation-btn {
            background-color: #007bff;
            border: none;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin: 5px 0;
        }
        .edit-btn:hover, .logout-btn:hover, .donation-btn:hover {
            background-color: #0056b3;
        }
        h1 {
            margin-top: 0;
        }
    </style>
</head>
<body>
    <% Organization org = (Organization) session.getAttribute("org"); 
      String username = org.getName();
      String image = "data:image/*;base64," + Base64.getEncoder().encodeToString(org.getImage());
    %>
    <header>
        <div class="app-name">NourishLink</div>
        <div class="user-info">
            <div class="username"><%=username%></div>
            <div class="profile-pic">
                <img src="<%=image%>" alt="Profile Picture">
            </div>
        </div>
    </header>
    <main>
        <a href="choose_donor.jsp" class="donation-btn">SEND DONATION REQUEST</a>
        <% List<Donations> listings = (List<Donations>) session.getAttribute("listings"); 
        if(listings != null && listings.size() != 0){
        %><h1>Donations listed to your organization</h1><%
           for(Donations listing: listings){
            String email = listing.getEmail();
            String pickup_location = listing.getPickup_location();
            String items = listing.getItems();
            String to_location = listing.getTo_location();
            String pickup_time = listing.getPickup_time();
            String pickup_date = listing.getPickup_date();
            String img = "data:image/*;base64," + Base64.getEncoder().encodeToString(listing.getFoodImage());
            Integer index = listing.getIndex();
            Boolean recieved = listing.getReceived();
        %>
                <div class="listing">
                    <table>
                        <tr>
                            <td>
                                <p><strong>To:</strong> <%= to_location %></p>
                                <p><strong>Donor Email:</strong> <%= email %></p>
                                <p><strong>Items:</strong> <%= items %></p>
                                <p><strong>Pickup Location:</strong> <%= pickup_location %></p>
                                <p><strong>Pickup Date:</strong> <%= pickup_date %></p>
                                <p><strong>Pickup Time:</strong> <%= pickup_time %></p>
                            </td>
                            <td><img src="<%= img %>" alt="Food Image" class="food-image"/></td>
                        </tr>
                    </table>
                        <%if(recieved){%> 
                        <h4>Package was received, provide feedback...</h4>
                        <%}else{%>
                    <form action="DonationListServlet.do" method="POST">
                        <input type="hidden" name="index" value="<%=index%>"/>
                        <input type="hidden" name="name" value="<%=username%>"/>
                        <input type="hidden" name="op" value="received"/>
                        <input type="submit" value="Mark it received" class="edit-btn"/>
                    </form>
                        <%}%>
                </div>
        <%}
}else{
        %><h1>No donation has been listed yet</h1><%
}%>
 <a href="index.jsp" class="logout-btn">Sign out</a>
    </main>
    
   
</body>
</html>
