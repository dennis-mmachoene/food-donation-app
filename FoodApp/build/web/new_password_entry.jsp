<%-- 
    Document   : new_password_entry
    Created on : 21 May 2024, 18:26:58
    Author     : Dennis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Enter New Password Page</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: #f3f5f7; /* Use background color from index.jsp */
            color: #333;
        }

        h1 {
            text-align: center;
            margin-top: 50px;
            color: #333; /* Use text color from index.jsp */
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
            margin-top: 10px;
            font-weight: bold;
            color: #555; /* Use label color from index.jsp */
        }

        input[type="password"],
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        input[type="checkbox"] {
            margin-top: 5px;
        }

        #password-error {
            color: red;
            display: none;
        }

        .clear {
            clear: both;
        }
    </style>
</head>
<body>
    <h1>Provide a new password</h1>
    <% 
    String usertype = (String) request.getAttribute("usertype");
    String email = (String) request.getAttribute("email");
    %>
    <form action="ChangePasswordServlet.do" method="POST" onsubmit="return validatePassword()">
        <label for="password">Enter a new password</label>
        <input type="password" name="password" id="password" required=""/>
        <input type="checkbox" id="show-password-checkbox" onchange="showPassword()"/>
        <label for="show-password-checkbox">Show password</label>
        <label for="retype-password">Confirm Password</label>
        <input type="password" id="retype-password" required=""/>
        <span id="password-error">Passwords do not match</span>
        <div class="clear"></div>
        <input type="hidden" name="usertype" value="<%=usertype%>"/>
        <input type="hidden" name="email" value="<%=email%>"/>
        <input type="hidden" name="op" value="change_password"/>
        <input type="submit" value="Change Password" style="background-color: #007bff; color: #fff; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; padding: 10px; margin-top: 15px; width: 100%;" />
    </form>
    
    <script>
        function validatePassword() {
            var password = document.getElementById("password").value;
            var retypePassword = document.getElementById("retype-password").value;

            if (password !== retypePassword) {
                document.getElementById("password-error").style.display = "block";
                return false;
            } else {
                var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%&])[A-Za-z\d@#$%&]{8,}$/;
                if (!passwordRegex.test(password)) {
                    document.getElementById("password-error").innerText = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one of the symbols (@#$%&)";
                    document.getElementById("password-error").style.display = "block";
                    return false;
                }
            }
            document.getElementById("password-error").style.display = "none";
            return true;
        }

        function showPassword() {
            var passwordInput = document.getElementById("password");
            var checkbox = document.getElementById("show-password-checkbox");

            if (checkbox.checked) {
                passwordInput.type = "text";
            } else {
                passwordInput.type = "password";
            }
        }
    </script>
</body>
</html>
