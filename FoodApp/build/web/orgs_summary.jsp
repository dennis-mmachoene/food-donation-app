<%-- 
    Document   : orgs_summary
    Created on : 16 May 2024, 06:07:26
    Author     : Dennis
--%>
<%@page import="java.util.List" %>
<%@page import="entity.Donations"%>
<%@page import="entity.Organization"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Base64" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Summary Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                margin: 0;
                padding: 0;
            }
            .container {
                width: 80%;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            h1 {
                font-size: 24px;
                color: #333;
                margin-bottom: 20px;
            }
            .registered-orgs {
                margin-bottom: 20px;
            }
            .article {
                border-bottom: 1px solid #ccc;
                padding-bottom: 20px;
                margin-bottom: 20px;
            }
            .article img {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                margin-right: 20px;
                border: 1px solid #ccc;
            }
            table {
                width: 100%;
            }
            td {
                padding: 10px;
            }
            .food-image {
                max-width: 200px;
                max-height: 200px;
            }
            .button {
                display: block;
                width: 150px;
                margin: 20px auto;
                padding: 10px;
                background-color: #007bff;
                color: #fff;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
            }
            button {
                display: block;
                width: 150px;
                margin: 10px auto;
                padding: 10px;
                background-color: #ccc;
                color: #333;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
                text-decoration: none;
                text-align: center;
            }
            button:hover {
                background-color: #999;
            }
        </style>
          <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js" integrity="sha512-BNaRQnYJYiPSqHHDb58B0yaPfCu+Wgds8Gp/gU33kqBtgNS4tSPHuGibyoeqMV/TJlSKda6FXzoEyYGjTe+vXA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

    </head>
    <body>
        <div class="container">
            <h1>Registered Organizations to the system</h1>
            <% List<Organization> orgs = (List<Organization>) request.getAttribute("orgs");
            if(orgs == null) { %>
                <h2> No registered organizations yet.</h2>
            <% } else {
                for(Organization org: orgs) {
                    String name = org.getName();
                    String desc = org.getDescription();
                    String phone = org.getPhone();
                    String email = org.getEmail();
                    String address = org.getAddress();
                    String link = org.getLink();
                    byte[] img = org.getImage();
                    Date date = org.getCreationDate();
                    String image = "data:image/*;base64,"+Base64.getEncoder().encodeToString(img); %>
                    <div class="registered-orgs">
                        <div class="article">
                            <table>
                                <tr>
                                    <td><img src="<%=image%>" alt="Organization Logo"></td>
                                    <td>
                                        <p><strong>Name:</strong> <%=name%></p>
                                        <p><strong>Description:</strong> <%=desc%></p>
                                        <p><strong>Phone:</strong> <%=phone%></p>
                                        <p><strong>Email:</strong> <%=email%></p>
                                        <p><strong>Address:</strong> <%=address%></p>
                                        <p><strong>Website:</strong> <%=link%></p>
                                        <p>Registered on <%=date%>.</p>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <% List<Donations> listings = (List<Donations>) request.getAttribute("listings");
                        if(listings != null) {
                            for(Donations listing : listings) {
                                if(listing.getTo_location().equals(name)) {
                                String d_email = listing.getEmail();
                                    String pickup_location = listing.getPickup_location();
                                    String items = listing.getItems();
                                    String to_location = listing.getTo_location();
                                    String pickup_time = listing.getPickup_time();
                                    String imag_e = "data:image/*;base64," + Base64.getEncoder().encodeToString(listing.getFoodImage()); %>
                                    <div class="listings-made">
                                        <h2>Donation listed to <%=name%></h2>
                                        <table>
                                            <tr>
                                                <td>
                                                    <p><strong>Donor Email:</strong> <%=d_email %></p>
                                                    <p><strong>Pickup Location:</strong> <%= pickup_location %></p>
                                                    <p><strong>Items:</strong> <%= items %></p>
                                                    <p><strong>To:</strong> <%= to_location %></p>
                                                    <p><strong>Pickup Time:</strong> <%= pickup_time %></p>
                                                </td>
                                                <td><img src="<%= imag_e %>" alt="Food Image" class="food-image"></td>
                                            </tr>
                                        </table>
                                    </div>
                                <% }
                            }
                        }
                    } %>
                </div>
            <% } %>
        </div>
        <button class="button" id="get-pdf-button">Get PDF</button>
        <button onclick="admin_page.jsp">Back to menu</button>
        
        <script>
            var getPDFButton = document.getElementById("get-pdf-button");
            getPDFButton.addEventListener("click", function(){
                var container = document.querySelector(".container");

                html2canvas(container).then(function(canvas) {
                    var imgData = canvas.toDataURL('image/png');
                    var pdf = new jsPDF();
                    var imgHeight = canvas.height * 210 / canvas.width;
                    pdf.addImage(imgData, 'PNG', 0, 0, 210, imgHeight);
                    pdf.save("summary.pdf");
                });
            });
        </script>
    </body>
</html>
