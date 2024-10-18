<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>

    <title>Tennis Scoreboard</title>

    <%-- bootstrap --%>
    <link href="static/css/bootstrap.css" rel="stylesheet"/>

    <%-- Font awesome style --%>
    <link rel="stylesheet" href="static/css/all.min.css">

    <%-- Roboto font --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">

    <!-- Custom page styles -->
    <link href="static/css/custom/index.css" rel="stylesheet"/>

</head>


<body>

<%@ include file="WEB-INF/pages/util/header.html" %>


<div class="container page_content ">


    <div class="row justify-content-md-center mb-3 ">
        <div class="col-lg-9 themed-grid-col text-center">A simple project for keeping score in a tennis match. Inspired
            by
            <a href="https://zhukovsd.github.io/java-backend-learning-course/projects/tennis-scoreboard/">Sergey Zhukov
                Java Roadmap</a>
        </div>
    </div>

    <div class="row justify-content-md-center mb-3">
        <img src="static/img/sep.svg" alt="Tennis" width="28" height="28"/>
    </div>

    <div class="row justify-content-md-center mb-6">
        <div class="col-lg-8 themed-grid-col text-center"> To create a new match, click the New Match button.</div>
    </div>

    <div class="row justify-content-md-center mb-3">
        <img src="static/img/sep.svg" alt="Tennis" width="28" height="28"/>
    </div>

    <div class="row justify-content-md-center mb-3">
        <div class="col-lg-8 themed-grid-col text-center"> To view all players, click Players button.</div>
    </div>

    <div class="row justify-content-md-center mb-3">
        <img src="static/img/sep.svg" alt="Tennis" width="28" height="28"/>
    </div>

    <div class="row justify-content-md-center mb-3">
        <div class="col-lg-8 themed-grid-col text-center">To view all completed matches, click Matches.</div>
    </div>

    <div class="row justify-content-md-center mb-3">
        <img src="static/img/sep.svg" alt="Tennis" width="28" height="28"/>
    </div>

    <div class="row justify-content-md-center mb-3">
        <div class="col-lg-9 themed-grid-col text-center">hello fsadfsad аыва ipsum dolor sit amet consectetur
            adipisicing elit.
            Perspiciatis quis adipisci doloremque eligendi, maxime fugiat voluptatem culpa tenetur veniam asperiores
            minus provident obcaecati cumque laboriosam saepe magni nam. Adipisci, dolorem dignissimos nostrum nemo
            animi reiciendis! Distinctio sapiente ab ipsum fugit, blanditiis animi, modi officia numquam ipsam unde,
            dolores nisi id.
        </div>
    </div>


</div>

<%@ include file="WEB-INF/pages/util/footer.html" %>

<script src="static/js/bootstrap.bundle.min.js"></script>
</body>
</html>
