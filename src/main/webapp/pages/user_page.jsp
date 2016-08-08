<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
            <a class="navbar-brand" href="#">WunderWaffel</a>
        </div>

        <p id="navbarText" class="navbar-text" style="padding-left: 180px; width: 60%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">Signed in as Mark Otto Signed in as Mark Otto Signed in as Mark Otto Signed in as Mark Otto Signed in as Mark Otto Signed in as Mark Otto Signed in as Mark Otto Signed in as Mark Otto</p>
        <form class="navbar-form navbar-right">
            <button type="submit" class="btn btn-default">Profile</button>
            <button type="submit" class="btn btn-default">Sign Out</button>
        </form>
    </div>
    </div>
</nav>

<div style="width: 100%; overflow: hidden; padding-top: 70px">
    <div style="width: 315px; float: left; position: fixed; bottom: 150px; height: auto; top: 70px; overflow-y: auto;">
        <div style="width: 290px; float: left; margin-left: 5px">
            <div id="listResult" class="list-group">

            </div>
        </div>
    </div>

    <div style="margin-left: 340px; width: auto">
        <div class="row" style="width: 100%">
            <div class="col-lg-6" style="width: 100%">
                <div class="input-group" style="width: 100%">
                    <input id="addTodoInput" type="text" onkeyup="todoInputEnter(event)" class="form-control" placeholder="Add new TODO...">
                    <span class="input-group-btn">
                        <button id="addTodoButton" onclick="addTodo()" class="btn btn-default" type="button">+</button>
                    </span>
                </div>
            </div>
        </div>
        <br><br>
        <div style="width: 100%">
            <div id="todoResult" class="list-group" style="padding-right: 30px">

            </div>
        </div>
        <button id="showDoneTodosButton" onclick="showDoneTodosClick()" class="btn btn-default" type="button">Show done todos...</button>
        <br><br>
        <div style="width: 100%">
            <div id="doneTodoResult" class="list-group" style="padding-right: 30px">

            </div>
        </div>
        <br><br>
    </div>


    <div style="position: fixed; bottom: 20px; width: 320px; margin-left: 5px">
        <div class="row" style="width: 100%">
            <div class="col-lg-6" style="width: 100%">
                <div class="input-group" style="width: 100%">
                    <input type="text" class="form-control" placeholder="Add new LIST...">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button">+</button>
                    </span>
                </div>
            </div>
        </div>
        <br>
        <div class="btn-group btn-group-justified" role="group" aria-label="..." style="padding-right: 30px">
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
