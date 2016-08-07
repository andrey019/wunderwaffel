
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
    var search = {
        "listId": 0,
        "todoId": 0,
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null,
        "todoText": null
    };

    $.ajax({
        type: "POST",
        url: "/user/loadLists",
        data: JSON.stringify(search),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            document.getElementById("listResult").innerHTML = data;
            loadCurrentListTodos();
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function loadCurrentListTodos() {
    if (typeof window.currentList === 'undefined' || window.currentList == null) {
        return;
    }

    var search = {
        "listId": window.currentList,
        "todoId": 0,
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null,
        "todoText": null
    };

    $.ajax({
        type: "POST",
        url: "/user/loadTodos",
        data: JSON.stringify(search),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            document.getElementById("todoResult").innerHTML = data;
            document.getElementById("doneTodoResult").innerHTML = "";
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function loadTodos(event) {
    window.currentList = String(event.target.id).split("=")[1];

    var search = {
        "listId": window.currentList,
        "todoId": 0,
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null,
        "todoText": null
    };

    $.ajax({
        type: "POST",
        url: "/user/loadTodos",
        data: JSON.stringify(search),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            document.getElementById("todoResult").innerHTML = data;
            document.getElementById("doneTodoResult").innerHTML = "";
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function addTodo() {
    if (typeof window.currentList === 'undefined' || window.currentList == null
        || document.getElementById("addTodoInput").value == "") {
        return;
    }

    var search = {
        "listId": window.currentList,
        "todoId": 0,
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null,
        "todoText": document.getElementById("addTodoInput").value
    };

    $.ajax({
        type: "POST",
        url: "/user/addTodo",
        data: JSON.stringify(search),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            document.getElementById("addTodoInput").value = "";
            loadLists();
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function loadDoneTodos() {
    if (typeof window.currentList === 'undefined' || window.currentList == null) {
        return;
    }

    var search = {
        "listId": window.currentList,
        "todoId": 0,
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null,
        "todoText": null
    };

    $.ajax({
        type: "POST",
        url: "/user/loadDoneTodos",
        data: JSON.stringify(search),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            document.getElementById("doneTodoResult").innerHTML = data;
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function getCSRFHeader() {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[csrfHeader] = csrfToken;
    return headers;
}

function jsonErrorHandler(jqXHR, exception) {
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

function todoInputEnter(event) {
    event.preventDefault();
    if (event.keyCode == 13) {
        document.getElementById("addTodoButton").click();
    }
}