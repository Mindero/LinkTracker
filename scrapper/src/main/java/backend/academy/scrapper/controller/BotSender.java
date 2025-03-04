package backend.academy.scrapper.controller;

import backend.academy.scrapper.controller.dto.LinkUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class BotSender {
    private final RestClient restClient;

    public BotSender(@Autowired @Qualifier("bot") RestClient restClient){
        this.restClient = restClient;
    }

    public void update(LinkUpdate body){
        restClient.post()
            .uri("/update")
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
            .retrieve()
            .toBodilessEntity();
    }
}
