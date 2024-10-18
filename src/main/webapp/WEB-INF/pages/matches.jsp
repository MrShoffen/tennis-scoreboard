<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>

    <title>Matches</title>

    <%-- bootstrap --%>
    <link href="static/css/bootstrap.css" rel="stylesheet"/>

    <%-- Font awesome style --%>
    <link rel="stylesheet" href="static/css/all.min.css">

    <%-- Roboto font --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">

    <!-- Custom page styles -->
    <link href="static/css/custom/matches.css" rel="stylesheet"/>
</head>


<body>

<%@ include file="util/header.html" %>

<div class="container match_container">

    <div class="row justify-content-md-center mb-3 ">
        <div class="col-lg-9  themed-grid-col ">
            <div class="d-flex justify-content-between align-items-center">
                <h3>Matches</h3>
                <%@ include file="util/search_bar.html" %>
            </div>
        </div>
    </div>

    <div class="row justify-content-md-center mb-3 ">
        <div class="col-lg-9 themed-grid-col text-center">

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
                    <th scope="row">1</th>
                    <td>Jacob</td>
                    <td>Thornton</td>
                    <td>Jacob</td>
                </tr>
                <tr>
                    <th scope="row">2</th>
                    <td>Larry the Bird</td>
                    <td>Anna</td>
                    <td>Anna</td>
                </tbody>
            </table>

            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">

<%--                    <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>--%>
                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
<%--                    <li class="page-item"><a class="page-link" href="#">Next</a></li>--%>
                </ul>
            </nav>


        </div>
    </div>

</div>

<%@ include file="util/footer.html" %>

<script src="static/js/bootstrap.bundle.min.js"></script>
<script src="static/js/matches.js"></script>

</body>
</html>
