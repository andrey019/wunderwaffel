
$(document).ready(function () {
    document.getElementById("registrationButton").onclick = function(event) {
        event.preventDefault();
        document.getElementById("registrationModal").style.display = "block";
        $("#signInDiv").hide();
    };

    document.getElementById("closeSpan").onclick = onRegFormClose();

    window.onclick = function(event) {
        if (event.target == document.getElementById("registrationModal")) {
            onRegFormClose();
        }
    };
});

function onRegFormClose() {
    document.getElementById("registrationModal").style.display = "none";
    $("#signInDiv").show();
    document.getElementById("regEmailInput").value = "";
    document.getElementById("regFNameInput").value = "";
    document.getElementById("regLNameInput").value = "";
    document.getElementById("regPassInput").value = "";
    document.getElementById("regRepeatPassInput").value = "";
    $("#regEmailError").hide();
    $("#regFNameError").hide();
    $("#regLNameError").hide();
    $("#regPassError").hide();
    $("#regRepeatPassError").hide();
}

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