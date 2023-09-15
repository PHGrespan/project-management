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

function showWorkspaces(user) {
    $("#conversation").html("<thead>" +
        "<tr>" +
        "<th>Id</th>" +
        "<th>Name</th>" +
        "<th>Description</th>" +
        "<th>Creation Date</th>" +
        "<th>Update Date</th>" +
        "<th>Owner</th>" +
        "<th>Action</th>" +
        "</tr>" +
        "</thead>" +
        "<tbody id='workspaces'></tbody>");
    user.workspaces.sort(function (a, b) {
        return new Date(b.workspace.updateDate) - new Date(a.workspace.updateDate);
    });
    $("#workspaces").html("");
    user.workspaces.forEach(workspaces => {
        var rowId = workspaces.workspace.id;
        $("#workspaces").append("<tr id='" + rowId + "'>")
        $("#workspaces").append("<td><input id='editId_" + rowId + "' value='" + workspaces.workspace.id + "' readonly></td>");
        $("#workspaces").append("<td><input id='editName_" + rowId + "' value='" + workspaces.workspace.name + "'></td>");
        $("#workspaces").append("<td><input id='editDescription_" + rowId + "' value='" + workspaces.workspace.description + "'></td>");
        $("#workspaces").append("<td><input id='editCreationDate_" + rowId + "' value='" + workspaces.workspace.creationDate + "' readonly></td>");
        $("#workspaces").append("<td><input id='editUpdateDate_" + rowId + "' value='" + workspaces.workspace.updateDate + "' readonly></td>");
        $("#workspaces").append("<td><input id='editOwner_" + rowId + "' value='" + workspaces.owner + "' readonly></td>");
        $("#workspaces").append("<td><button onclick='updateWorkspace(" + rowId + ")'>Update</button>");
        $("#workspaces").append("<button onclick='deleteWorkspace(" + rowId + ")'>Delete</button>");
        $("#workspaces").append("</tr>")
    });
}