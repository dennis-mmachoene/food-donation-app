<%@page import="java.util.List" %>
<%@page import="entity.ProfilePicture"%>
<%@page import="entity.DonorDetails" %>
<%@page import="java.util.Base64" %>
<%@page import="entity.Organization" %>
<%@page import="entity.Requests"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Choose Donors Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            width: 100%;
            max-width: 800px;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        .donor-profile-card {
            display: flex;
            align-items: center;
            background-color: #f9f9f9;
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .donor-profile-card img {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            margin-right: 20px;
        }
        .donor-details {
            flex-grow: 1;
        }
        .donor-details h2 {
            margin: 0 0 10px 0;
            font-size: 20px;
        }
        .donor-details p {
            margin: 5px 0;
            font-size: 14px;
            color: #555;
        }
        form {
            text-align: right;
        }
        input[type="submit"] {
            background-color: #007bff;
            border: none;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Donors to send donation request to:</h1>
    <% Organization org = (Organization) session.getAttribute("org");
    String org_name = org.getName();
        List<DonorDetails> donors = (List<DonorDetails>) session.getAttribute("donors");
        List<ProfilePicture> pps = (List<ProfilePicture>) session.getAttribute("pps");
        List<Requests> reqs = (List<Requests>) session.getAttribute("reqs");
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
               boolean requestSent = false;
               for(Requests req : reqs){
               if(req.getOrg_name().equals(org_name) && req.getDonor_email().equals(email)){
               requestSent = true;
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
        <%if(requestSent){%>
        <h4>Request sent</h4><%}else{%>
        <form action="AddRequestServlet.do" method="Post">
            <input type="hidden" name="name" value="<%=org_name%>"/>
            <input type="hidden" name="email" value="<%=email%>"/>
            <input type="submit" value="SEND REQUEST TO"/>
        </form><%}%>
    </div>
    <% 
            }
        }
    %>
    <a href="org_homepage.jsp">Back</a>
</div>
</body>
</html>
