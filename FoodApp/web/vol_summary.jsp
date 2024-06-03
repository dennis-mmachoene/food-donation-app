<%-- 
    Document   : vol_summary
    Created on : 16 May 2024, 07:11:38
    Author     : Dennis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Volunteer Activity Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }
        
        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        
        h1 {
            font-size: 32px;
            color: #333;
            margin-bottom: 20px;
        }
        
        h2 {
            font-size: 24px;
            color: #666;
            margin-bottom: 15px;
        }
        
        p {
            color: #333;
            margin-bottom: 10px;
        }
        
        ul {
            margin: 0;
            padding: 0;
            list-style-type: none;
        }
        
        li {
            margin-bottom: 5px;
        }
        
        .button {
            display: block;
            width: 200px;
            margin: 20px auto;
            padding: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 18px;
            cursor: pointer;
        }
        
        .button:hover {
            background-color: #0056b3;
        }
    </style>
	<script src=
"https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js">
    </script>
</head>
<body>
    <div class="container">
        <h1>Volunteer Activity Report</h1>
        
        <h2>Volunteer Information:</h2>
        <p>Name: John Doe</p>
        <p>Email: john@example.com</p>
        <p>Phone: 123-456-7890</p>
        
        <h2>Shift Information:</h2>
        <p>Date: May 15, 2024</p>
        <p>Time: 10:00 AM - 2:00 PM</p>
        <p>Location: Community Center</p>
        
        <h2>Donation Collection:</h2>
        <ul>
            <li>Collected donation package from Donor: Jane Smith</li>
            <li>Address: 123 Main St, Cityville</li>
            <li>Items: Clothes, non-perishable food</li>
        </ul>
        
        <h2>Delivery Information:</h2>
        <p>Delivered donation package to Beneficiary: Local Charity Organization</p>
        <p>Address: 456 Oak St, Townsville</p>
        
        <button class="button" id="btn">Download Report</button>
        <button class="button" onclick="admin_page.jsp">Back to Menu</button>
		<script>
        var button = document.getElementById("btn");
        button.addEventListener("click", function () {
            var doc = new jsPDF();
            var pdf = document.querySelector(".container");
            doc.fromHTML(pdf);
            doc.save("report.pdf");
        });
    </script>
    </div>
</body>
</html>
