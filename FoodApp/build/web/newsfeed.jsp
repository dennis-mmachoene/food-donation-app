<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="entity.Organization" %>
<%@page import="java.util.Base64" %>
<%@page import="entity.DonorDetails"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .menu-bar {
            list-style-type: none;
            margin: 0;
            padding: 10px 0;
            background-color: #333;
            overflow: hidden;
        }
        .menu-bar li {
            float: left;
        }
        .menu-bar li a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }
        .menu-bar li a:hover {
            background-color: #ddd;
            color: black;
        }
        .org-card {
            width: 300px;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin: 10px;
            padding: 10px;
            float: left;
        }
        .org-card img {
            width: 100%;
            border-radius: 5px;
        }
        .org-card h1 {
            font-size: 20px;
            margin: 10px 0;
        }
        .org-card p {
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <ul class="menu-bar">
        <li><a href="account.jsp">View Account</a></li>
        <li><a href="donation_list.jsp">Create Donation List</a></li>
        <li><a href="DonationRequestServlet.do">Check Donation Requests</a></li>
    </ul>
    
    <% List<Organization> orgs = (List<Organization>) session.getAttribute("orgs");
    
    for(Organization org: orgs){
    String name = org.getName();
    String description = org.getDescription();
    String phone = org.getPhone();
    String email = org.getEmail();
    String address = org.getAddress();
    String link = org.getLink();
    byte[] imgByte = org.getImage();
    
    String image = "data:image/png;base64,"+
                           Base64.getEncoder().encodeToString(imgByte);
        %>
        <div class="org-card">
            <img src="<%=image%>"/>
            <h1><%=name%></h1>
            <p><%=description%></p>
            <p>Phone: <%=phone%></p>
            <p>Email Address: <%=email%></p>
            <p>Address: <%=address%></p>
            <p>Website: <a href="<%=link%>"><%=link%></a></p>
        </div>
        <%}%>
</body>
</html>
