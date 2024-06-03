<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Donor Account</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-image: url('CSS/Pictures/donor_bg.jpg');
            background-size: cover;
            background-repeat: no-repeat; 
            background-position: center;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        #container {
            background-color: rgba(255, 255, 255, 0.8);
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.5);
            width: 100%;
            max-width: 400px;
        }

        #form-container {
            text-align: center;
        }

        .input-group {
            margin-bottom: 15px;
            text-align: left;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
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
    <div id="container">
        <div id="form-container">
            <h1>Create a donor account</h1>
            <% String errorMessage = (String) request.getAttribute("message");
            String usertype = (String) request.getAttribute("usertype");
            if (errorMessage != null) { %>
                <div id="error-message">
                    <%= errorMessage %>
                </div>
            <% } %>
            <form id="create-accout" action="CreateAccountServlet.do" method="POST" onsubmit="return validateForm()">
                <div class="input-group">
                    <label for="username">Username</label>
                    <input type="text" name="username" id="username" required>
                </div>
                <div class="input-group">
                    <label for="email-address">Email Address</label>
                    <input type="text" name="email-address" id="email-address" required>
                </div>
                <div class="input-group">
                    <label for="password">Password</label>
                    <input type="password" name="password" id="password" required>
                    <input type="checkbox" id="show-password-checkbox" onchange="togglePasswordVisibility()">
                    <label for="show-password-checkbox">Show password</label>
                </div>
                <div class="input-group">
                    <label for="retype-password">Confirm Password</label>
                    <input type="password" id="retype-password" required>
                </div>
                <span id="password-error">Passwords do not match</span>
                <div class="clear"></div>
                <input type="hidden" name="usertype" value="<%= usertype %>">
                <input type="submit" value="Create Account">
            </form>
        </div>
    </div>

    <script>
        function validateForm() {
            var password = document.getElementById("password").value;
            var retypePassword = document.getElementById("retype-password").value;

            if (password !== retypePassword) {
                document.getElementById("password-error").innerText = "Passwords do not match";
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

        function togglePasswordVisibility() {
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
