package backend.academy.scrapper.controller;

import backend.academy.dto.LinkUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class BotSender {
    private final RestClient restClient;

    public BotSender(@Qualifier("bot") RestClient restClient) {
        this.restClient = restClient;
    }

    public void update(LinkUpdate body) {
        log.info("Отправляется запрос боту на обновление ссылки {}", body);
        restClient
                .post()
                .uri("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }
}
