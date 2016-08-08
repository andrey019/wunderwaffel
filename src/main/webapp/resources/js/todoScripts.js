
function loadLists() {
    var jsonLoadLists = {
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
        data: JSON.stringify(jsonLoadLists),
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

    var jsonCurrentListTodos = {
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
        data: JSON.stringify(jsonCurrentListTodos),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            document.getElementById("todoResult").innerHTML = data;
            //document.getElementById("doneTodoResult").innerHTML = "";
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function loadTodos(event) {
    event.preventDefault();
    window.currentList = String(event.target.id).split("=")[1];

    var jsonTodos = {
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
        data: JSON.stringify(jsonTodos),
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

    var jsonAddTodo = {
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
        data: JSON.stringify(jsonAddTodo),
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

    var jsonDoneTodos = {
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
        data: JSON.stringify(jsonDoneTodos),
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

function doneTodo(event) {
    event.preventDefault();
    if (typeof window.currentList === 'undefined' || window.currentList == null) {
        return;
    }

    var jsonDoneTodo = {
        "listId": window.currentList,
        "todoId": String(event.target.id).split("=")[1],
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null,
        "todoText": null
    };

    $.ajax({
        type: "POST",
        url: "/user/doneTodo",
        data: JSON.stringify(jsonDoneTodo),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data == "ok") {
                loadLists();
            }
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function unDoneTodo(event) {
    event.preventDefault();
    if (typeof window.currentList === 'undefined' || window.currentList == null) {
        return;
    }

    var jsonUnDoneTodo = {
        "listId": window.currentList,
        "todoId": 0,
        "doneTodoId": String(event.target.id).split("=")[1],
        "shareWith": null,
        "unShareWith": null,
        "todoText": null
    };

    $.ajax({
        type: "POST",
        url: "/user/unDoneTodo",
        data: JSON.stringify(jsonUnDoneTodo),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data == "ok") {
                loadLists();
                loadDoneTodos();
            }
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
        addTodo();
    }
}