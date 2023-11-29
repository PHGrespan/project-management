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

@Controller
public class ProjectWebSocketController {

    @Autowired
    WorkspaceService workspaceService;

    @Autowired
    ProjectService projectService;

    @JsonView(View.Card.class)
    @MessageMapping("/workspace/{workspaceId}/project.add")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public Workspace insertProject(@DestinationVariable Long workspaceId, @Payload ProjectDto project) throws Exception {
        try {
            projectService.insert(project);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return workspaceService.findById(workspaceId);
    }

    @JsonView(View.Card.class)
    @MessageMapping("/workspace/{workspaceId}/project.update/{projectId}")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public Workspace updateProject(@DestinationVariable Long workspaceId, @DestinationVariable Long projectId, @Payload ProjectDto project) throws Exception {
        try {
            projectService.update(projectId, project);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return workspaceService.findById(workspaceId);
    }

    @JsonView(View.Card.class)
    @MessageMapping("/workspace/{workspaceId}/project.delete/{projectId}")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public Workspace deleteProject(@DestinationVariable Long workspaceId, @DestinationVariable Long projectId) throws Exception {
        try {
            projectService.deleteProjectById(projectId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return workspaceService.findById(workspaceId);
    }
}
