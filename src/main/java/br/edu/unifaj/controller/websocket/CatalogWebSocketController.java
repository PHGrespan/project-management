package br.edu.unifaj.controller.websocket;

import br.edu.unifaj.dto.CatalogDto;
import br.edu.unifaj.dto.CatalogWithIdDto;
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

import java.util.List;

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
        catalogService.insert(catalog);
        return workspaceService.findById(workspaceId);
    }

    @JsonView(View.Card.class)
    @MessageMapping("/workspace/{workspaceId}/project/catalog.update")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public Workspace updateCatalogs(@DestinationVariable Long workspaceId, @Payload List<CatalogWithIdDto> catalogs) throws Exception {
        for (CatalogWithIdDto catalog : catalogs) {
            CatalogDto catalogDto = new CatalogDto(catalog.getName(), catalog.getIdProject(), catalog.getProjectPosition());
            catalogService.update(catalog.getId(), catalogDto);
        }
        return workspaceService.findById(workspaceId);
    }

    @JsonView(View.Card.class)
    @MessageMapping("/workspace/{workspaceId}/project/catalog.delete/{catalogId}")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public Workspace deleteCatalog(@DestinationVariable Long workspaceId, @DestinationVariable Long catalogId) throws Exception {
        catalogService.deleteCatalogById(catalogId);
        return workspaceService.findById(workspaceId);
    }
}
