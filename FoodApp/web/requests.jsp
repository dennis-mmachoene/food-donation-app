<%@page import="java.util.Date" %>
<%@page import="java.util.Base64" %>
<%@page import="java.util.List" %>
<%@page import="entity.Organization"%>
<%@page import="entity.Requests" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check Page</title>
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
                text-align: center;
            }
            h1, h4, h5 {
                margin-bottom: 20px;
            }
            .request-card {
                background-color: #f9f9f9;
                padding: 10px;
                border-radius: 8px;
                margin-bottom: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                display: flex;
                align-items: center;
            }
            .request-card img {
                width: 80px;
                height: 80px;
                border-radius: 50%;
                margin-right: 20px;
            }
            .request-card table {
                width: 100%;
            }
            .request-card td {
                vertical-align: middle;
            }
            a {
                display: inline-block;
                margin-top: 20px;
                padding: 10px 20px;
                background-color: #007bff;
                color: #fff;
                text-decoration: none;
                border-radius: 5px;
                transition: background-color 0.3s ease;
            }
            a:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <%
            List<Requests> reqs = (List<Requests>) session.getAttribute("reqs");
            List<Organization> orgs = (List<Organization>) session.getAttribute("orgs");
            if (reqs != null && reqs.size() != 0) {
            %>
            <h1>Request made by beneficiaries</h1>
            <h5>Check emails for request details.</h5>
            <%
            for (Requests req : reqs) {
                String name = req.getOrg_name();
                Date posted = req.getCreationDate();
                String image = "CSS/Pictures/images.png"; // Default image
                for (Organization org : orgs) {
                    if (org.getName().equals(name)) {
                        image = "data:image/*;base64," + Base64.getEncoder().encodeToString(org.getImage());
                    }
                }
            %>
            <div class="request-card">
                <table>
                    <tr>
                        <td><img src="<%= image %>" alt="Profile Picture"/></td>
                        <td>
                            Posted by: <%= name %><br>
                            Posted on: <%= posted %>
                        </td>
                    </tr>
                </table>
            </div>
            <%
            }
            } else {
            %>
            <h4>No requests have been made</h4>
            <%
            }
            %>
            <a href="newsfeed.jsp">Back</a>
        </div>
    </body>
</html>
