function searchModule() {
    window.location.replace("/modules/" + document.getElementById("search").value + "/info");
}

function project() {
    document.getElementById("maxMember").style.visibility = "hidden";
    document.getElementById("maxMember").value = "2";
    document.getElementById("groupName").style.visibility = "hidden";
    document.getElementById("groupName").value = "Projekt";

}

function group() {
    console.log("OK");
    document.getElementById("maxMember").style.visibility = "visible";
    document.getElementById("groupName").style.visibility = "visible";
}