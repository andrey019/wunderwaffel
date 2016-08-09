
$(document).ready(function () {
    document.getElementById("registrationButton").onclick = function(event) {
        event.preventDefault();
        document.getElementById("registrationModal").style.display = "block";
        $("#signInDiv").hide();
    };

    document.getElementById("closeSpan").onclick = function() {
        document.getElementById("registrationModal").style.display = "none";
        $("#signInDiv").show();
    };

    window.onclick = function(event) {
        if (event.target == document.getElementById("registrationModal")) {
            document.getElementById("registrationModal").style.display = "none";
            $("#signInDiv").show();
        }
    };
});


function emailCheck() {
    var jsonEmailCheck = {
        "email": document.getElementById("regEmailInput").value,
        "password": null,
        "fName": null,
        "lName": null
    };

    $.ajax({
        type: "POST",
        url: "/auth/emailCheck",
        data: JSON.stringify(jsonEmailCheck),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data != "ok") {
                document.getElementById("regEmailErrorText").innerHTML = data;
                $("#regEmailError").show();
            } else {
                $("#regEmailError").hide();
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