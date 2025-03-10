package backend.academy.scrapper.service.sdk.github;

import backend.academy.scrapper.ScrapperConfig;
import backend.academy.scrapper.service.sdk.LinkSDK;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GitHubSDK implements LinkSDK {
    private static final String PREFIX = "https://github.com/";
    private final RestClient restClient;
    private final String token;

    public GitHubSDK(@Autowired @Qualifier("SDK") RestClient restClient, @Autowired ScrapperConfig scrapperConfig) {
        this.restClient = restClient;
        token = scrapperConfig.githubToken();
    }

    @Override
    public boolean validURL(String repo) {
        if (!repo.startsWith(PREFIX)) return false;
        String url = repo.replaceFirst(PREFIX, "https://api.github.com/repos/");
        System.out.println("GitHub " + url);
        return restClient
                .get()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .exchange((request, response) -> response.getStatusCode().is2xxSuccessful());
    }

    @Override
    public boolean haveUpdate(String url, ZonedDateTime lastUpdate) {
        if (!url.startsWith(PREFIX)) return false;
        System.out.println("LocalDateTime " + lastUpdate);
        String apiUrl = url.replaceFirst(PREFIX, "https://api.github.com/repos/") + "/commits?since="
                + lastUpdate.withZoneSameInstant(ZoneOffset.UTC);
        System.out.println("Github update url " + apiUrl);
        try {
            List<?> updates = restClient
                    .get()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(List.class);

            return !updates.isEmpty();
        } catch (Exception exception) {
            return false;
        }
    }
}
