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

    <div class="panel panel-default">
        <div class="panel-body" style="">
            <div style="text-align: right; margin-bottom: 10px;">
                <i class="fa fa-user"></i> <strong>${ideaOwner}</strong>
            </div>
            <span style="">${ideaText}</span>
        </div>

    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-money" style="margin-right: 3px;"> </i> Buy Shares</h3>
        </div>
        <div class="panel-body">
            <s:if test="%{#session.user.username == 1}">
                <form action="buySharesAction.action" accept-charset="UTF-8" role="form" method="POST" role="form">
                    <fieldset>
                        <div class="row">
                            <div class="col-md-3">
                                <label for="numberOfShares">Shares</label>
                                <input type="text" id="numberOfShares" class="form-control" name="share_num" placeholder="">
                            </div>
                            <div class="col-md-3">
                                <label for="pricePerShare">Price</label>
                                <input type="text" id="pricePerShare" class="form-control" name="price_share" placeholder="">
                            </div>
                            <div class="col-md-3">
                                <label for="newPricePerShare">New Price</label>
                                <input type="text" id="newPricePerShare" class="form-control" name="new_price_share" placeholder="">
                            </div>
                            <div class="col-md-3">
                                <input type="hidden" name="ideaText" value="${ideaText}">
                                <input type="hidden" name="ideaOwner" value="${ideaOwner}">
                                <input type="hidden" name="ideaId" value="${ideaId}">
                                <div style="padding-top: 25px;">
                                    <button type="submit" class="btn btn-default" style="background-color: #007765; color: #f5f5f5; width: 100%;"><strong>Buy</strong></button>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </s:if>
            <s:else>
                <form action="buySharesAction.action" accept-charset="UTF-8" role="form" method="POST" role="form">
                    <fieldset>
                        <input type="hidden" name="ideaId" value="${ideaId}">
                        <button type="submit" class="btn btn-default" style="background-color: #007765; color: #f5f5f5; width: 100%;"><strong>Buy</strong></button>
                    </fieldset>
                </form>
            </s:else>
        </div>
    </div>
    <table class="table table-hover table-condensed table-bordered" style="border-radius: 10px;">
        <thead>
        <tr style="text-align: center;">
            <th style="text-align: center;">User</th>
            <th style="text-align: center;">Number of Share</th>
            <th style="text-align: center;">Price per share</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="share" items="${shares}">
            <tr style="text-align: center;">
                <td>${share.username}</td>
                <td>${share.parts}</td>
                <td>${share.value}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>


<script src="assets/jquery.js"></script>
<script src="assets/dist/js/bootstrap.min.js"></script>
<script src="assets/custom.js"></script>


</body>
</html>