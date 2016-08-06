
function oneMore(event) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var headers = {};
    headers[csrfHeader] = csrfToken;

    var search = {
        "listId": 125,
        "todoId": event.target.id
    };

    window.testVar = "ololololo test";

    $.ajax({
        type: "POST",
        url: "/user/test",
        //data: JSON.stringify({id: 20, name: "фыва ололошка"}),
        //dataType: 'json',
        data: JSON.stringify(search),
        contentType: 'application/json',
        headers: headers,
        success: function (data) {
//                $("#result").html(data);
            document.getElementById("result").innerHTML = data;
            innerTest();
        },
        error: function (e) {
            alert("fail");
        }
    });
}

function loadLists() {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var headers = {};
    headers[csrfHeader] = csrfToken;

    var search = {
        "listId": 0,
        "todoId": 0,
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null,
        "date": new Date(),
        "strDate": new Date().toJSON()
    };

    $.ajax({
        type: "POST",
        url: "/user/loadLists",
        data: JSON.stringify(search),
        contentType: 'application/json',
        headers: headers,
        success: function (data) {
            document.getElementById("listResult").innerHTML = data;
            if (typeof window.currentList !== 'undefined' && window.currentList != null) {
                loadTodosCurrent();
            }
        },
        error: function (e) {
            alert("fail");
        }
    });
}

function loadTodosCurrent() {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var headers = {};
    headers[csrfHeader] = csrfToken;

    var search = {
        "listId": String(window.currentList).split("=")[1],
        "todoId": 0,
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null
    };

    $.ajax({
        type: "POST",
        url: "/user/loadTodos",
        data: JSON.stringify(search),
        contentType: 'application/json',
        headers: headers,
        success: function (data) {
            document.getElementById("todoResult").innerHTML = data;
        },
        error: function (e) {
            alert("fail");
        }
    });
}

function loadTodos(event) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var headers = {};
    headers[csrfHeader] = csrfToken;

    window.currentList = event.target.id;

    var search = {
        "listId": String(event.target.id).split("=")[1],
        "todoId": 0,
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null,
        "date": new Date(),
        "strDate": new Date().toJSON()
    };

    $.ajax({
        type: "POST",
        url: "/user/loadTodos",
        data: JSON.stringify(search),
        contentType: 'application/json',
        headers: headers,
        success: function (data) {
            document.getElementById("todoResult").innerHTML = data;
        },
        error: function (jqXHR, exception) {
            var msg = '';
            if (jqXHR.status === 0) {
                msg = 'Not connect.\n Verify Network.';
            } else if (jqXHR.status == 404) {
                msg = 'Requested page not found. [404]';
            } else if (jqXHR.status == 500) {
                msg = 'Internal Server Error [500].';
            } else if (exception === 'parsererror') {
                msg = 'Requested JSON parse failed.';
            } else if (exception === 'timeout') {
                msg = 'Time out error.';
            } else if (exception === 'abort') {
                msg = 'Ajax request aborted.';
            } else {
                msg = 'Uncaught Error.\n' + jqXHR.responseText;
            }
            alert(msg + "/ status: " + jqXHR.status + "/ exception: " + exception);
        }
    });
}