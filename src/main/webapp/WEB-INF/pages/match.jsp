<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>

    <title>Match result</title>
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
    <link href="static/css/custom/match.css" rel="stylesheet"/>


</head>
<body>


<%@ include file="util/header.html" %>


<div class="container page-content">

    <div class="row justify-content-md-center mb-3 item-title">
        <div class="col-lg-9  themed-grid-col ">
            <div class="d-flex justify-content-center align-items-center">
                <h3>Match</h3>
            </div>

        </div>
    </div>


    <div class="row justify-content-md-center">

        <div class="col-lg-9 themed-grid-col text-center">

            <div id="first-player">
                <div class="name d-grid" style="grid-template-columns: repeat(1, 1fr);">

                    <div class="col d-flex justify-content-center align-items-center">
                        <h4>...</h4>
                    </div>
                </div>
            </div>

            <div id="vs">
                <div class="name d-grid" style="grid-template-columns: repeat(1, 1fr);">

                    <div class="col d-flex justify-content-center align-items-center">
                        <h4>VS</h4>
                    </div>
                </div>
            </div>


            <div id="second-player">
                <div class="name d-grid" style="grid-template-columns: repeat(1, 1fr);">
                    <div class="col d-flex justify-content-center align-items-center">
                        <h4>...</h4>
                    </div>
                </div>
            </div>

            <hr>

            <div id="win-label">
                <div class="name d-grid" style="grid-template-columns: repeat(1, 1fr);">

                    <div class="col d-flex justify-content-center align-items-center">
                        <h4><i class="fa-solid fa-trophy" style="color: rgb(214,154,0)"></i> WINNER <i
                                class="fa-solid fa-trophy" style="color: rgb(214,154,0)"></i></h4>
                    </div>
                </div>
            </div>


            <div id="winner">
                <div class="name d-grid" style="grid-template-columns: repeat(1, 1fr);">
                    <div class="col d-flex justify-content-center align-items-center">
                        <h4>...</h4>
                    </div>
                </div>
            </div>

        </div>


    </div>


</div>


<%@ include file="util/footer.html" %>

<script src="static/js/bootstrap.bundle.min.js"></script>

<script src="app.js"></script>
<script src="static/js/match.js"></script>

</body>
</html>
