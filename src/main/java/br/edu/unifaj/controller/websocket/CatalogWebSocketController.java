package br.edu.unifaj.controller.websocket;

import br.edu.unifaj.dto.CatalogDto;
import br.edu.unifaj.entity.Project;
import br.edu.unifaj.service.CatalogService;
import br.edu.unifaj.service.ProjectService;
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
public class CatalogWebSocketController {

    @Autowired
    ProjectService projectService;

    @Autowired
    CatalogService catalogService;

    @JsonView(View.Card.class)
    @MessageMapping("/project/{projectId}/catalog.add")
    @SendTo("/topic/project/{projectId}/catalog.list")
    public List<Project> insertCatalog(@DestinationVariable Long projectId, @Payload CatalogDto catalog) throws Exception {
        catalogService.insert(catalog);
        return Collections.singletonList(projectService.findById(projectId));
    }

    @JsonView(View.Card.class)
    @MessageMapping("/project/{oldProjectId}/{newProjectId}/catalog.update/{catalogId}")
    @SendTo({"/topic/project/{oldProjectId}/catalog.list", "/topic/project/{newProjectId}/catalog.list"})
    public List<Project> updateProject(@DestinationVariable Long oldProjectId, @DestinationVariable Long newProjectId, @DestinationVariable Long catalogId, @Payload CatalogDto catalog) throws Exception {
        catalogService.update(catalogId, catalog);
        return Arrays.asList(projectService.findById(oldProjectId), projectService.findById(newProjectId));
    }

    @JsonView(View.Card.class)
    @MessageMapping("/project/{projectId}/catalog.delete/{catalogId}")
    @SendTo("/topic/project/{projectId}/catalog.list")
    public List<Project> deleteProject(@DestinationVariable Long projectId, @DestinationVariable Long catalogId) throws Exception {
        catalogService.deleteCatalogById(catalogId);
        return Collections.singletonList(projectService.findById(projectId));
    }
}
