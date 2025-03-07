package backend.academy.bot.controller;

import backend.academy.bot.controller.dto.AddLinkRequest;
import backend.academy.bot.controller.dto.ApiErrorResponse;
import backend.academy.bot.controller.dto.ListLinkResponse;
import backend.academy.bot.controller.dto.RemoveLinkRequest;
import backend.academy.bot.exception.ScrapperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.io.IOException;

@Component
public class ScrapperSender {

    private final RestClient restClient;

    public ScrapperSender(@Autowired RestClient restClient) {
        this.restClient = restClient;
    }

    public void addChat(Long id) {
        restClient
                .post()
                .uri("/tg-chat/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> responseHandler(response, Void.class));
    }

    public void track(Long id, AddLinkRequest addLinkRequest) {
        restClient
                .post()
                .uri("/links")
                .header("Tg-Chat-Id", id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .body(addLinkRequest)
                .exchange((request, response) -> responseHandler(response, Void.class));
    }

    public void untrack(Long id, RemoveLinkRequest link) {
        System.out.println("unTrack");
        restClient
                .method(HttpMethod.DELETE)
                .uri("/links")
                .header("Tg-Chat-Id", id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .body(link)
                .exchange((request, response) -> responseHandler(response, Void.class));
    }

    public ListLinkResponse getLinkList(Long id) {
        return restClient
                .get()
                .uri("/links")
                .header("Tg-Chat-Id", id.toString())
                .exchange((request, response) -> responseHandler(response, ListLinkResponse.class));
    }

    private static <T> T responseHandler(
        RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse response,
        Class<T> acceptBodyClass) throws IOException {
        if (!response.getStatusCode().is2xxSuccessful()) {
            // TODO: добавить логирование
            ApiErrorResponse error = response.bodyTo(ApiErrorResponse.class);
            System.out.println("Поймана ошибка " + error);
            throw new ScrapperException(error.exceptionMessage());
        }
        return response.bodyTo(acceptBodyClass);
    }
}
