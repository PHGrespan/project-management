function insertProject() {
    stompClient.publish({
        destination: "/app/workspace/" + $("#projectIdWorkspace").val() + "/project.add",
        body: JSON.stringify({
            'name': $("#projectName").val(),
            'description': $("#projectDescription").val(),
            'idWorkspace': $("#projectIdWorkspace").val(),
            'workspacePosition': $("#projectWorkspacePosition").val()
        })
    });
}

function updateProject(id, workspaceId) {
    var name = document.getElementById("projectName_" + id).value;
    var description = document.getElementById("projectDescription_" + id).value;
    var newWorkspaceId = document.getElementById("projectWorkspaceId_" + id).value;
    var workspacePosition = document.getElementById("projectWorkspacePosition_" + id).value;

    stompClient.publish({
        destination: "/app/workspace/" + workspaceId + "/" + newWorkspaceId + "/project.update/" + id,
        body: JSON.stringify({
            'name': name,
            'description': description,
            'idWorkspace': newWorkspaceId,
            'workspacePosition': workspacePosition
        })
    });
}

function deleteProject(id, workspaceId) {
    stompClient.publish({
        destination: "/app/workspace/" + workspaceId + "/project.delete/" + id
    });
}

function showProjects(workspaces) {
    if (!Array.isArray(workspaces)) {
        // transform to array
        workspaces = [workspaces];
    }

    workspaces.forEach(workspace => {
        var workspaceId = workspace.id;

        // clear projects
        if (workspace.projects.length === 0) {
            $("#theadProjects" + workspaceId).remove();
            $("#projects" + workspaceId).remove();
        } else {
            if ($("#theadProjects" + workspaceId).length <= 0) {
                $("#conversation").append("<thead id='theadProjects" + workspaceId + "'>" +
                    "<tr>" +
                    "<th>Workspace Id</th>" +
                    "<th>Id</th>" +
                    "<th>Name</th>" +
                    "<th>Description</th>" +
                    "<th>Position</th>" +
                    "<th>Action</th>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody id='projects" + workspaceId + "'></tbody>");
            }

            $("#projects" + workspaceId).html("");

            workspace.projects.sort(function (a, b) {
                return a.workspacePosition - b.workspacePosition;
            });

            workspace.projects.forEach(project => {
                var rowId = project.id;
                $("#projects" + workspaceId).append("<tr id='" + rowId + "'>")
                $("#projects" + workspaceId).append("<td><input id='projectWorkspaceId_" + rowId + "' value='" + workspaceId + "'></td>");
                $("#projects" + workspaceId).append("<td><input id='projectId_" + rowId + "' value='" + project.id + "' readonly></td>");
                $("#projects" + workspaceId).append("<td><input id='projectName_" + rowId + "' value='" + project.name + "'></td>");
                $("#projects" + workspaceId).append("<td><input id='projectDescription_" + rowId + "' value='" + project.description + "'></td>");
                $("#projects" + workspaceId).append("<td><input id='projectWorkspacePosition_" + rowId + "' value='" + project.workspacePosition + "'></td>");
                $("#projects" + workspaceId).append("<td><button onclick='updateProject(" + rowId + ", " + workspaceId + ")'>Update</button>");
                $("#projects" + workspaceId).append("<button onclick='deleteProject(" + rowId + "," + workspaceId + ")'>Delete</button></td>");
                $("#projects").append("</tr>")
            });
        }
    });
}