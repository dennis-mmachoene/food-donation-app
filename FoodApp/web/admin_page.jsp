<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nourish Link</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        .menu-bar {
            background-color: #333;
            color: white;
            padding: 10px 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .menu-title {
            font-size: 24px;
        }

        .menu-items {
            list-style-type: none;
            margin: 0;
            padding: 0;
        }

        .menu-items li {
            display: inline-block;
            margin-right: 20px;
            position: relative; /* Added for positioning the submenu */
        }

        .menu-items li:last-child {
            margin-right: 0;
        }

        .menu-items a {
            text-decoration: none;
            color: white;
            font-size: 18px;
            display: block; /* Added for proper styling */
            padding: 10px; /* Added for proper styling */
        }

        .menu-items a:hover {
            text-decoration: underline;
        }

        /* Added for styling the submenu */
        .submenu {
            display: none;
            position: absolute;
            background-color: #333;
            min-width: 200px;
            z-index: 1;
            left: 0;
            top: 100%;
            padding: 10px 0;
        }

        .menu-items li:hover .submenu {
            display: block;
        }

        .submenu-item {
            padding: 8px 20px;
            color: white;
            text-decoration: none;
            display: block;
        }

        .submenu-item:hover {
            background-color: #555;
        }
    </style>
</head>
<body>

<div class="menu-bar">
    <div class="menu-title">Nourish Link</div>
    <ul class="menu-items">
        <li><a href="#">View Donors</a></li>
        <li><a href="#">View Volunteers</a></li>
        <li><a href="#">View Organizations</a></li>
        <li class="has-submenu">
            <a href="#" id="view-report">View Report</a>
            <div class="submenu" id="report-menu">
                <a href="GetSummaryServlet.do" class="submenu-item">View Summary Report</a>
                <a href="GetDonorSummary.do" class="submenu-item">Donor Report</a>
                <a href="GetOrgs.do" class="submenu-item">Beneficiary Report</a>
                <a href="vol_summary.jsp" class="submenu-item">Volunteer Report</a>
            </div>
        </li>
        <li><a href="#">Sign out</a></li>
    </ul>
</div>

<script>
    document.getElementById('view-report').addEventListener('click', function() {
        var submenu = document.getElementById('report-menu');
        if (submenu.style.display === 'block') {
            submenu.style.display = 'none';
        } else {
            submenu.style.display = 'block';
        }
    });
</script>

</body>
</html>
