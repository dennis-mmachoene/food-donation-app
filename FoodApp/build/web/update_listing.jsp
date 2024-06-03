<%@page import="entity.Donations" %>
<%@page import="java.util.Base64"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Donation List Page</title>
    </head>
    <body>
        <h1>Edit Listing</h1>
        <% Donations listing = (Donations) request.getAttribute("donation");
         String email = listing.getEmail();
                    String pickup_location = listing.getPickup_location();
                    String items = listing.getItems();
                    String to_location = listing.getTo_location();
                    String pickup_time = listing.getPickup_time();
                    String pickup_date = listing.getPickup_date();
                    String image = "data:image/*;base64," + Base64.getEncoder().encodeToString(listing.getFoodImage());
              
List<String> org_names = (List<String>) session.getAttribute("org_names");%>
        <form action="" method="post" enctype="multipart/form-data">
            <label for="beneficiary">To</label> 
            <input type="text" name="beneficiary" value="<%=to_location%>"<button>Change To Location</button>
             
        <select id="beneficiary" name="to_location">
            <%for(int i = 0; i < org_names.size();i++){
            %>
            <option value="<%=org_names.get(i)%>"><%=org_names.get(i)%></option>
               <% }%>
        </select>
        <label for="items">Item/s details</label>
        <textarea id="items" name="items" value="<%=items%>"></textarea>
        <label for="pickup-location">Pick up location</label>
        <input type="text" name="pickup-location" id="pickup-location" value="<%=pickup_location%>"/>
       
        <label for="pickup-date">Pick up date</label>
        <input type="date" id="pickup-date" name="pickup_date" value="<%=pickup_date%>"/>
        <label for="pickup-time">Pickup time</label>
        <input type="text" name="pickup_time" id="pickup-time" value="<%=pickup_time%>"/><button>Change Time</button>
        <select id="pickup-time" name="time">
            
            <option value="Morning">Morning- 6 a.m. to 12 p.m.</option>
            <option value="Afternoon">Afternoon- 12 p.m. to 6 p.m.</option>
            <option value="Evening">Evening- 6 p.m. to 8 p.m</option>
        </select>
        <label for="image">Add photo of the food</label>
        <input type="file" name="image" id="image" value="<%=image%>"/>
        <input type="submit" value="Submit"/>
    </form>
</body>
</html>
        </form>
    </body>
</html>
