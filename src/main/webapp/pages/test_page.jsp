<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>


<head>
    <link rel="shortcut icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link rel="icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <title>Title</title>
</head>
<body>
asdgasdgadgagfd ывпфывдлфоывфыовжf<br>
<br>
<a href="ololo">ololo</a><br>
<a href="user/ololo">user/ololo</a><br>
<a href="user/json">json test</a><br>
<a href="admin">adminka</a><br>
<a href="rest">rest adminka</a><br>
<a href="auth/access_denied">access dedied</a><br>
<br>
<div>
    <sec:authorize access="isAuthenticated()">
        <sec:authentication var="username" property="principal.username" />
        <label>Long time no seeing, ${username}!</label>
    </sec:authorize>
</div>
<div>
    <sec:authorize access="hasRole('ADMIN')">
        <label>hello, ADMIN!</label>
    </sec:authorize>
</div>
<div>
    <c:if test="${confirm != null}">
        <c:out value="${confirm}"/>
    </c:if>
</div>
<sec:authorize var="loggedIn" access="isAuthenticated()" />
<c:choose>
    <c:when test="${loggedIn}">
        <form role="form" method="get" action="/auth/logout">
            <input type="submit" value="Sign out"  />
        </form>
    </c:when>
    <c:otherwise>
        <form role="form" method="get" action="/auth/login">
            <input type="submit" value="Sign in"  />
        </form>
        <form role="form" method="get" action="/auth/registration">
            <input type="submit" value="Registration"  />
        </form>
    </c:otherwise>
</c:choose>
</body>
</html>
