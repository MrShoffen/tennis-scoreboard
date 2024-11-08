<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>

    <title>Error!</title>
    <link rel="icon" href="static/img/main_icon.svg" type="image/x-icon">

    <%-- bootstrap --%>
    <link href="static/css/bootstrap.css" rel="stylesheet"/>

    <%-- Font awesome style --%>
    <link rel="stylesheet" href="static/css/all.min.css">

    <%-- Roboto font --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">

    <!-- Custom page styles -->
    <link href="static/css/custom/error-page.css" rel="stylesheet"/>


</head>
<body>


<%@ include file="util/header.html" %>


<div class="container page-content">


    <div class="row justify-content-md-center">

        <div class="col-lg-9 themed-grid-col text-center">

            <h1>ERROR 404</h1>
            <h3>No such page!</h3>

        </div>
    </div>


</div>


<%@ include file="util/footer.html" %>

<script src="static/js/bootstrap.bundle.min.js"></script>

<script src="app.js"></script>

</body>
</html>
