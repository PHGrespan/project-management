const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);

    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch("http://localhost:8080/users/1/workspaces", requestOptions)
        .then(response => response.json())
        .then(json => showGreeting(json))
        .catch(error => console.log('error', error));

    stompClient.subscribe('/topic/user/' + userId + '/workspace.list', (user) => {
        showGreeting(JSON.parse(user.body));
    });

    console.log('SUBSCRIBED: /topic/user/' + userId + '/workspace.list');
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#workspaces").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function insertWorkspace() {
    stompClient.publish({
        destination: "/app/user/" + userId + "/workspace.add",
        body: JSON.stringify({'name': $("#name").val(), 'description': $("#description").val()})
    });
}

function updateWorkspace(id) {
    var name = document.getElementById("editName_" + id).value;
    var description = document.getElementById("editDescription_" + id).value;

    stompClient.publish({
        destination: "/app/user/" + userId + "/workspace.update/" + id,
        body: JSON.stringify({'name': name, 'description': description})
    });
}

function deleteWorkspace(id) {

    stompClient.publish({
        destination: "/app/user/" + userId + "/workspace.delete/" + id
    });
}

function showGreeting(user) {
    $("#workspaces").html("");
    user.workspaces.sort(function(a,b){
        return new Date(b.workspace.updateDate) - new Date(a.workspace.updateDate);
    });
    document.getElementById("workspaces").innerHTML = "";
    user.workspaces.forEach(workspaces => {
        var rowId = workspaces.workspace.id;
        $("#workspaces").append("<tr id='" + rowId + "'>")
        $("#workspaces").append("<td><input id='editId_" + rowId + "' value='" + workspaces.workspace.id + "' readonly></td>");
        $("#workspaces").append("<td><input id='editName_" + rowId + "' value='" + workspaces.workspace.name + "'></td>");
        $("#workspaces").append("<td><input id='editDescription_" + rowId + "' value='" + workspaces.workspace.description + "'></td>");
        $("#workspaces").append("<td><input id='editCreationDate_" + rowId + "' value='" + workspaces.workspace.creationDate + "' readonly></td>");
        $("#workspaces").append("<td><input id='editUpdateDate_" + rowId + "' value='" + workspaces.workspace.updateDate + "' readonly></td>");
        $("#workspaces").append("<td><input id='editOwner_" + rowId + "' value='" + workspaces.owner + "' readonly></td>");
        $("#workspaces").append("<td><button onclick='updateWorkspace(\"" + rowId + "\")'>Update</button><button onclick='deleteWorkspace(\"" + rowId + "\")'>Delete</button></td>");
        $("#workspaces").append("</tr>")
    });
}

const userId = 1;
$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#insert").click(() => insertWorkspace());
    $(".update-btn").click(() => updateWorkspace());
});