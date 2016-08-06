<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link rel="icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="/resources/js/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="/resources/js/oneMore.js"></script>
    <sec:csrfMetaTags />
    <meta http-equiv="refresh" content="${pageContext.session.maxInactiveInterval}">
    <title>WunderWaffel</title>
</head>
<body>

<div style="width: 99%; overflow: hidden;">
    <div style="width: 315px; float: left; background-color: #87608f; position: fixed; max-height: 80%; overflow-y: auto;">
        <div style="width: 290px; float: left; margin-left: 5px">
            <div id="listResult" class="list-group">

            </div>
        </div>
    </div>

    <div style="margin-left: 320px; background-color: #54808f">
        <div class="row" style="width: 99%">
            <div class="col-lg-6" style="width: 99%">
                <div class="input-group" style="width: 99%">
                    <input id="addTodoInput" type="text" onkeyup="todoInputEnter(event)" class="form-control" placeholder="Add new TODO...">
                    <span class="input-group-btn">
                        <button id="addTodoButton" onclick="addTodo()" class="btn btn-default" type="button">+</button>
                    </span>
                </div>
            </div>
        </div>
        <br><br>
        <div id="todos" style="width: 95%">
            <div id="todoResult" class="list-group">

            </div>
        </div>
        <br><br>
    </div>


    <div style="position: fixed; bottom: 3%; width: 290px; margin-left: 5px">
        <div class="row" style="width: 112%">
            <div class="col-lg-6" style="width: 99%">
                <div class="input-group" style="width: 99%">
                    <input type="text" class="form-control" placeholder="Add new LIST...">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button">+</button>
                    </span>
                </div>
            </div>
        </div>
        <br>
        <div class="btn-group btn-group-justified" role="group" aria-label="...">
            <a type="button" class="btn btn-default" onclick="loadLists()">Delete</a>
            <a type="button" class="btn btn-default">Share</a>
        </div>
    </div>

</div>

<script type="text/javascript">
    $(document).ready(function () {
        loadLists();
    });
</script>

</body>
</html>
