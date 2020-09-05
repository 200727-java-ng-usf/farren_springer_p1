function returnAMessage() {
    var selection = document.querySelector("#reimbsbytypeorstatus").value;
    var message;
    if (selection == "type") {
        message = "type";
    } else if (selection == "status") {
        message = "status";
    }

    document.querySelector("#financemanager-container").innerHTML = message;
}
