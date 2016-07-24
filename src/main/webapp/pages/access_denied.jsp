<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="/resources/favicon.ico" type="image\x-icon" />
    <link rel="icon" href="/resources/favicon.ico" type="image\x-icon" />
    <title>Denied!</title>
</head>
<body>
<c:choose>
    <c:when test="${param.user eq 'anonymousUser'}">
        what are you doing here?.. O_o
    </c:when>
    <c:otherwise>
        ${user}, fuck off!
    </c:otherwise>
</c:choose>

</body>
</html>
