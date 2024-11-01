<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>

    <title>Players</title>

    <%-- bootstrap --%>
    <link href="static/css/bootstrap.css" rel="stylesheet"/>

    <%-- Font awesome style --%>
    <link rel="stylesheet" href="static/css/all.min.css">

    <%-- Roboto font --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">

    <!-- Custom page styles -->
    <link href="static/css/custom/info_page.css" rel="stylesheet"/>
</head>


<body>

<%@ include file="util/header.html" %>

<div class="container item-container">

    <div class="row justify-content-md-center mb-3 item-title">
        <div class="col-lg-9  themed-grid-col ">
            <div class="d-flex justify-content-center align-items-center">
                <h3>Players</h3>
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
        <div class="col-lg-9 themed-grid-col text-center">

            <table class="table item-table">

                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Player</th>
                    <th scope="col">Count</th>
                </tr>
                </thead>
                <tbody>
                <%-- table content--%>
                </tbody>
            </table>

            <div class="bouncing-ball-loader">
                <!-- Вставьте ваш SVG-код ниже -->
                <img src="static/img/main_icon.svg" alt="Tennis" width="28" height="28"/>
            </div>

        </div>

    </div>
    <%@ include file="util/pagination_plugin.html" %>
</div>
<%@ include file="util/footer.html" %>

<script src="static/js/bootstrap.bundle.min.js"></script>

<script src="app.js"></script>
<script src="static/js/players.js"></script>
<script src="static/js/info_page.js"></script>

</body>
</html>
