<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>


<head>
    <link rel="shortcut icon" href="/resources/favicon.ico" type="image\x-icon" />
    <link rel="icon" href="/resources/favicon.ico" type="image\x-icon" />
    <title>Title</title>
</head>
<body>
asdgasdgadgagfd ывпфывдлфоывфыовжf<br>
<br>
<a href="ololo">ololo</a><br>
<a href="user/ololo">user/ololo</a><br>
<a href="admin">adminka</a><br>
<a href="rest">rest adminka</a><br>
<a href="access_denied">access dedied</a><br>
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
<sec:authorize var="loggedIn" access="isAuthenticated()" />
<c:choose>
    <c:when test="${loggedIn}">
        <a href="logout">logout</a><br>
        <form role="form" method="post" action="/logout">
            <input type="submit" value="logout"  />
        </form>
    </c:when>
    <c:otherwise>
        <a href="login">login</a><br>
    </c:otherwise>
</c:choose>
</body>
</html>
