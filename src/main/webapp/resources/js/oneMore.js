
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
        "unShareWith": null
    };

    $.ajax({
        type: "POST",
        url: "/user/loadLists",
        data: JSON.stringify(search),
        contentType: 'application/json',
        headers: headers,
        success: function (data) {
            document.getElementById("listResult").innerHTML = data;
        },
        error: function (e) {
            alert("fail");
        }
    });
}