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
    <script type="text/javascript" src="/resources/js/oneMore.js"></script>
    <script type="text/javascript" src="/resources/js/innerTest.js"></script>
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

<button type="button" onclick="oneMore(event)" id="but111">Request data 111</button>
<button type="button" onclick="oneMore(event)" id="but222">Request data 222</button>

<div id="result" style="alignment: left; background-color: #31708f;"></div>

<input type="hidden" id="csrftoken_" name="${_csrf.parameterName}" value="${_csrf.token}" />


    <script type="text/javascript" language="javascript">
    jQuery(document).ready(
            function($) {

                $("#btn-save").click(function(event) {

                    var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
                    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
                    var csrfToken = $("meta[name='_csrf']").attr("content");
                    //var token = $("input[name='_csrf']").val();

                    //var data = {}
//                    data[csrfParameter] = csrfToken;
                    //data["name"] = "amelolo";

                    var headers = {};
                    headers[csrfHeader] = csrfToken;
                    headers["contentType"] = "application/json";

                    $("#btn-save").prop("disabled", true);

                    var csrftoken = $("#csrftoken_").clone();

//                    $.ajaxPrefilter(function(options, originalOptions, jqXHR) {
//                        var token = $("meta[name='_csrf']").attr("content");
//                        var header = $("meta[name='_csrf_header']").attr("content");
//                        jqXHR.setRequestHeader(header, token);
//                    });

                    $.ajax({
                        data: {},
                        headers: {},
                        timeout: 10000,
                        type: "POST",
                        url: "/user/test",
                        success: function (data) {
                            $("#result").html(data);
                        },
                        error: function (e) {
                            alert(e);
                        }
                    });



//                    $('<form target="_blank" action="/user/test" method="post"></form>')
//                            .append(data)
//                            .append(csrftoken)
//                            .appendTo('body')
//                            .submit()
//                            .remove();
//
                });

            });
</script>

<script type="text/javascript" language="javascript">
    function loadDoc() {
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        var search = {
            "name" : "bhanu",
            "lName" :"prasad"
        };
        var data = {}
        data["name"] = "amelolo";
        data["lara"] = "lolo";
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (xhttp.readyState == 4 && xhttp.status == 200) {
                document.getElementById("result").innerHTML = xhttp.responseText;
            }
        };
        xhttp.open("POST", "/user/test", true);
//        xhttp.setRequestHeader(csrfHeader, csrfToken);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.dataType("json");
        xhttp.data(JSON.stringify(data));
        xhttp.setData();
        xhttp.send();
    }
</script>

<script type="text/javascript" language="javascript">
    function searchText() {
        var search = {
            "pName": "bhanu",
            "lName": "prasad"
        }
        $.ajax({
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            url: "/user/test",
            data: JSON.stringify(search), // Note it is important
            success: function (result) {
                document.getElementById("result").innerHTML = result;
            }
        });
    }
</script>


<%--<script type="text/javascript" language="javascript">--%>
    <%--function oneMore(event) {--%>
        <%--var csrfHeader = $("meta[name='_csrf_header']").attr("content");--%>
        <%--var csrfToken = $("meta[name='_csrf']").attr("content");--%>

        <%--var headers = {};--%>
        <%--headers[csrfHeader] = csrfToken;--%>

        <%--var search = {--%>
            <%--"id": 125,--%>
            <%--"name": event.target.id--%>
        <%--};--%>

        <%--$.ajax({--%>
            <%--type: "POST",--%>
            <%--url: "/user/test",--%>
            <%--//data: JSON.stringify({id: 20, name: "фыва ололошка"}),--%>
            <%--//dataType: 'json',--%>
            <%--data: JSON.stringify(search),--%>
            <%--contentType: 'application/json',--%>
            <%--headers: headers,--%>
            <%--success: function (data) {--%>
<%--//                $("#result").html(data);--%>
                <%--document.getElementById("result").innerHTML = data;--%>
                <%--innerTest();--%>
            <%--},--%>
            <%--error: function (e) {--%>
                <%--alert("fail");--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>
<%--</script>--%>

<%--<script type="text/javascript" language="javascript">--%>
    <%--function innerTest() {--%>
        <%--alert("innerTest = ok")--%>
    <%--}--%>
<%--</script>--%>

</body>
</html>


<%--//                    $.ajax({--%>
<%--//                        type: "POST",--%>
<%--//                        X_CSRF_TOKEN: token,--%>
<%--//                        contentType: "application/json",--%>
<%--//                        url: "/user/test",--%>
<%--////                        headers: headers,--%>
<%--//                        data: JSON.stringify(data),--%>
<%--//                        dataType: 'json',--%>
<%--//                        timeout: 600000,--%>
<%--//                        type:"POST",--%>
<%--//                        beforeSend: function (request)--%>
<%--//                        {--%>
<%--//                            request.setRequestHeader(csrfHeader, csrfToken);--%>
<%--//                        },--%>
<%--//                        url: "/user/test",--%>
<%--//                        data: "json=" + escape(JSON.stringify(data)),--%>
<%--//                        processData: false,--%>
<%--//                        success: function (data) {--%>
<%--//                            $("#result").html(data);--%>
<%--//                        },--%>
<%--//                        error: function (e) {--%>
<%--//                            alert(e);--%>
<%--//                        }--%>
<%--//                    });--%>