package backend.academy.scrapper.service.sdk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GitHubSDK implements LinkSDK{
    private static final String PREFIX = "https://github.com/";
    private final RestClient restClient;

    public GitHubSDK(@Autowired RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public boolean validURL(String repo) {
        if (!repo.startsWith(PREFIX)) return false;
        String url = repo.replaceFirst(PREFIX, "https://api.github.com/repos/");
        System.out.println("GitHub " + url);
        return restClient
            .get()
            .uri(url)
            .exchange((request, response) -> response.getStatusCode().is2xxSuccessful());
    }
}
