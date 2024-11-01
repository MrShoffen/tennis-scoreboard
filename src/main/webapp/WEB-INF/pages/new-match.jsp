<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>

    <title>New Match</title>

    <%-- bootstrap --%>
    <link href="static/css/bootstrap.css" rel="stylesheet"/>

    <%-- Font awesome style --%>
    <link rel="stylesheet" href="static/css/all.min.css">

    <%-- Roboto font --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">

    <!-- Custom page styles -->
    <link href="static/css/custom/new-match.css" rel="stylesheet"/>
</head>


<body>

<%@ include file="util/header.html" %>

<div class="container item-container">

    <div class="row justify-content-md-center mb-3 item-title">
        <div class="col-lg-9  themed-grid-col ">
            <div class="d-flex justify-content-center align-items-center">
                <h3>New Match</h3>
            </div>

        </div>
    </div>

    <div class="row justify-content-md-center mb-3 item-title">
        <div class="col-lg-9  themed-grid-col ">


            <div class="form-group">

                <label for="player_1_form"><h5>Player 1</h5></label>
                <div style="position: relative;">
                    <input class="player_form" id="player_1_form" type="text" placeholder="Player 1 Name..." data-bs-toggle="tooltip" data-bs-placement="top" title="Latin Letters, '.', '-'">
                    <div class="invisible player_ac" id="player_1_ac"></div>
                    <div class="invisible error-popup" >Error</div>
                </div>

                <label for="player_2_form"><h5>Player 2</h5></label>
                <div style="position: relative;">
                    <input class="player_form" id="player_2_form" type="text" placeholder="Player 2 Name..." data-bs-toggle="tooltip" data-bs-placement="top" title="Latin Letters, '.', '-'">
                    <div class="position-absolute invisible player_ac" id="player_2_ac"></div>
                    <div class="invisible error-popup" >Error</div>
                </div>

                <div class="form-group">
                    <button class="btn-start">Start Match <i class="fa-solid fa-arrow-right"></i></button>
                </div>

            </div>


        </div>
    </div>


</div>
<%@ include file="util/footer.html" %>


<script src="static/js/bootstrap.bundle.min.js"></script>

<script src="app.js"></script>

<script src="static/js/new-match.js"></script>


</body>
</html>
