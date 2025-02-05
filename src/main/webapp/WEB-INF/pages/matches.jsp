<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>

    <title>Matches</title>
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
    <link href="static/css/custom/table-page.css" rel="stylesheet"/>
</head>


<body>

<%@ include file="util/header.html" %>

<div class="container item-container">

    <div class="row justify-content-md-center mb-3 item-title">
        <div class="col-lg-9  themed-grid-col ">
            <div class="d-flex justify-content-center align-items-center">
                <h3>Matches</h3>
            </div>

        </div>
    </div>

    <div class="row justify-content-md-center mb-3 items-per-page">
        <div class="col-lg-9  themed-grid-col ">
            <div class="d-flex justify-content-between align-items-center">
                <%@ include file="util/page_size_selector_plugin.html" %>
                <%@ include file="util/search_bar_plugin.html" %>
            </div>
        </div>
    </div>

    <div class="row justify-content-md-center mb-3 ">
        <div class="col-lg-9 themed-grid-col text-center table-container">

            <table class="table item-table">
                <thead>
                <tr>
                    <th scope="col">№</th>
                    <th scope="col">Player 1</th>
                    <th scope="col">Player 2</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>

            <div class="bouncing-ball-loader">
                <img src="static/img/main_icon.svg" alt="Tennis" width="28" height="28"/>
            </div>


        </div>
    </div>




    <%@ include file="util/pagination_plugin.html" %>

</div>
<%@ include file="util/footer.html" %>


<script src="static/js/bootstrap.bundle.min.js"></script>

<script src="app.js"></script>
<script src="static/js/matches.js"></script>
<script src="static/js/table-page.js"></script>

</body>
</html>
