<%-- 
    Document   : forgot_password
    Created on : 21 May 2024, 16:04:19
    Author     : Dennis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password Page</title>
        <style>
            body {
                margin: 0;
                padding: 0;
                font-family: Arial, sans-serif;
                background-color: #f2f2f2;
                color: #333;
            }

            h4 {
                text-align: center;
                margin-top: 50px;
            }

            .error-msg {
                color: red;
                text-align: center;
                margin-bottom: 20px;
            }

            form {
                width: 300px;
                margin: 0 auto;
                background-color: #fff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
            }

            label {
                display: block;
                margin-bottom: 5px;
            }

            select,
            input[type="text"],
            input[type="submit"] {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border-radius: 5px;
                border: 1px solid #ccc;
                box-sizing: border-box;
            }

            input[type="submit"] {
                margin-top: 15px;
                background-color: #007bff;
                color: white;
                cursor: pointer;
                font-size: 16px;
                border: none;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <h4>Change your password.</h4>
        <% 
        String message = (String) request.getAttribute("message");
        if(message != null){
        %>
        <div class="error-msg">
            <%=message%>
        </div>
        <% } %>

        <form action="ChangePasswordServlet.do" method="POST">
            <label for="user_type">Which user are you?</label>
            <select name="user_type" id="user_type" required="">
                <option value="donor">Donor</option>
                <option value="volunteer">Volunteer</option>
            </select>
            <label for="email-address">Email Address</label>
            <input type="text" name="email" id="email-address" required="">
            <input type="hidden" name="op" value="check_email"/>
            <input type="submit" value="SUBMIT"/>
        </form>
    </body>
</html>
