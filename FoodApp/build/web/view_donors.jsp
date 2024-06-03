
<%@page import="java.util.List" %>
<%@page import="entity.ProfilePicture"%>
<%@page import="entity.DonorDetails" %>
<%@page import="java.util.Base64" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Donors Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        .donor-profile-card {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            padding: 20px;
            display: flex;
            align-items: center;
        }

        .donor-profile-card img {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            margin-right: 20px;
        }

        .donor-details {
            flex-grow: 1;
        }

        .donor-details h2 {
            margin-bottom: 10px;
            color: #333;
        }

        .donor-details p {
            margin-bottom: 10px;
            color: #666;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Donors to send donation request to:</h1>
    <% 
        List<DonorDetails> donors = (List<DonorDetails>) session.getAttribute("donors");
        List<ProfilePicture> pps = (List<ProfilePicture>) session.getAttribute("pps");
    
        if(donors != null && pps != null) {
            for(DonorDetails donor : donors) {
                String email = donor.getEmail();
                String names = donor.getNames();
                String phones = donor.getPhone();
                String image = "CSS/Pictures/images.png"; // Default image
                for(ProfilePicture pp : pps) {
                    if(pp.getEmail().equals(email)) {
                        byte[] dp = pp.getPp();
                        if(dp != null) {
                            image = "data:image/*;base64," + Base64.getEncoder().encodeToString(dp);
                        }
                    }
                }
    %>
    <div class="donor-profile-card">
        <img src="<%=image%>" alt="Profile Picture">
        <div class="donor-details">
            <h2><%=names%></h2>
            <p>Email: <%=email%></p>
            <p>Phone: <%=phones%></p>
        </div>
    </div>
    <% 
            }
        }
    %>
     <a href="homefeed.jsp">Back</a>
</div>
</body>
</html>
