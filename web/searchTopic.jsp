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
                <li class="active"><a href="<s:url action='topicsAction'/>"> <i class="fa fa-bars"></i> Topics </a></li>
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
                    <a><strong><span style="">Balance: 3000</span></strong></a>
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

    <s:set name="responseTopic" value="responseTopic"/>
    <s:if test="%{#response == 'success'}">
        <div class='alert alert-success alert-dismissable'>
            <button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>
            <strong>Success!</strong>  Topic submitted successfully
        </div>
    </s:if>
    <s:elseif test="%{#response == 'rmi'}">
        <div class='alert alert-danger alert-dismissable'>
            <button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>
            <strong>Error!</strong> An internal error occurred during topic submission
        </div>
    </s:elseif>
    <s:elseif test="%{#response == 'topicExists'}">
        <div class='alert alert-danger alert-dismissable'>
            <button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>
            <strong>Error!</strong> Topic already exists
        </div>
    </s:elseif>

    <div class="panel panel-default">
        <div class="panel-body">
            <c:forEach var="topic" items="${topics}" >
                <c:url value="topicIdeasAction.action" var="topicIdeasTag">
                    <c:param name="topicId" value="${topic.id}"/>
                    <c:param name="topicText" value="${topic.text}" />
                </c:url>

                <a style="cursor: pointer; text-decoration: none" href="<c:out value='${topicIdeasTag}'/>" >
                    <div class="well well-sm" style="text-align: center">
                        <h3> <span style="color: #007765; font-size: 18px;">${topic.text}</span></h3>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
</div>


<script src="assets/jquery.js"></script>
<script src="assets/dist/js/bootstrap.min.js"></script>
<script src="assets/custom.js"></script>


</body>
</html>