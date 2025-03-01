package backend.academy.bot.controller;


import backend.academy.bot.controller.dto.AddLinkRequest;
import backend.academy.bot.controller.dto.ListLinkResponse;
import backend.academy.bot.controller.dto.RemoveLinkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.Map;

@Component
public class ScrapperController {

    private final RestClient restClient;
    public ScrapperController(@Autowired RestClient restClient){
        this.restClient = restClient;
    }

    public void addChat(Long id) {
        System.out.println("add chat");
        ResponseEntity<Void> response = restClient.post()
            .uri("/tg-chat/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .toBodilessEntity();
    }
    public void track(Long id, AddLinkRequest addLinkRequest){
        ResponseEntity<Void> response = restClient.post()
            .uri("/links")
            .header("Tg-Chat-Id", id.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .body(addLinkRequest)
            .retrieve()
            .toBodilessEntity();
    }
    public void untrack(Long id, RemoveLinkRequest link){
        System.out.println("untrack");
        ResponseEntity<Void> response = restClient.method(HttpMethod.DELETE)
            .uri("/links")
            .header("Tg-Chat-Id", id.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .body(link)
            .retrieve()
            .toBodilessEntity();
    }
    public String getLinkList(Long id){
        ListLinkResponse response = restClient.get()
            .uri("/links")
            .header("Tg-Chat-Id", id.toString())
            .retrieve()
            .body(ListLinkResponse.class);
        List<String> urls = response.links()
            .stream()
            .map(ListLinkResponse.Link::url)
            .toList();
        if (urls.isEmpty()) return "Не дам";
        return String.join("\n", urls);
    }
}
