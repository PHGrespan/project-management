package br.edu.unifaj.controller.websocket;

import br.edu.unifaj.dto.WorkspaceDto;
import br.edu.unifaj.entity.User;
import br.edu.unifaj.service.UserService;
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
public class WorkspaceWebSocketController {

    @Autowired
    UserService userService;

    @Autowired
    WorkspaceService workspaceService;

    @JsonView(View.Workspace.class)
    @MessageMapping("/user/{userId}/workspace.add")
    @SendTo("/topic/user/{userId}/workspace.list")
    public User insertWorkspace(@DestinationVariable Long userId, @Payload WorkspaceDto workspace) throws Exception {
        workspaceService.insert(userId, workspace);
        return userService.findById(userId);
    }

    @JsonView(View.Workspace.class)
    @MessageMapping("/user/{userId}/workspace.update/{workspaceId}")
    @SendTo("/topic/user/{userId}/workspace.list")
    public User updateWorkspace(@DestinationVariable Long userId, @DestinationVariable Long workspaceId, @Payload WorkspaceDto workspace) throws Exception {
        workspaceService.update(workspaceId, workspace);
        return userService.findById(userId);
    }

    @JsonView(View.Workspace.class)
    @MessageMapping("/user/{userId}/workspace.delete/{workspaceId}")
    @SendTo("/topic/user/{userId}/workspace.list")
    public User deleteWorkspace(@DestinationVariable Long userId, @DestinationVariable Long workspaceId) throws Exception {
        workspaceService.deleteWorkspaceByUserIdAndId(userId, workspaceId);
        return userService.findById(userId);
    }

    @JsonView(View.Workspace.class)
    @MessageMapping("/user.add/{newUserId}/workspace/{workspaceId}")
    @SendTo({"/topic/user/{newUserId}/workspace.list"})
    public User addUserToWorkspace(@DestinationVariable Long workspaceId, @DestinationVariable Long newUserId) throws Exception {
        workspaceService.addUserToWorkspace(newUserId, workspaceId);
        return userService.findById(newUserId);
    }
}
