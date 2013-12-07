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
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <title>Idea Broker</title>
</head>
<body background="assets/textures/escheresque_ste.png">

<script>
    (function (d) {
        var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
        if (d.getElementById(id)) { return; }
        js = d.createElement('script'); js.id = id; js.async = true;
        js.src="//connect.facebook.net/en_US/all.js";
        ref.parentNode.insertBefore(js, ref);
    }(document));


    window.fbAsyncInit = function() {
        FB.init({
            appId: 436480809808619,
            channelUrl: '//' + window.location.hostname + '/channel',
            status: true,
            cookie: true,
            xfbml: true
        })

        FB.Event.subscribe('auth.authResponseChange', function(response) {
            document.getElementById('tokenFacebook').value = response.authResponse.accessToken;
        })
    }


    function Login() {
        FB.login(function(response) {
            if(response.authResponse) {
                FB.api('/me', function(response) {
                    alert(response.id);
                });
            }
        });
    }
</script>

<div class="container">

    <s:set name="responseLogin" value="responseLogin"/>

    <div style="text-align: center; margin-bottom: 80px;">
       <span class="fontBrand" style="font-size: 100px; color: #FFF;">IDEA BROKER</span>
    </div>
    <%--<div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please sign in or <a href="<s:url action='register'/>"> sign up </a> </h3>
                        <h3 class="panel-title">Please sign in with your Facebook account </h3>
                </div>
                <div class="panel-body">
                    <form action="loginFaceAction.action" accept-charset="UTF-8" role="form" method="POST">
                        <fieldset>
                            <s:if test="%{#responseLogin == 'failedAuth'}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="loginUsername">Username or password incorrect</label>
                                    <input class="form-control" placeholder="E-mail" name="username" type="text" id="loginUsername">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                            </s:if>
                            <s:else>
                                <div class="form-group">
                                    <input class="form-control" placeholder="E-mail" name="username" type="text">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                            </s:else>
                            <input class="btn btn-lg btn-success btn-block" type="submit" value="Login">
                            <input type="hidden" name="token" value="" id="tokenFacebook" >
                                <input class="btn btn-lg btn-success btn-block" type="submit" value="Login Facebook" >
                                <div id="auth-status" style="text-align: center; margin-top: 20px;">
                                    <div id="auth-loggedout">
                                        <div class="fb-login-button" autologoutlink="true" scope="email,user_checkins,publish_actions,publish_stream,read_stream">Login  with Facebook</div>
                                    </div>

                                    <div id="auth-loggedin" style="display: none"></div>
                                </div>

                                <div style="margin-top: 10px;"><strong>How to Login:</strong></div>
                                <div style="text-align: justify;">
                                    If you are not logged at Facebook, you need "Login with Facebook first and validate you're Facebook data."
                                    <br>
                                    Next, press "Login" button to access application.
                                </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div> --%>
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please sign in or <a href="<s:url action='register'/>"> sign up </a> </h3>
                </div>
                <div class="panel-body">
                    <form action="loginAction.action" method="POST" accept-charset="UTF-8" role="form">
                        <fieldset>
                            <s:if test="%{#responseLogin == 'failedAuth'}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="loginUsername">Username or password incorrect</label>
                                    <input class="form-control" placeholder="Username" name="username" type="text" id="loginUsername">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                            </s:if>
                            <s:else>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Username" name="username" type="text">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                            </s:else>
                            <input class="btn btn-lg btn-success btn-block" type="submit" value="Login">
                        </fieldset>
                    </form>
                    <hr/>
                    <div style="text-align: center;"><h4>OR</h4></div>
                    <form action="loginFaceAction.action" accept-charset="UTF-8" role="form" method="POST">
                        <input type="hidden" name="token" value="" id="tokenFacebook" >
                        <input class="btn btn-lg btn-facebook btn-block" type="submit" value="Login via facebook">
                        <div id="auth-status" style="text-align: center; margin-top: 20px;">
                            <div id="auth-loggedout">
                                <div class="fb-login-button" autologoutlink="true" scope="email,user_checkins,publish_actions,publish_stream,read_stream">Login  with Facebook</div>
                            </div>
                            <div id="auth-loggedin" style="display: none"></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="assets/jquery.js"></script>
<script src="assets/dist/js/bootstrap.min.js"></script>


</body>
</html>