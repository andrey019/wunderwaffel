<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <link rel="shortcut icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link rel="icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="/resources/js/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="/resources/js/todoScripts.js"></script>
    <sec:csrfMetaTags />
    <meta http-equiv="refresh" content="${pageContext.session.maxInactiveInterval}">
    <title>WunderWaffel</title>
</head>
<body style="background-image: url('/resources/images/background2.jpg'); background-attachment: fixed">

<nav class="navbar navbar-inverse navbar-fixed-top" style="color: #3e8f3e">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">

            </button>
            <a class="navbar-brand" href="#" style="font-size: 160%; padding-left: 30px">WunderWaffel</a>
        </div>

        <p id="navbarText" class="navbar-text" style="font-size: 115%; padding-left: 145px; width: 60%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis"></p>
        <form class="navbar-form navbar-right" method="get" style="padding-right: 30px" action="/auth/registration">
            <button type="submit" class="btn btn-default">Create free account</button>
        </form>
    </div>
    </div>
</nav>


<div style="width: 400px; height: 300px; position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto;">
    <c:if test="${param.error != null}">
        <div class="alert alert-danger" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            <span class="sr-only">Error:</span>
            Email or password is incorrect!
        </div>
    </c:if>
    <form method="post" action="/">
        <div class="input-group">
            <span class="input-group-addon">E</span>
            <input id="emailInput" name="email" type="text" class="form-control" placeholder="Email" aria-describedby="basic-addon1" required>
        </div>
        <p></p>
        <div class="input-group">
            <span class="input-group-addon">P</span>
            <input id="passwordInput" name="password" type="text" class="form-control" placeholder="Password" aria-describedby="basic-addon1" required>
        </div>
        <p></p>
        <div hidden class="input-group input-sm">
            <div hidden class="checkbox">
                <input hidden checked type="checkbox" id="rememberme" name="remember-me">
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />
        <div class="form-actions">
            <input type="submit" class="btn btn-block btn-primary btn-default" value="Sign in">
        </div><br>
        <button type="button" class="btn btn-danger" style="width: 100%">Can't remember my password...</button>
    </form>

</div>




</body>
</html>
