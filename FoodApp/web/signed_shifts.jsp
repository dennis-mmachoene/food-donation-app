<%@page import="java.util.List" %>
<%@page import="entity.Shift" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Shifts Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
                min-height: 100vh;
            }
            h1 {
                color: #333;
            }
            .container {
                width: 80%;
                max-width: 800px;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }
            .signed-shifts {
                border: 1px solid #ddd;
                margin-bottom: 20px;
                padding: 15px;
                border-radius: 8px;
                background-color: #f9f9f9;
            }
            .signed-shifts h2 {
                margin-top: 0;
                color: #555;
            }
            .signed-shifts p {
                margin: 5px 0;
                color: #666;
            }
            .signed-shifts form {
                display: flex;
                gap: 10px;
            }
            .signed-shifts input[type="submit"] {
                background-color: #007bff;
                color: #fff;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            .signed-shifts input[type="submit"]:hover {
                background-color: #0056b3;
            }
            a {
                display: inline-block;
                margin-top: 20px;
                text-decoration: none;
                color: #007bff;
                transition: color 0.3s;
            }
            a:hover {
                color: #0056b3;
            }
        </style>
    </head>
    <body> 
        <div class="container">
            <%
                List<Shift> signedShifts = (List<Shift>) session.getAttribute("signedShifts");
                if(signedShifts != null){
            %>
            <h1>Shifts you have signed up for.</h1>
            <%
                for(Shift shift: signedShifts){
                    String email = shift.getEmail();
                    String pickup_location = shift.getPickup_location();
                    String dropoff_org = shift.getDropoff_org();
                    String dropoff_location = shift.getDropoff_location();
                    String pickup_time = shift.getPickup_time();
                    String pickup_date = shift.getPickup_date();
                    Boolean isAvailable = shift.getIsAvailable();
                    Integer index = shift.getIndex();
            %>
            <div class="signed-shifts">
                <h2>Donation Package</h2>
                <p><strong>From:</strong> <%= email %></p>
                <p><strong>To:</strong> <%= dropoff_org %></p>
                <p><strong>Pickup Location:</strong> <%= pickup_location %></p>
                <p><strong>Pickup Date:</strong> <%= pickup_date %></p>
                <p><strong>Drop Off Location:</strong> <%= dropoff_location %></p>
                <p>The package can be collected at <%= pickup_time %>.</p>
                <%if (!shift.getCompleted()){%>
                <form action="SignUpForShiftServlet.do" method="POST">
                    <input type="hidden" name="index" value="<%= index %>"/>
                    <input type="hidden" name="op" value="complete"/>
                    <input type="submit" name="action" value="MARK SHIFT COMPLETED"/>

                </form><p>
                <form action="SignUpForShiftServlet.do" method="POST">
                    <input type="hidden" name="index" value="<%= index %>"/>
                    <input type="hidden" name="op" value="cancel"/>

                    <input type="submit" name="action" value="CANCEL SHIFT"/>
                </form></p><%}else{%>
                <h4>You completed this shift </h4>
            </div>
            <%}
                }
            } else {
            %>
            <h1>You haven't signed up for any shift yet</h1>
            <%
            }
            %>
            <a href="homefeed.jsp">Back</a>
        </div>
    </body>
</html>
