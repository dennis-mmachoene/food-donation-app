<%-- 
    Document   : register_org
    Created on : 10 May 2024, 17:06:28
    Author     : Dennis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register An Organization Page</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-image: url('CSS/Pictures/orgs.png');
            background-size: cover;
            background-position: center;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            color: #333;
        }

        form {
            background-color: rgba(255, 255, 255, 0.9);
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.5);
            max-width: 400px;
            width: 100%;
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-top: 10px;
        }

        input[type="text"],
        input[type="file"],
        textarea,
        select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        textarea {
            resize: vertical;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-top: 15px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        
        #error-message {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <form action="RegisterOrgServlet.do" method="POST" enctype="multipart/form-data">
        <h1>Register an Organization</h1>
        <% String errorMessage = (String) request.getAttribute("message");
           String usertype = (String) request.getAttribute("usertype");
           if (errorMessage != null) { %>
            <div id="error-message">
                <%= errorMessage %>
            </div>
        <% } %>
        <label for="org_name">Organization Name:</label>
        <input type="text" name="org_name" id="org_name" required=""/>
        <label for="description">Description</label>
        <textarea id="description" name="description" rows="4" required=""></textarea>
        <label>Contact Information</label>
        <select id="contact-type" onchange="toggleContactFields()">
            <option value="phone">Phone</option>
            <option value="email">Email Address</option>
            <option value="address">Address</option>
            <option value="url">Website Url</option>
        </select>
        <div id="phone-field" style="display: block;">
            <label for="phone">Phone</label>
            <input type="text" name="phone" id="phone" required=""/>
        </div>
        <div id="email-field" style="display: none;">
            <label for="email">Email Address</label>
            <input type="text" name="email" id="email" required=""/>
        </div>
        <div id="address-field" style="display: none;">
            <label for="address">Address</label>
            <textarea id="address" name="address" required="" rows="4"></textarea>
        </div>
        <div id="url-field" style="display: none;">
            <label for="url">Website Url</label>
            <input type="text" name="url" id="url" required=""/>
        </div>
        <label for="pp">Picture/LOGO</label>
        <input type="file" name="image" id="pp" required=""/>
        <input type="submit" value="REGISTER"/>
    </form>
    <script>
        function toggleContactFields() {
            var contactType = document.getElementById("contact-type").value;
            var phoneField = document.getElementById("phone-field");
            var emailField = document.getElementById("email-field");
            var addressField = document.getElementById("address-field");
            var urlField = document.getElementById("url-field");

            switch(contactType) {
                case "phone":
                    phoneField.style.display = "block";
                    emailField.style.display = "none";
                    addressField.style.display = "none";
                    urlField.style.display = "none";
                    break;
                case "email":
                    phoneField.style.display = "none";
                    emailField.style.display = "block";
                    addressField.style.display = "none";
                    urlField.style.display = "none";
                    break;
                case "address":
                    phoneField.style.display = "none";
                    emailField.style.display = "none";
                    addressField.style.display = "block";
                    urlField.style.display = "none";
                    break;
                case "url":
                    phoneField.style.display = "none";
                    emailField.style.display = "none";
                    addressField.style.display = "none";
                    urlField.style.display = "block";
                    break;
                default:
                    break;
            }
        }
    </script>
</body>
</html>
