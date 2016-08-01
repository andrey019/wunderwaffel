<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: andrey
  Date: 01.08.16
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link rel="icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="/resources/js/jquery-3.1.0.min.js"></script>
    <%--<link rel="shortcut icon" href="<c:url value="/resources/favicon.png"/>" type="image\png" />--%>
    <%--<link rel="icon" href="<c:url value="/resources/favicon.png"/>" type="image\png" />--%>
    <%--<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">--%>
    <sec:csrfMetaTags />
    <title>json test</title>
</head>
<body>
<div class="list-group">
    <button type="button" class="list-group-item" name="but1">Cras justo odio</button>
    <button type="button" class="list-group-item">Dapibus ac facilisis in</button>
    <button type="button" class="list-group-item">Morbi leo risus</button>
    <button type="button" class="list-group-item">Porta ac consectetur ac</button>
    <button type="button" class="list-group-item">Vestibulum at eros</button>
</div>

<button type="button" value="ololo" name="btn-save" title="btn-save" id="btn-save">sfdsd</button>

<div id="result" style="alignment: left; background-color: #31708f;"></div>



    <script type="text/javascript" language="javascript">
    jQuery(document).ready(
            function($) {

                $("#btn-save").click(function(event) {

                    var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
                    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
                    var csrfToken = $("meta[name='_csrf']").attr("content");
                    var token = $("input[name='_csrf']").val();

                    var data = {}
//                    data[csrfParameter] = csrfToken;
                    data["name"] = "amelolo";

                    var headers = {}
                    headers[csrfHeader] = csrfToken;
                    headers["contentType"] = "application/json";

                    $("#btn-save").prop("disabled", true);

                    $.ajax({
                        type: "POST",
                        X_CSRF_TOKEN: token,
                        contentType: "application/json",
                        url: "/user/test",
//                        headers: headers,
                        data: JSON.stringify(data),
                        dataType: 'json',
                        timeout: 600000,
                        success: function (data) {
                            $("#result").html(data);
                        },
                        error: function (e) {
                            alert(e);
                        }
                    });


                });

            });
</script>
</body>
</html>
