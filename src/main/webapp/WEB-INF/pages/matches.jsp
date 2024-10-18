<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="static_path" value="${pageContext.request.contextPath}/static"/>


<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>


    <title>Matches</title>

    <%-- bootstrap --%>
    <link href="${static_path}/css/bootstrap.css" rel="stylesheet"/>

    <%-- Font awesome style --%>
    <link rel="stylesheet" href="${static_path}/css/all.min.css">

    <%-- Roboto font --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">


    <!-- Custom page styles -->
    <link href="${static_path}/css/custom/matches.css" rel="stylesheet"/>


    <link rel="stylesheet" href="${static_path}/css/custom/search_bar.css">


</head>


<body>

<%@ include file="header.html" %>


<!-- pagination -->


<div class="container match_container">

    <div class="row justify-content-md-center mb-3 ">
        <div class="col-lg-9  themed-grid-col ">
            <div class="d-flex justify-content-between align-items-center">
                <h3>Matches</h3>
                <div class="search-box">
                    <button class="btn-search"><i class="fa-solid fa-magnifying-glass"></i></button>
                    <input type="text" class="input-search" placeholder="Type to Search...">
                </div>
            </div>
        </div>
    </div>

    <div class="row justify-content-md-center mb-3 ">
        <div class="col-lg-9 themed-grid-col text-center">

            <!--            <h3>Matches</h3>-->
            <!--            <br>-->

            <!--            <div class="search-box ">-->

            <!--                <button class="btn-search"><i class="fa-solid fa-magnifying-glass"></i></button>-->
            <!--                <input type="text" class="input-search" placeholder="Type to Search...">-->
            <!--            </div>-->

            <table class="table match_table">

                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Player 1</th>
                    <th scope="col">Player 2</th>
                    <th scope="col">Winner</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th scope="row">32</th>
                    <td>Ma 34rtgewrg gserger g rgdrk</td>
                    <td>Ott sdfqwferq erge ergqer ferqfo</td>
                    <td>@msdfs sdf safsd werweadgsad do</td>
                </tr>
                <tr>
                    <th scope="row">2</th>
                    <td>Jacob</td>
                    <td>Thornton</td>
                    <td>@fat</td>
                </tr>
                <tr>
                    <th scope="row">3</th>
                    <td>Larry the Bird</td>
                    <td></td>
                    <td>@twitter</td>
                </tr>
                <th scope="row">1</th>
                <td>Ma 34rtgewrg gserger g rgdrk</td>
                <td>Ott sdfqwferq erge ergqer ferqfo</td>
                <td>@msdfs sdf safsd werweadgsad do</td>
                </tr>
                <tr>
                    <th scope="row">2</th>
                    <td>Jacob</td>
                    <td>Thornton</td>
                    <td>@fat</td>
                </tr>
                <tr>
                    <th scope="row">3</th>
                    <td>Larry the Bird</td>
                    <td></td>
                    <td>@twitter</td>
                </tr>
                <th scope="row">1</th>
                <td>Ma 34rtgewrg gserger g rgdrk</td>
                <td>Ott sdfqwferq erge ergqer ferqfo</td>
                <td>@msdfs sdf safsd werweadgsad do</td>
                </tr>
                <tr>
                    <th scope="row">2</th>
                    <td>Jacob</td>
                    <td>Thornton</td>
                    <td>@fat</td>
                </tr>
                <tr>
                    <th scope="row">3</th>
                    <td>Larry the Bird</td>
                    <td></td>
                    <td>@twitter</td>
                </tr>
                </tbody>
            </table>

            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">

                    <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                </ul>
            </nav>


        </div>
    </div>

</div>


<%@ include file="footer.jsp" %>


<script src="${static_path}/js/search_bar_script.js"></script>
<script src="${static_path}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
