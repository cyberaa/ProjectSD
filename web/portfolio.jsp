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
                <li class="active"><a href="<s:url action='portfolioAction'/>"> <i class="fa fa-tasks"></i> Portfolio </a></li>
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

    <c:choose>
        <c:when test="${responseDel == 'nfo'}">
            <div class='alert alert-danger alert-dismissable'>
                <button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>
                <strong>Error!</strong> You're not full owner of idea.
            </div>
        </c:when>
        <c:when test="${responseDel == 'success'}">
            <div class='alert alert-success alert-dismissable'>
                <button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>
                <strong>Success!</strong> Idea deleted successfully!
            </div>
        </c:when>
        <c:otherwise>

        </c:otherwise>
    </c:choose>

    <div class="well well-sm">
        <h3><i class="fa fa-tasks"></i> Your Portfolio</h3>
    </div>

    <c:set var="count" value="0" scope="page" />

    <c:forEach var="idea" items="${ideas}">
        <c:url value="viewSharesAction.action" var="buySharesTag">
            <c:param name="ideaId" value="${idea.idea_id}"/>
            <c:param name="ideaText" value="${idea.text}" />
            <c:param name="ideaOwner" value="${idea.ideaOwner}" />
        </c:url>
        <c:url value="deleteIdeaAction.action" var="deleteIdeaTag">
            <c:param name="ideaId" value="${idea.idea_id}"/>
        </c:url>

            <div class="panel panel-default">
                <a onclick="togglePanel('${count}');" style="text-decoration: none; cursor: pointer; color: #000;">
                    <div class="panel-body" style="">
                        <div style="text-align: right; margin-bottom: 10px;">
                            <i class="fa fa-user"></i> <strong>${idea.ideaOwner}</strong>
                        </div>
                        <span style="">${idea.text}</span>
                    </div>
                </a>
                <div class="panel-heading" id="${count}" style="display: none; padding-bottom: 0px;">
                    <form action="setValueAction.action" accept-charset="UTF-8" role="form" method="POST" role="form" style="margin-bottom: 0px;">
                        <fieldset>
                            <div class="row" style="margin-bottom: 0px;">
                                <div class="col-md-3" style="padding-top: 8px;">
                                    <strong>Share Price:</strong> ${idea.value}
                                </div>
                                <div class="col-md-3" style="padding-top: 8px; margin-bottom: 0px;">
                                    <strong>Shares:</strong> ${idea.parts}
                                </div>
                                <div class="col-md-2" style="padding-right: 0px; margin-bottom: 0px;">
                                    <div class="form-group" style="text-align: right;">
                                        <input type="text" class="form-control" id="sharePrice" name="sharePrice" placeholder="Share Price">
                                    </div>
                                </div>
                                <div class="col-md-1" style="text-align: left; padding-left: 5px; margin-bottom: 0px;">
                                    <input type="hidden" value="${idea.idea_id}" name="ideaId">
                                    <button type="submit" class="btn btn-info" style=""><strong>Set</strong></button>
                                </div>


                                <div class="col-md-1" style="margin-bottom: 0px;">
                                    <button style="height: 35px;" type="button" class="btn btn-success" onclick="location.href='<c:out value="${deleteIdeaTag}"/>'"><i class="fa fa-times"></i></button>
                                </div>
                                <div class="col-md-2" style="margin-bottom: 0px;">
                                    <div style="text-align: right">
                                        <button type="button" class="btn btn-danger" onclick="location.href='<c:out value="${buySharesTag}"/>'">Buy Shares</button>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        <c:set var="count" value="${count + 1}" scope="page"/>
    </c:forEach>

</div>


<script src="assets/jquery.js"></script>
<script src="assets/dist/js/bootstrap.min.js"></script>
<script src="assets/jquery-ui-1.10.3/ui/jquery-ui.js"></script>
<script type="text/javascript" src="assets/noty-2.1.3/js/noty/jquery.noty.js"></script>
<script type="text/javascript" src="assets/noty-2.1.3/js/noty/layouts/top.js"></script>
<script type="text/javascript" src="assets/noty-2.1.3/js/noty/layouts/topLeft.js"></script>
<script type="text/javascript" src="assets/noty-2.1.3/js/noty/layouts/topRight.js"></script>
<!-- You can add more layouts if you want -->
<script type="text/javascript" src="assets/noty-2.1.3/js/noty/themes/default.js"></script>
<script src="assets/custom.js"></script>



</body>
</html>