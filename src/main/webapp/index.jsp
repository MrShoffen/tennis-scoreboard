<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>

    <title>Tennis Scoreboard</title>
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
        <img src="static/img/sep.svg" alt="Tennis" width="20" height="20"/>
    </div>

    <div class="row justify-content-md-center mb-6">
        <div class="col-lg-8 themed-grid-col text-center"> To create a new match, click the New Match button.</div>
    </div>

    <div class="row justify-content-md-center mb-3">
        <img src="static/img/sep.svg" alt="Tennis" width="20" height="20"/>
    </div>

    <div class="row justify-content-md-center mb-3">
        <div class="col-lg-8 themed-grid-col text-center"> To view all players, click Players button.</div>
    </div>

    <div class="row justify-content-md-center mb-3">
        <img src="static/img/sep.svg" alt="Tennis" width="20" height="20"/>
    </div>

    <div class="row justify-content-md-center mb-3">
        <div class="col-lg-8 themed-grid-col text-center">To view all completed matches, click Matches.</div>
    </div>

    <div class="row justify-content-md-center mb-3">
        <img src="static/img/sep.svg" alt="Tennis" width="20" height="20"/>
    </div>

    <div class="row justify-content-md-center mb-3">
        <div class="col-lg-9 themed-grid-col text-center">
            <h4>Technologies and resources used in the project</h4>
            <hr>
            <h5>Frontend</h5>

            <img src="static/img/sep.svg" alt="Tennis" width="12" height="12"/>  Bootstrap 5 <a href="https://getbootstrap.com/docs/5.0/examples/">Examples</a><br>
            <img src="static/img/sep.svg" alt="Tennis" width="12" height="12"/>  JavaScript <a href="https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch">Fetch API</a><br>
            <img src="static/img/sep.svg" alt="Tennis" width="12" height="12"/>  Font Awesome <a href="https://fontawesome.com/">Icons</a><br>
            <br>

            <h5>Backend</h5>
            <img src="static/img/sep.svg" alt="Tennis" width="12" height="12"/>  Java 22 Core (with Jakarta Servlets)<br>
            <img src="static/img/sep.svg" alt="Tennis" width="12" height="12"/>  Gradle<br>
            <img src="static/img/sep.svg" alt="Tennis" width="12" height="12"/>  Hibernate ORM<br>
            <img src="static/img/sep.svg" alt="Tennis" width="12" height="12"/>  Hibernate <a href="https://hibernate.org/validator/">Validator</a><br>
            <img src="static/img/sep.svg" alt="Tennis" width="12" height="12"/>  H2 <a href="https://www.h2database.com/html/main.html">Database</a><br>
            <img src="static/img/sep.svg" alt="Tennis" width="12" height="12"/>  Guice <a href="https://github.com/google/guice/wiki/">Dependency Injection</a><br>
            <img src="static/img/sep.svg" alt="Tennis" width="12" height="12"/>  MapStruct <a href="https://mapstruct.org/">Mapping</a><br>
            <img src="static/img/sep.svg" alt="Tennis" width="12" height="12"/>  Apache Tomcat 10 <a href="https://tomcat.apache.org/tomcat-10.1-doc/index.html">Servlet Container</a><br>
            <img src="static/img/sep.svg" alt="Tennis" width="12" height="12"/>  JUnit 5 and AssertJ Library for Tests<br>
        </div>
    </div>


</div>

<%@ include file="WEB-INF/pages/util/footer.html" %>

<script src="static/js/bootstrap.bundle.min.js"></script>
</body>
</html>
