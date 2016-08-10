
$(document).ready(function () {
    loadLists();
    document.getElementById("profileButton").onclick = function(event) {
        event.preventDefault();
        document.getElementById("profileModal").style.display = "block";
        getProfile();
        //$("#signInDiv").hide();
    };

    document.getElementById("closeSpan").onclick = function() {
        onProfileClose();
    };

    window.onclick = function(event) {
        if (event.target == document.getElementById("profileModal")) {
            onProfileClose();
        }
    };
});

function loadLists() {
    var jsonLoadLists = {
        "listId": 0,
        "todoId": 0,
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null,
        "todoText": null,
        "listName": null
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
        "todoText": null,
        "listName": null
    };

    $.ajax({
        type: "POST",
        url: "/user/loadTodos",
        data: JSON.stringify(jsonCurrentListTodos),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            document.getElementById("todoResult").innerHTML = data;
            if (typeof window.showDoneTodos !== 'undefined' && window.showDoneTodos != null) {
                loadDoneTodos();
            }
            //document.getElementById("doneTodoResult").innerHTML = "";
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function loadTodos(event) {
    event.preventDefault();
    window.currentList = event.currentTarget.id.split("=")[1];
    window.showDoneTodos = null;
    var navbarText = event.currentTarget.getAttribute("name");

    var jsonTodos = {
        "listId": window.currentList,
        "todoId": 0,
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null,
        "todoText": null,
        "listName": null
    };

    $.ajax({
        type: "POST",
        url: "/user/loadTodos",
        data: JSON.stringify(jsonTodos),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            document.getElementById("navbarText").innerHTML = navbarText;
            document.getElementById("todoResult").innerHTML = data;
            document.getElementById("doneTodoResult").innerHTML = "";
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
        "todoText": null,
        "listName": null
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
        "todoText": document.getElementById("addTodoInput").value,
        "listName": null
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

function doneTodo(event) {
    event.preventDefault();
    if (typeof window.currentList === 'undefined' || window.currentList == null) {
        return;
    }

    var todo = event.currentTarget;

    var jsonDoneTodo = {
        "listId": window.currentList,
        "todoId": event.currentTarget.id.split("=")[1],
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null,
        "todoText": null,
        "listName": null
    };

    $.ajax({
        type: "POST",
        url: "/user/doneTodo",
        data: JSON.stringify(jsonDoneTodo),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data == "ok") {
                $(todo).hide();
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

    var doneTodo = event.currentTarget;

    var jsonUnDoneTodo = {
        "listId": window.currentList,
        "todoId": 0,
        "doneTodoId": event.currentTarget.id.split("=")[1],
        "shareWith": null,
        "unShareWith": null,
        "todoText": null,
        "listName": null
    };

    $.ajax({
        type: "POST",
        url: "/user/unDoneTodo",
        data: JSON.stringify(jsonUnDoneTodo),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data == "ok") {
                $(doneTodo).hide();
                loadLists();
            }
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function addTodoList() {
    if (document.getElementById("addTodoListInput").value == "") {
        return;
    }

    var jsonAddTodoList = {
        "listId": 0,
        "todoId": 0,
        "doneTodoId": 0,
        "shareWith": null,
        "unShareWith": null,
        "todoText": null,
        "listName": document.getElementById("addTodoListInput").value
    };

    $.ajax({
        type: "POST",
        url: "/user/addTodoList",
        data: JSON.stringify(jsonAddTodoList),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            document.getElementById("addTodoListInput").value = "";
            loadLists();
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

function addTodoInputEnter(event) {
    event.preventDefault();
    if (event.keyCode == 13) {
        addTodo();
    }
}

function addTodoListInputEnter(event) {
    event.preventDefault();
    if (event.keyCode == 13) {
        addTodoList();
    }
}

function showDoneTodosClick() {
    if (typeof window.showDoneTodos !== 'undefined' && window.showDoneTodos != null) {
        window.showDoneTodos = null;
        document.getElementById("doneTodoResult").innerHTML = "";
        return;
    }
    window.showDoneTodos = "ok";
    loadDoneTodos();
}

function refresh(event) {
    event.preventDefault();
    loadLists();
}

function passCheck() {
    var passLength = document.getElementById("proPassInput").value.length;
    if ( (passLength < 6) || (passLength > 20) ) {
        $("#proPassError").show();
    } else {
        $("#proPassError").hide();
    }
}

function repeatPassCheck() {
    if (document.getElementById("proPassInput").value != document.getElementById("proRepeatPassInput").value) {
        $("#proRepeatPassError").show();
    } else {
        $("#proRepeatPassError").hide();
    }
}

function onProfileClose() {
    document.getElementById("profileModal").style.display = "none";
    //$("#signInDiv").show();
    //document.getElementById("proEmailInput").value = "";
    document.getElementById("proFNameInput").value = "";
    document.getElementById("proLNameInput").value = "";
    document.getElementById("proPassInput").value = "";
    document.getElementById("proRepeatPassInput").value = "";
    //$("#regEmailError").hide();
    //$("#regFNameError").hide();
    //$("#regLNameError").hide();
    $("#proPassError").hide();
    $("#proRepeatPassError").hide();
    $("#proSuccess").hide();
}

function getProfile() {
    $.ajax({
        type: "POST",
        url: "/user/getProfile",
        data: null,
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            alert(data);
            var jsonObj = JSON.parse(data);
            alert(jsonObj + " / " + jsonObj.fName);
            document.getElementById("proEmailInput").placeholder = jsonObj.email;
            document.getElementById("proFNameInput").placeholder = jsonObj.fName;
            document.getElementById("proLNameInput").placeholder = jsonObj.lName;
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}