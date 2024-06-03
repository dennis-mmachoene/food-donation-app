<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.List"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>List A Donation Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }

        h1 {
            text-align: center;
            margin-top: 20px;
        }

        form {
            width: 50%;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input[type="text"],
        textarea,
        select,
        input[type="file"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            resize: none;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Donation Details</h1>
    <% List<String> org_names = (List<String>) session.getAttribute("org_names");%>

    <form action="DonationListServlet.do" method="POST" enctype="multipart/form-data">
         <label for="beneficiary">To</label>
        <select id="beneficiary" name="beneficiary">
            <%for(int i = 0; i < org_names.size();i++){
            %>
            <option value="<%=org_names.get(i)%>"><%=org_names.get(i)%></option>
               <% }%>
        </select>
        <label for="items">Item/s details</label>
        <textarea id="items" name="items"></textarea>
        <label for="pickup-location">Pick up location</label>
        <input type="text" name="pickup-location" id="pickup-location" required=""/>
       
        <label for="pickup-date">Pick up date</label>
        <input type="date" id="pickup-date" name="pickup_date" required=""/>
        <label for="pickup-time">Pickup time</label>
        <select id="pickup-time" name="pickup-time">
            
            <option value="Morning">Morning- 6 a.m. to 12 p.m.</option>
            <option value="Afternoon">Afternoon- 12 p.m. to 6 p.m.</option>
            <option value="Evening">Evening- 6 p.m. to 8 p.m</option>
        </select>
        <label for="image">Add photo of the food</label>
        <input type="file" name="image" id="image" required=""/>
        <input type="hidden" name="op" value="add"/>
        <input type="submit" value="Submit"/>
    </form>
</body>
</html>
