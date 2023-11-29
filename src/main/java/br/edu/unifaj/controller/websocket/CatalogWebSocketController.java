package br.edu.unifaj.controller.websocket;

import br.edu.unifaj.dto.CatalogDto;
import br.edu.unifaj.entity.Workspace;
import br.edu.unifaj.service.CatalogService;
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
public class CatalogWebSocketController {

    @Autowired
    WorkspaceService workspaceService;

    @Autowired
    CatalogService catalogService;

    @JsonView(View.Card.class)
    @MessageMapping("/workspace/{workspaceId}/project/catalog.add")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public Workspace insertCatalog(@DestinationVariable Long workspaceId, @Payload CatalogDto catalog) throws Exception {
        try {
            catalogService.insert(catalog);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return workspaceService.findById(workspaceId);
    }

    @JsonView(View.Card.class)
    @MessageMapping("/workspace/{workspaceId}/project/catalog.update/{catalogId}")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public Workspace updateCatalog(@DestinationVariable Long workspaceId, @DestinationVariable Long catalogId, @Payload CatalogDto catalog) throws Exception {
        try {
            catalogService.update(catalogId, catalog);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return workspaceService.findById(workspaceId);
    }

    @JsonView(View.Card.class)
    @MessageMapping("/workspace/{workspaceId}/project/catalog.delete/{catalogId}")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public Workspace deleteCatalog(@DestinationVariable Long workspaceId, @DestinationVariable Long catalogId) throws Exception {
        try {
            catalogService.deleteCatalogById(catalogId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return workspaceService.findById(workspaceId);
    }
}
