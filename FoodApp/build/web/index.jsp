<!DOCTYPE html>
<html>
<head>
    <title>Welcome Page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/indexStyle.css">
</head>
<body>

<div class="logo-container">
    <img src="CSS/Pictures/DonationLogo.png" alt="Mxolisi" class="logo">
</div>
<div class="container">
    <div class="right-panel">

        <h1 id="heading">Spread Love</h1>

        <p id="paragraph">After donating the food to the needy, share your feeling to motivate many people.</p>
    </div>

    <div class="left-panel">
        <% String message = (String) request.getAttribute("message"); 
        
        if(message != null){
       %>
            <div id="error-message">
                <%=message%>
            </div>
            <%}%>
        <form onsubmit="return submitForm()" action="LoginServlet.do" method="POST" id="signinForm">
            <div>
                <input type="radio" id="donor" name="userType" value="donor">
                <label for="donor">Donor</label>
                <input type="radio" id="volunteer" name="userType" value="volunteer">
                <label for="volunteer">Volunteer</label><br>
                <input type="radio" id="beneficiary" name="userType" value="beneficiary">
                <label for="beneficiary">Beneficiary</label>
                <input type="radio" id="admin" name="userType" value="admin">
                <label for="beneficiary">Admin</label>
            </div>
            <table cellspacing="5" cellpadding="10">
                <tr>
                    <td>Username : </td>
                    <td><input type="text" name="username" size="25" maxlength="30"></td>
                </tr>
                <tr>
                    <td>Password: </td>
                    <td><input type="password" name="password" size="25" maxlength="30"></td>
                </tr>
                <tr>
                    <td><input type="hidden" id="sselectedUserType" name="selectedUserType" value=""></td>
                    <td><input type="hidden" name="operation" value="sign-in"/></td>
                </tr>
                <tr>
                    
                    <td><input type="submit" value="SIGN IN"></td>
                </tr>
                <tr>
                    <td><p><a href="forgot_password.jsp">FORGOT PASSWORD</a></p></td>
                </tr>
            </table>
        </form>
        <form onsubmit="return submitForm()" action="CheckUserTypeServlet.do" method="POST" id="signupForm">
            <input type="hidden" id="selectedUserType" name="selectedUserType" value="">
            <input type="hidden" id="operation" name="operation" value="create-account"/> 
            <input type="submit" value="CREATE ACCOUNT">
        </form>
    </div>
</div>

    <script src="JavaScript/IndexScript.js"></script>
<script>
    function getUserType() {
        var userType = document.getElementsByName('userType');
        var selectedUserType = null;

        for (var i = 0; i < userType.length; i++) {
            if (userType[i].checked) {
                selectedUserType = userType[i].value;
                break;
            }
        }
        return selectedUserType;
    }

    function submitForm() {
        var selectedUserType = getUserType();
        if (!selectedUserType) {
            alert("Please select a user type.");
            return false;
        }
        document.getElementById("sselectedUserType").value = selectedUserType;
        document.getElementById("selectedUserType").value = selectedUserType;
        return true;
    }
</script>
</body>
</html>
