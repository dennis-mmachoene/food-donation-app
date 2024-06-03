<%@page import="java.util.List" %>
<%@page import="entity.Donor" %>
<%@page import="entity.DonorDetails" %>
<%@page import="entity.ProfilePicture" %>
<%@page import="java.util.Base64"%>
<%@page import="java.util.Date"%>
<%@page import="entity.Donations" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Donor Summary Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
        }
        
        .summary {
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            padding: 20px;
        }
        
        .summary .profile-card {
            display: flex;
            align-items: center;
        }
        
        .summary .profile-card img {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            margin-right: 20px;
        }
        
        .listing {
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        
        .listing table {
            width: 100%;
        }
        
        .listing td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        
        .food-image {
            max-width: 200px;
            max-height: 200px;
        }

        .get-pdf-button, .menu-button {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 15px 32px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .get-pdf-button:hover, .menu-button:hover {
            background-color: #45a049;
        }
    </style>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js" integrity="sha512-BNaRQnYJYiPSqHHDb58B0yaPfCu+Wgds8Gp/gU33kqBtgNS4tSPHuGibyoeqMV/TJlSKda6FXzoEyYGjTe+vXA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
    <div class="container">
        <%
        List<Donor> donors = (List<Donor>) request.getAttribute("donor");
        List<DonorDetails> details = (List<DonorDetails>) request.getAttribute("details");
        List<ProfilePicture> pps = (List<ProfilePicture>) request.getAttribute("pps"); // Corrected line
        String names = "";
        String phone = "";
        String img = "CSS/Pictures/images.png";
        if(donors != null && details != null){
            for(Donor donor : donors){
                String username = donor.getUsername();
                String email = donor.getEmail_address();
                Date creationDate = donor.getCreationDate();

                for(DonorDetails detail : details){
                    if(detail.getEmail().equals(email)){
                        names = detail.getNames();
                        phone = detail.getPhone();
                    }
                }
                if(pps != null){
                    for(ProfilePicture pp : pps){
                        if(pp.getEmail().equals(email)){
                            img = "data:image/*;base64,"+ Base64.getEncoder().encodeToString(pp.getPp());
                        }
                    }
                }
        %>
        <div class="summary">
            <div class="profile-card">
                <img src="<%=img%>" alt="Profile Picture">
                <div>
                    <p><strong>Username:</strong> <%=username%></p>
                    <p><strong>Names:</strong> <%=names%></p>
                    <p><strong>Phone:</strong> <%=phone%></p>
                    <p><strong>Email:</strong> <%=email%></p>
                </div>
            </div>
            <p>Account created on <%=creationDate%></p>
        </div>
        <% 
        List<Donations> donations = (List<Donations>) request.getAttribute("donations");
        if(donations != null){
            for(Donations listing : donations){
                if(listing.getEmail().equals(email)){
                    String pickup_location = listing.getPickup_location();
                    String items = listing.getItems();
                    String to_location = listing.getTo_location();
                    String pickup_time = listing.getPickup_time();
                    String image = "data:image/*;base64," + Base64.getEncoder().encodeToString(listing.getFoodImage());
        %>
        <div class="listing">
            <h1>Listings by donor</h1>
            <table>
                <tr>
                    <td>
                        <p><strong>Donor Email:</strong> <%=email %></p>
                        <p><strong>Pickup Location:</strong> <%= pickup_location %></p>
                        <p><strong>Items:</strong> <%= items %></p>
                        <p><strong>To:</strong> <%= to_location %></p>
                        <p><strong>Pickup Time:</strong> <%= pickup_time %></p>
                    </td>
                    <td><img src="<%= image %>" alt="Food Image" class="food-image"/></td>
                </tr>
            </table>
        </div>
        <% }
        }
    }
}
    }
        %>
    </div>
     <button class="get-pdf-button" id="get-pdf-button">GET PDF</button>
    <button class="menu-button" onclick="window.location.href='admin_page.jsp'">Go to menu</button>
    
  <script>
    var getPDFButton = document.getElementById("get-pdf-button");
    getPDFButton.addEventListener("click", function(){
        // Capture the content of the .container element using html2canvas
        html2canvas(document.querySelector(".container")).then(function(canvas) {
            // Convert the canvas to a data URL
            var imgData = canvas.toDataURL('image/png');

            // Set the width and height of the PDF document to match the container element
            var doc = new jsPDF({orientation: 'landscape', unit: 'px', format: [canvas.width, canvas.height]});
            
            // Add the image data to the PDF document
            doc.addImage(imgData, 'PNG', 0, 0);

            // Save the PDF document
            doc.save("Report.pdf");
        });
    });
</script>

</body>
</html>
