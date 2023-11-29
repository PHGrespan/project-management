package br.edu.unifaj.controller.websocket;

import br.edu.unifaj.dto.CardDto;
import br.edu.unifaj.entity.Workspace;
import br.edu.unifaj.service.CardService;
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
public class CardWebSocketController {

    @Autowired
    WorkspaceService workspaceService;

    @Autowired
    CardService cardService;

    @JsonView(View.Card.class)
    @MessageMapping("/workspace/{workspaceId}/project/catalog/card.add")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public Workspace insertCard(@DestinationVariable Long workspaceId, @Payload CardDto card) throws Exception {
        cardService.insert(card);
        return workspaceService.findById(workspaceId);
    }

    @JsonView(View.Card.class)
    @MessageMapping("/workspace/{workspaceId}/project/catalog/card.update/{cardId}")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public Workspace updateCard(@DestinationVariable Long workspaceId, @DestinationVariable Long cardId, @Payload CardDto card) throws Exception {
        cardService.update(cardId, card);
        return workspaceService.findById(workspaceId);
    }

    @JsonView(View.Card.class)
    @MessageMapping("/workspace/{workspaceId}/project/catalog/card.delete/{cardId}")
    @SendTo("/topic/workspace/{workspaceId}/project.list")
    public Workspace deleteCard(@DestinationVariable Long workspaceId, @DestinationVariable Long cardId) throws Exception {
        cardService.deleteCardById(cardId);
        return workspaceService.findById(workspaceId);
    }
}
