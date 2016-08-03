
function loadTodos(event) {

    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var headers = {};
    headers[csrfHeader] = csrfToken;

    var info = {
        "listId": event.target.id,
    };

    $.ajax({
        type: "POST",
        url: "/user/test",
        data: JSON.stringify(info),
        //dataType: 'json',
        //data: JSON.stringify(search),
        contentType: 'application/json',
        headers: headers,
        success: function (data) {
//                $("#result").html(data);
            document.getElementById("result").innerHTML = data;
        },
        error: function (e) {
            alert("fail");
        }
    });
}