package backend.academy.bot.controller;

import backend.academy.bot.exception.ScrapperException;
import backend.academy.dto.AddLinkRequest;
import backend.academy.dto.ApiError;
import backend.academy.dto.ListLinkResponse;
import backend.academy.dto.RemoveLinkRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class ScrapperSender {

    private final RestClient restClient;

    public ScrapperSender(@Autowired RestClient restClient) {
        this.restClient = restClient;
    }

    public void addChat(Long id) {
        log.info("id = {}: Запрос на регистрирование чата", id);
        restClient
                .post()
                .uri("/tg-chat/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> responseHandler(response, Void.class, id, "addChat"));
    }

    public void track(Long id, AddLinkRequest addLinkRequest) {
        log.info("id = {} Запрос на отслеживание ссылки {}", id, addLinkRequest);
        restClient
                .post()
                .uri("/links")
                .header("Tg-Chat-Id", id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .body(addLinkRequest)
                .exchange((request, response) -> responseHandler(response, Void.class, id, "track"));
    }

    public void untrack(Long id, RemoveLinkRequest link) {
        log.info("id = {} Запрос на удаление ссылки {}", id, link);
        restClient
                .method(HttpMethod.DELETE)
                .uri("/links")
                .header("Tg-Chat-Id", id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .body(link)
                .exchange((request, response) -> responseHandler(response, Void.class, id, "untrack"));
    }

    public ListLinkResponse getLinkList(Long id) {
        log.info("id = {} Запрос на получение всех ссылок", id);
        return restClient
                .get()
                .uri("/links")
                .header("Tg-Chat-Id", id.toString())
                .exchange((request, response) -> responseHandler(response, ListLinkResponse.class, id, "getLinkList"));
    }

    private static <T> T responseHandler(
            RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse response,
            Class<T> acceptBodyClass,
            Long id,
            String requestName)
            throws IOException {
        if (!response.getStatusCode().is2xxSuccessful()) {
            ApiError error = response.bodyTo(ApiError.class);
            log.warn("id = {}, Получена ошибка ошибка из Scrapper: {}", id, error);
            throw new ScrapperException(error.exceptionMessage());
        }
        T acceptResponse = response.bodyTo(acceptBodyClass);
        log.info("id = {} Запрос {} Результат {}", id, requestName, acceptResponse);
        return acceptResponse;
    }
}
