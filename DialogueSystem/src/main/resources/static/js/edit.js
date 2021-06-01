window.onload = enableAdding;

function enableAdding() {
    let delElem = document.getElementById("del");
    delElem.style.visibility = "hidden";
    delElem.style.display = "none";

    let addElem = document.getElementById("addNew");
    addElem.style.visibility = "visible";
    addElem.style.display = "flex";
    addElem.style.justifyContent = "center";
}

function enableDeleting() {
    let addElem = document.getElementById("addNew");
    addElem.style.visibility = "hidden";
    addElem.style.display = "none";

    let delElem = document.getElementById("del");
    delElem.style.visibility = "visible";
    delElem.style.display = "flex";
    delElem.style.justifyContent = "center";
}