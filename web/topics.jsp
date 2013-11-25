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
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link href="assets/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/font-awesome-4.0.3/css/font-awesome.min.css" rel="stylesheet">
    <link href="assets/style.css" rel="stylesheet">
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
                    <li><a href="<s:url action='idea'/>"><i class="fa fa-flash"></i> New idea</a></li>
                    <li class="divider"></li>
                    <li><a href="#"><i class="fa fa-star"></i> Watchlist</a></li>
                    <li><a href="#"><i class="fa fa-trophy"></i> Hall of fame</a></li>
                </ul>
            </li>
            <li class="active"><a href="<s:url action='topics'/>"> <i class="fa fa-bars"></i> Topics </a></li>
            <li><a href="#"> <i class="fa fa-tasks"></i> Portfolio </a></li>
        </ul>
        <form class="navbar-form navbar-left" role="search">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Search by topic or idea" size="45">
            </div>
            <div class="btn-group">
                <button type="button" class="btn btn-default" style="height: 34px;"> <i class="fa fa-search"></i> </button>
                <button type="button" class="btn btn-default dropdown-toggle" style="height: 34px;" data-toggle="dropdown">
                    <span class="caret"></span>
                    <span class="sr-only">Toggle Dropdown</span>
                </button>
                <ul class="dropdown-menu" role="menu">
                    <li><a href="#">Topic</a></li>
                    <li><a href="#">Idea</a></li>
                </ul>
            </div>
        </form>
        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">João Simões<!--<b class="caret"></b>--></a>
                <ul class="dropdown-menu">
                    <li><a href="#">Log out</a></li>
                </ul>
            </li>
        </ul>
    </div><!-- /.navbar-collapse -->
    </div>
</nav>

<!-- Main container -->
<div class="container" style="padding-left: 200px; padding-right: 200px;">

    <div class="panel panel-default">
        <!-- Default panel contents -->
        <a style="cursor: pointer; text-decoration: none;" onclick="togglePanel('p1');">
            <div class="panel-heading" style=" background-color: #f5f5f5;">
                <h3> <span style="color: #007765;">Situação económica da europa </span></h3>
            </div>
        </a>

        <!-- List group -->
        <ul class="list-group" id="p1" style="display: none;">
            <li class="list-group-item">
                <div style="">
                    <strong> <i class="fa fa-user"></i> João Simões</strong>
                </div>
                <div style="padding-left: 10px; padding-top: 5px;">
                    A europa está a atravessar uma crise económica derivada da má gestão financeira praticada pelos líderes politicos
                </div>

            </li>
            <li class="list-group-item">
                <div style="">
                    <strong> <i class="fa fa-user"></i> João Simões </strong>
                </div>
                <div style="padding-left: 10px; padding-top: 5px;">
                    A europa está a atravessar uma crise económica derivada da má gestão financeira praticada pelos líderes politicos
                </div>

            </li>
        </ul>
    </div>

</div>


<script src="assets/jquery.js"></script>
<script src="assets/dist/js/bootstrap.min.js"></script>
<script src="assets/custom.js"></script>


</body>
</html>