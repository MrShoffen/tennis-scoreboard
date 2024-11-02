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
    <link href="static/css/custom/match-score.css" rel="stylesheet"/>
</head>


<body>

<%@ include file="util/header.html" %>


<div class="container page-content">


    <div class="row justify-content-md-center">
        <!-- Используйте 'col-lg-9' для того, чтобы ширина соответствовала вашему столбцу -->
        <div class="col-lg-9 themed-grid-col text-center">

            <div id="first-player">
                <div class="name d-grid" style="grid-template-columns: repeat(1, 1fr);">
                    <!-- Один сплошной элемент на всю ширину контейнера -->
                    <div class="col d-flex justify-content-center align-items-center">
                        <h4>Player 1</h4>
                    </div>
                </div>

                <div class="d-grid first-player-score" style="grid-template-columns: repeat(4, 1fr);">
                    <!-- Каждый отдельный элемент -->
                    <div class="points score-element d-flex justify-content-center align-items-center">
                       45
                    </div>
                    <div class="set1 score-element d-flex justify-content-center align-items-center">
                        6
                    </div>
                    <div class="set2 score-element d-flex justify-content-center align-items-center">
                        8
                    </div>
                    <div class="set3 score-element d-flex justify-content-center align-items-center">
                        9
                    </div>
                </div>

            </div>


            <div class="d-grid score-names" style="grid-template-columns: repeat(4, 1fr);">
                <!-- Каждый отдельный элемент -->
                <div class="points score-element d-flex justify-content-center align-items-center">
                    <span>GAME</span>
                </div>
                <div class="set1 score-element d-flex justify-content-center align-items-center">
                    <span>SET 1</span>
                </div>
                <div class="set2 score-element d-flex justify-content-center align-items-center">
                    <span>SET 2</span>
                </div>
                <div class="set3 score-element d-flex justify-content-center align-items-center">
                    <span>SET 3</span>
                </div>

            </div>

            <div id="second-player">

                <div class="d-grid second-player-score" style="grid-template-columns: repeat(4, 1fr);">
                    <!-- Каждый отдельный элемент -->
                    <div class="points score-element d-flex justify-content-center align-items-center">
                        45
                    </div>
                    <div class="set1 score-element d-flex justify-content-center align-items-center">
                        6
                    </div>
                    <div class="set2 score-element d-flex justify-content-center align-items-center">
                        8
                    </div>
                    <div class="set3 score-element d-flex justify-content-center align-items-center">
                        9
                    </div>
                </div>

                <div class="name d-grid" style="grid-template-columns: repeat(1, 1fr);">
                    <!-- Один сплошной элемент на всю ширину контейнера -->
                    <div class="col score-element d-flex justify-content-center align-items-center">
                        <h4>Player 2</h4>
                    </div>
                </div>

            </div>

            <hr>

            <div class="button-group d-grid" style="grid-template-columns: repeat(2, 1fr);">

                <div class="first-player-point-button d-flex justify-content-center align-items-center">
                    <button class="btn-point">Player 1  <i class="fa-solid fa-arrow-right"></i></button>
                </div>


                <div class="second-player-point-button d-flex justify-content-center align-items-center">
                    <button class="btn-point">Player 2 <i class="fa-solid fa-arrow-right"></i></button>

                </div>

            </div>


        </div>


    </div>


</div>


<%@ include file="util/footer.html" %>

<script src="static/js/bootstrap.bundle.min.js"></script>

<script src="app.js"></script>
<script src="static/js/match-score.js"></script>

</body>
</html>
