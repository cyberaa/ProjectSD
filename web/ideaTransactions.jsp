<%--
  Created by IntelliJ IDEA.
  User: joaosimoes
  Date: 11/16/13
  Time: 5:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link href="assets/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/font-awesome-4.0.3/css/font-awesome.min.css" rel="stylesheet">
    <link href="assets/style.css" rel="stylesheet">
    <link href="assets/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css" />
    <link href="assets/jquery.pnotify.default.icons.css" media="all" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="assets/jquery.pnotify.min.js"></script>
    <script type="text/javascript">

        window.onload = function() {
            initializeWS();
        }
    </script>
    <title>Idea Broker</title>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="container" style="padding-left: 0px; padding-right: 0px;">
        <div class="navbar-header">
            <span class="navbar-brand"> <span class="fontBrand"> <strong>Idea Broker</strong> </span> </span>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-lightbulb-o"> </i> Ideas<!--<b class="caret"></b>--></a>
                    <ul class="dropdown-menu">
                        <li><a href="<s:url action='ideaAction'/>"><i class="fa fa-flash"></i> New idea</a></li>
                        <li class="divider"></li>
                        <li><a href="<s:url action='watchlistAction'/>"><i class="fa fa-star"></i> Watchlist</a></li>
                        <li><a href="<s:url action='hallOfFameAction'/>"><i class="fa fa-trophy"></i> Hall of fame</a></li>
                    </ul>
                </li>
                <li><a href="<s:url action='topicsAction'/>"> <i class="fa fa-bars"></i> Topics </a></li>
                <li><a href="<s:url action='portfolioAction'/>"> <i class="fa fa-tasks"></i> Portfolio </a></li>
            </ul>
            <form action="searchAction.action" method="GET" class="navbar-form navbar-left" role="form">
                <fieldset>
                    <div class="form-group">
                        <input type="text" name="searchKey" class="form-control" placeholder="Search by topic or idea" size="45">
                        <input type="hidden" id="search" name="wat" value="Idea">
                    </div>
                    <div class="btn-group">
                        <button type="submit" class="btn btn-default" style="height: 34px;"> <i class="fa fa-search"></i> </button>
                        <button type="button" class="btn btn-default dropdown-toggle" style="height: 34px;" data-toggle="dropdown">
                            <span class="caret"></span>
                            <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a onclick="javascript:document.getElementById('search').value = 'Topic'">Topic</a></li>
                            <li><a onclick="javascript:document.getElementById('search').value = 'Idea'">Idea</a></li>
                        </ul>
                    </div>
                </fieldset>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a><strong><span style="">Balance: ${session.user.money}</span></strong></a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <s:property value="%{#session.user.username}" /><!--<b class="caret"></b>--></a>
                    <ul class="dropdown-menu">
                        <li><a href="#"><i class="fa fa-power-off"></i> Log out</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div>
</nav>

<!-- Main container -->
<div class="container" style="padding-left: 200px; padding-right: 200px;">

    <div class="panel panel-default">
        <div class="panel-body" style="">
            <div style="text-align: right; margin-bottom: 10px;">
                <i class="fa fa-user"></i> <strong>${ideaOwner}</strong>
            </div>
            <span style="">${ideaText}</span>
        </div>

    </div>

    <table class="table table-hover table-condensed table-bordered" style="border-radius: 10px;">
        <thead>
        <tr style="text-align: center;">
            <th style="text-align: center;">Buyer</th>
            <th style="text-align: center;">Seller</th>
            <th style="text-align: center;">Total Price</th>
            <th style="text-align: center;">Total shares</th>
            <th style="text-align: center;">Total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="transaction" items="${transactions}">
            <tr style="text-align: center;">
                <td>${transaction.buyer}</td>
                <td>${transaction.seller}</td>
                <td>${transaction.total}</td>
                <td>${transaction.parts}</td>
                <td>${transaction.total * transaction.parts}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>


<script src="assets/jquery.js"></script>
<script src="assets/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="assets/noty-2.1.3/js/noty/jquery.noty.js"></script>
<script type="text/javascript" src="assets/noty-2.1.3/js/noty/layouts/top.js"></script>
<script type="text/javascript" src="assets/noty-2.1.3/js/noty/layouts/topLeft.js"></script>
<script type="text/javascript" src="assets/noty-2.1.3/js/noty/layouts/topRight.js"></script>
<!-- You can add more layouts if you want -->
<script type="text/javascript" src="assets/noty-2.1.3/js/noty/themes/default.js"></script>
<script src="assets/custom.js"></script>


</body>
</html>