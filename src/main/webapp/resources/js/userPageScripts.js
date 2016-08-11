
$(document).ready(function () {
    loadLists();

    document.getElementById("profileButton").onclick = function(event) {
        event.preventDefault();
        $("#addTodoDiv").hide();
        document.getElementById("profileModal").style.display = "block";
        getProfile();
    };

    document.getElementById("closeSpan").onclick = function() {
        onProfileClose();
    };

    document.getElementById("deleteModalButton").onclick = function(event) {
        event.preventDefault();
        if (typeof window.currentList === 'undefined' || window.currentList == null) {
            return;
        }
        $("#addTodoDiv").hide();
        document.getElementById("deleteModal").style.display = "block";
        getDeleteInfo();
    };

    document.getElementById("delCloseSpan").onclick = function() {
        onDeleteClose();
    };

    document.getElementById("shareModalButton").onclick = function(event) {
        event.preventDefault();
        if (typeof window.currentList === 'undefined' || window.currentList == null) {
            return;
        }
        $("#addTodoDiv").hide();
        document.getElementById("shareModal").style.display = "block";
        getShareInfo();
    };

    document.getElementById("shareCloseSpan").onclick = function() {
        onShareClose();
    };

    window.onclick = function(event) {
        if (event.target == document.getElementById("profileModal")) {
            onProfileClose();
        }
        if (event.target == document.getElementById("deleteModal")) {
            onDeleteClose();
        }
        if (event.target == document.getElementById("shareModal")) {
            onShareClose();
        }
    };
});

function onProfileClose() {
    document.getElementById("profileModal").style.display = "none";
    $("#addTodoDiv").show();
    document.getElementById("proFNameInput").value = "";
    document.getElementById("proLNameInput").value = "";
    document.getElementById("proPassInput").value = "";
    document.getElementById("proRepeatPassInput").value = "";
    $("#proPassError").hide();
    $("#proRepeatPassError").hide();
    $("#proSuccess").hide();
    $("#proError").hide();
}

function onDeleteClose() {
    document.getElementById("deleteModal").style.display = "none";
    $("#addTodoDiv").show();
    document.getElementById("deleteButton").disabled = false;
    document.getElementById("delTodoListHeader").innerHTML = "";
    $("#delSuccess").hide();
    $("#delError").hide();
    document.getElementById("delInfo").innerHTML = "";
}

function onShareClose() {
    document.getElementById("shareModal").style.display = "none";
    $("#addTodoDiv").show();
    document.getElementById("shareTodoListHeader").innerHTML = "";
    document.getElementById("shareEmailInput").value = "";
    $("#shareSuccess").hide();
    $("#shareError").hide();
    $("#unShareSuccess").hide();
    $("#unShareError").hide();
    document.getElementById("sharedUsers").innerHTML = "";
}

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
    window.navbarText = event.currentTarget.getAttribute("name");

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
            document.getElementById("navbarText").innerHTML = window.navbarText;
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

function getDeleteInfo() {
    document.getElementById("delTodoListHeader").innerHTML = window.navbarText;
    var jsonTodoList = {
        "todoListId": window.currentList
    };

    $.ajax({
        type: "POST",
        url: "/user/todoListDeleteInfo",
        data: JSON.stringify(jsonTodoList),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data == "error") {
                document.getElementById("delInfo").innerHTML = "Error! Can't retrieve data.";
            } else if (data == "") {
                document.getElementById("delInfo").innerHTML = "This list isn't shared with anybody";
            } else {
                document.getElementById("delInfo").innerHTML = data;
            }
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function deleteTodoList() {
    if (typeof window.currentList === 'undefined' || window.currentList == null) {
        return;
    }

    var jsonTodoList = {
        "todoListId": window.currentList
    };

    $.ajax({
        type: "POST",
        url: "/user/deleteTodoList",
        data: JSON.stringify(jsonTodoList),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data == "ok") {
                $("#delError").hide();
                $("#delSuccess").show();
                document.getElementById("deleteButton").disabled = true;
                document.getElementById("navbarText").innerHTML = "";
                window.currentList = null;
                loadLists();
            } else if (data == "error") {
                $("#delSuccess").hide();
                document.getElementById("delErrorText").innerHTML = "Server internal error!";
                $("#delError").show();
            } else {
                $("#delSuccess").hide();
                document.getElementById("delErrorText").innerHTML = data;
                $("#delError").show();
            }
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function getShareInfo() {
    document.getElementById("shareTodoListHeader").innerHTML = window.navbarText;
    var jsonTodoList = {
        "todoListId": window.currentList
    };

    $.ajax({
        type: "POST",
        url: "/user/todoListShareInfo",
        data: JSON.stringify(jsonTodoList),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data == "error") {
                document.getElementById("sharedUsers").innerHTML = "Error! Can't retrieve data.";
            } else if (data == "") {
                document.getElementById("sharedUsers").innerHTML = "This list isn't shared with anybody";
            } else {
                document.getElementById("sharedUsers").innerHTML = data;
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

function getProfile() {
    $.ajax({
        type: "POST",
        url: "/user/getProfile",
        data: null,
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            document.getElementById("proEmailInput").placeholder = data.email;
            document.getElementById("proFNameInput").placeholder = data.fName;
            document.getElementById("proLNameInput").placeholder = data.lName;
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function updateProfile() {
    if ( ( (document.getElementById("proFNameInput").value == "") &&
        (document.getElementById("proLNameInput").value == "") &&
        (document.getElementById("proPassInput").value == "") ) ||
         ($("#proPassError").is(':visible')) || ($("#proRepeatPassError").is(':visible')) ) {
        return;
    }

    var profile = {
        "email": null,
        "fName": document.getElementById("proFNameInput").value,
        "lName": document.getElementById("proLNameInput").value,
        "password": document.getElementById("proPassInput").value
    };

    $.ajax({
        type: "POST",
        url: "/user/updateProfile",
        data: JSON.stringify(profile),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data != "ok") {
                $("#proSuccess").hide();
                document.getElementById("proErrorText").innerHTML = data;
                $("#proError").show();
            } else {
                $("#proError").hide();
                $("#proSuccess").show();
            }
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

