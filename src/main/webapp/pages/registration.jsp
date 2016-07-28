<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andrey
  Date: 27.07.16
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="/resources/favicon.ico" type="image\x-icon" />
    <link rel="icon" href="/resources/favicon.ico" type="image\x-icon" />
    <title>registration</title>
</head>
<body>
<form action="/auth/registration" method="post" class="form-horizontal">
    <c:if test="${param.error != null}">
        <div class="alert alert-danger">
            <p>This email is already in use</p>
        </div>
    </c:if>

    <div class="input-group input-sm">
        <label class="input-group-addon" for="email"><i class="fa fa-user"></i></label>
        <input type="text" class="form-control" id="email" name="email" placeholder="Enter email" required>
    </div>
    <div class="input-group input-sm">
        <label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label>
        <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <div class="form-actions">
        <input type="submit" class="btn btn-block btn-primary btn-default" value="Sign up!">
    </div>
</form>
</body>
</html>
