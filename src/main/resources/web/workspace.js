function insertWorkspace() {
    stompClient.publish({
        destination: `/app/user/${userId}/workspace.add`,
        body: JSON.stringify({'name': $("#name").val(), 'description': $("#description").val()})
    });
}

function updateWorkspace(id) {
    var name = document.getElementById(`workspaceName_${id}`).value;
    var description = document.getElementById(`workspaceDescription_${id}`).value;

    stompClient.publish({
        destination: `/app/user/${userId}/workspace.update/${id}`,
        body: JSON.stringify({'name': name, 'description': description})
    });
}

function deleteWorkspace(id) {
    stompClient.publish({
        destination: `/app/user/${userId}/workspace.delete/${id}`
    });
}

function showWorkspaces(user) {
    if (user.workspaces.length === 0) {
        $(`#tables`).remove();
    } else {
        $("#tables").html(`<table id="workspaceTable" class="table table-striped"><thead><caption>Workspaces</caption><tr><th>Id</th><th>Name</th><th>Description</th><th>Creation Date</th><th>Update Date</th><th>Owner</th><th>Action</th></tr></thead><tbody id='workspaceBody'></tbody></table>`);

        user.workspaces.sort(function (a, b) {
            return new Date(b.workspace.updateDate) - new Date(a.workspace.updateDate);
        });
        user.workspaces.forEach(workspaces => {

            var workspaceId = workspaces.workspace.id;

            var tableLine = `<td><input id='workspaceId_${workspaceId}' value='${workspaces.workspace.id}' readonly></td>`;
            tableLine += `<td><input id='workspaceName_${workspaceId}' value='${workspaces.workspace.name}'></td>`;
            tableLine += `<td><input id='workspaceDescription_${workspaceId}' value='${workspaces.workspace.description}'></td>`;
            tableLine += `<td><input id='workspaceCreationDate_${workspaceId}' value='${workspaces.workspace.creationDate}' readonly></td>`;
            tableLine += `<td><input id='workspaceUpdateDate_${workspaceId}' value='${workspaces.workspace.updateDate}' readonly></td>`;
            tableLine += `<td><input id='workspaceOwner_${workspaceId}' value='${workspaces.owner}' readonly></td>`;
            tableLine += `<td><button onclick='updateWorkspace(${workspaceId})'>Update</button>`;
            tableLine += `<button onclick='deleteWorkspace(${workspaceId})'>Delete</button></td>`;
            $("#workspaceBody").append(`<tr>${tableLine}</tr>`);

            stompClient.subscribe(`/topic/workspace/${workspaceId}/project.list`, (workspace) => {
                showProjects(JSON.parse(workspace.body));

            });
            console.log(`SUBSCRIBED: /topic/workspace/${workspaceId}/project.list`);

            var requestOptions = {
                method: 'GET',
                redirect: 'follow'
            };

            fetch(`http://localhost:8080/workspaces/${workspaceId}/projects`, requestOptions)
                .then(response => response.json())
                .then(json => {
                    showProjects(json)
                });
        });
    }
}