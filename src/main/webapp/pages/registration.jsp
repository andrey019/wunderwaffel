<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link rel="icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <title>registration</title>
    <meta http-equiv="refresh" content="${pageContext.session.maxInactiveInterval}">
</head>
<body>
<form action="/auth/registration" method="post" class="form-horizontal">
    <c:if test="${error != null}">
        <div class="alert alert-danger">
            <p><c:out value="${error}"/></p>
        </div>
    </c:if>

    <div class="input-group input-sm">
        <label class="input-group-addon" for="email"><i class="fa fa-user"></i></label>
        <input type="text" class="form-control" id="email" name="email" placeholder="Email" required>
    </div>
    <div class="input-group input-sm">
        <label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label>
        <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <div class="form-actions">
        <input type="submit" class="btn btn-block btn-primary btn-default" value="Sign up!">
    </div>
</form>
</body>
</html>
