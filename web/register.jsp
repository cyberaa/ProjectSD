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
<body background="assets/textures/escheresque_ste.png">

<div class="container">
    <div style="text-align: center; margin-bottom: 80px;">
        <span class="fontBrand" style="font-size: 100px; color: #FFF;">IDEA BROKER</span>
    </div>
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please sign up or <a href="<s:url action='index'/>"> sign in </a> </h3>
                </div>
                <div class="panel-body">
                    <form action="registerAction.action" accept-charset="UTF-8" role="form" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="E-mail" name="username" type="text">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" name="password" type="password" value="">
                            </div>
                            <input class="btn btn-lg btn-success btn-block" style="background-color: #4D68C9; border-color: #084AD7;" type="submit" value="Register">
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="assets/jquery.js"></script>
<script src="assets/dist/js/bootstrap.min.js"></script>
<script src="assets/custom.js"></script>


</body>
</html>