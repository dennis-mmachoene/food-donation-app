<%@page import="java.util.List" %>
<%@page import="entity.Shift"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
    <style>
        /* Reset CSS */
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
        }

        /* Menu Bar */
        .menu-bar {
            background-color: #333;
            overflow: hidden;
        }

        .menu-bar a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .menu-bar a:hover {
            background-color: #111;
        }

        .menu-bar .active {
            background-color: #555;
        }

        /* Container */
        .container {
            padding: 20px;
            margin: 20px auto;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            max-width: 800px;
        }

        /* Shifts */
        .shifts {
            border-bottom: 1px solid #ccc;
            padding: 20px 0;
        }

        .shifts:last-child {
            border-bottom: none;
        }

        .shifts h2 {
            font-size: 20px;
            margin-bottom: 10px;
            color: #333;
        }

        .shifts p {
            margin-bottom: 10px;
            color: #666;
        }

        .shifts strong {
            color: #333;
        }

        .shifts a {
            display: inline-block;
            background-color: #4CAF50;
            color: white;
            text-align: center;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .shifts a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<div class="menu-bar">
    <a href="homefeed.jsp" class="active">Home</a>
    <a href="signed_shifts.jsp">View Signed Shifts</a>
    <a href="view_donors.jsp">View Donors</a>
    <a href="index.jsp">Sign Out</a>
</div>

<div class="container">
    <h1>Shifts that are available to sign up for.</h1>
    <h4>The shift will be removed from home, after signup button is clicked.</h4>
    <% List<Shift> shifts = (List<Shift>) session.getAttribute("shifts");
    
    if(shifts != null){
        for(Shift shift: shifts){
            String email = shift.getEmail();
            String pickup_location = shift.getPickup_location();
            String dropoff_org = shift.getDropoff_org();
            String dropoff_location = shift.getDropoff_location();
            String pickup_time = shift.getPickup_time();
            String pickup_date = shift.getPickup_date();
            Boolean isAvailable = shift.getIsAvailable();
            Integer index = shift.getIndex();
         if(isAvailable){
    %>
    <div class="shifts">
        <h2>Donation Package</h2>
        <p><strong>From:</strong> <%= email %></p>
        <p><strong>To:</strong> <%= dropoff_org %></p>
        <p><strong>Pickup Location:</strong> <%= pickup_location %></p>
        <p><strong>Pickup Date: <%=pickup_date%></strong></p>
        <p><strong>Drop Off Location:</strong> <%= dropoff_location %></p>
        <p>The package can be collected at <%= pickup_time %>.</p>
        
        <form action="SignUpForShiftServlet.do" method="POST">
            <input type="hidden" name="index" value="<%=index%>"/>
            <input type="hidden" name="op" value="signup"/>
            <input type="submit" value="SIGN UP FOR SHIFT"/>
        </form>
    </div>
    <%}}
    }%>
    
</div>
<script>
    // JavaScript code to toggle active class
    var menuItems = document.querySelectorAll('.menu-bar a');

    menuItems.forEach(function(item) {
        item.addEventListener('click', function() {
            // Remove active class from all menu items
            menuItems.forEach(function(item) {
                item.classList.remove('active');
            });

            // Add active class to the clicked menu item
            this.classList.add('active');
        });
    });
    
</script>
</body>
</html>
