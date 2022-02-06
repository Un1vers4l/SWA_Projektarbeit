document.getElementById("submitButton").onclick = function () {
    validate(document.getElementById("login").value, document.getElementById("password").value)
};

function validate(user, password) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/login", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("user=" + user + "&password=" + password);
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            localStorage.setItem("token", this.responseText);
            document.cookie = "token=" + this.responseText;
            window.location.replace("/index");
        }
    };

}

