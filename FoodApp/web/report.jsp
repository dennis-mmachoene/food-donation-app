<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>System Administrator Report</title>
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
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js" integrity="sha512-BNaRQnYJYiPSqHHDb58B0yaPfCu+Wgds8Gp/gU33kqBtgNS4tSPHuGibyoeqMV/TJlSKda6FXzoEyYGjTe+vXA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
  <div class="container">
    <h1>System Administrator Report</h1>
<% Integer user = (Integer) request.getAttribute("users");

           Integer donors = (Integer) request.getAttribute("donors");
            Integer volunteers = (Integer) request.getAttribute("volunteers");
            Integer orgs = (Integer) request.getAttribute("orgs");
            Integer listings = (Integer) request.getAttribute("listings");
            
            %>
    <h2>User Statistics:</h2>
    <p>Total number of registered users: <%=user%></p>
    <p>Breakdown by user type:</p>
    <ul>
      <li>Donors: <%=donors%></li>
      <li>Volunteers: <%=volunteers%></li>
      <li>Beneficiaries: <%=orgs%></li>
    </ul>

    <h2>Activity Overview:</h2>
    <p>Total donation listings created: <%=listings%></p>
    <p>Total donation requests posted: 0</p>
    <p>Total shifts signed up for: 0</p>

    <h2>User Feedback:</h2>
    <p>No significant issues reported.</p>

    <h2>Technical Performance:</h2>
    <p>Server uptime: 99.9%</p>
    <p>No major system errors reported.</p>

    <h2>Security Overview:</h2>
    <p>No security incidents reported.</p>

    <h2>Feature Requests and Enhancements:</h2>
    <p>Several feature requests submitted. Currently evaluating feasibility.</p>

    <h2>User Engagement and Retention:</h2>
    <p>Active users: <%=user%></p>
    <p>Returning users: 70%</p>

    <h2>Training and Support:</h2>
    <p>User support tickets: 20</p>
    <p>Plans for additional user education materials.</p>

    <h2>Future Roadmap:</h2>
    <p>Planned feature updates and improvements.</p>

    <button class="button">GET PDF</button>
    <button class="button" onclick="admin_page.jsp">Go to menu</button>
    
    <script>
    var button = document.querySelector(".button");
    button.addEventListener("click", function(){
        var doc = new jsPDF();
        var container = document.querySelector(".container");

        // Convert container HTML to canvas
        html2canvas(container).then(function(canvas) {
            // Convert canvas to PDF
            var imgData = canvas.toDataURL('image/png');
            var imgWidth = 210; // A4 width
            var pageHeight = imgWidth * 1.414; // A4 height
            var imgHeight = canvas.height * imgWidth / canvas.width;
            var heightLeft = imgHeight;

            doc.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
            heightLeft -= pageHeight;

            // Add more pages if content overflows
            while (heightLeft >= 0) {
                position = heightLeft - imgHeight;
                doc.addPage();
                doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
                heightLeft -= pageHeight;
            }

            // Save PDF
            doc.save("Report.pdf");
        });
    });
</script>

</body>
</html>


