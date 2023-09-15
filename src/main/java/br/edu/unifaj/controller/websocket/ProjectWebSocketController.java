package br.edu.unifaj.controller.websocket;

import br.edu.unifaj.dto.ProjectDto;
import br.edu.unifaj.entity.Workspace;
import br.edu.unifaj.service.ProjectService;
import br.edu.unifaj.service.WorkspaceService;
import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class ProjectWebSocketController {

    @Autowired
    WorkspaceService workspaceService;

    @Autowired
    ProjectService projectService;

    @JsonView(View.Catalog.class)
    @MessageMapping("/workspace/{workspaceId}/project.add")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public List<Workspace> insertProject(@DestinationVariable Long workspaceId, @Payload ProjectDto project) throws Exception {
        projectService.insert(project);
        return Collections.singletonList(workspaceService.findById(workspaceId));
    }

    @JsonView(View.Catalog.class)
    @MessageMapping("/workspace/{oldWorkspaceId}/{newWorkspaceId}/project.update/{projectId}")
    @SendTo({"/topic/workspace/{oldWorkspaceId}/project.list", "/topic/workspace/{newWorkspaceId}/project.list"})
    public List<Workspace> updateProject(@DestinationVariable Long oldWorkspaceId, @DestinationVariable Long newWorkspaceId, @DestinationVariable Long projectId, @Payload ProjectDto project) throws Exception {
        projectService.update(projectId, project);
        return Arrays.asList(workspaceService.findById(oldWorkspaceId), workspaceService.findById(newWorkspaceId));
    }

    @JsonView(View.Catalog.class)
    @MessageMapping("/workspace/{workspaceId}/project.delete/{projectId}")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public List<Workspace> deleteProject(@DestinationVariable Long workspaceId, @DestinationVariable Long projectId) throws Exception {
        projectService.deleteProjectById(projectId);
        return Collections.singletonList(workspaceService.findById(workspaceId));
    }
}
