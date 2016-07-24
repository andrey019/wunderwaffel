<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Denied!</title>
</head>
<body>
<c:choose>
    <c:when test="${param.user != null}">
        ${user}, fuck off!
    </c:when>
    <c:when test="${param.user == anonymousUser}">
        what are you doing here?.. O_o
    </c:when>
</c:choose>

</body>
</html>
