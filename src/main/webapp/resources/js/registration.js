
$(document).ready(function () {
    document.getElementById("registrationButton").onclick = function(event) {
        event.preventDefault();
        document.getElementById("registrationModal").style.display = "block";
        $("#signInDiv").hide();
    };

    document.getElementById("closeSpan").onclick = function() {
        onRegFormClose();
    };

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
    $("#regSuccess").hide();
}

function emailCheck() {
    var email = document.getElementById("regEmailInput").value;
    if (email == "") {
        document.getElementById("regEmailErrorText").innerHTML = "Enter your email!";
        $("#regEmailError").show();
        return;
    } else {
        $("#regEmailError").hide();
    }

    var jsonEmailCheck = {
        "email": email,
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

function fNameCheck() {
    if (document.getElementById("regFNameInput").value == "") {
        $("#regFNameError").show();
    } else {
        $("#regFNameError").hide();
    }
}

function lNameCheck() {
    if (document.getElementById("regLNameInput").value == "") {
        $("#regLNameError").show();
    } else {
        $("#regLNameError").hide();
    }
}

function passCheck() {
    var passLength = document.getElementById("regPassInput").value.length;
    if ( (passLength < 6) || (passLength > 20) ) {
        $("#regPassError").show();
    } else {
        $("#regPassError").hide();
    }
}

function repeatPassCheck() {
    if (document.getElementById("regPassInput").value != document.getElementById("regRepeatPassInput").value) {
        $("#regRepeatPassError").show();
    } else {
        $("#regRepeatPassError").hide();
    }
}

function registration() {
    if ($("#regEmailError").is(':visible') || $("#regFNameError").is(':visible') ||
        $("#regLNameError").is(':visible') || $("#regPassError").is(':visible') ||
        $("#regRepeatPassError").is(':visible') ||
        (document.getElementById("regEmailInput").value == "") ||
        (document.getElementById("regFNameInput").value == "") ||
        (document.getElementById("regLNameInput").value == "") ||
        (document.getElementById("regPassInput").value == "") ||
        (document.getElementById("regRepeatPassInput").value == "") ) {
        alert("fuck you!");
        return;
    } else {
        alert("ok!");
        return;
    }

    var jsonRegistration = {
        "email": document.getElementById("regEmailInput").value,
        "password": document.getElementById("regPassInput").value,
        "fName": document.getElementById("regFNameInput").value,
        "lName": document.getElementById("regLNameInput").value
    };

    $.ajax({
        type: "POST",
        url: "/auth/registration",
        data: JSON.stringify(jsonRegistration),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data != "ok") {
                $("#regSuccess").hide();
                document.getElementById("regErrorText").innerHTML = data;
                $("#regError").show();
            } else {
                $("#regError").hide();
                $("#regSuccess").show();
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