package backend.academy.scrapper.service.sdk.github;

import backend.academy.scrapper.ScrapperConfig;
import backend.academy.scrapper.service.sdk.LinkSDK;
import backend.academy.scrapper.service.sdk.SdkEnum;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class GitHubSDK implements LinkSDK {
    private static final String PREFIX = "https://github.com/";
    private final RestClient restClient;
    private final String token;

    public GitHubSDK(RestClient.Builder restClientBuilder, ScrapperConfig scrapperConfig) {
        this.restClient = restClientBuilder.build();
        token = scrapperConfig.githubToken();
    }

    @Override
    public boolean validURL(String url) {
        if (!url.startsWith(PREFIX)) return false;
        String apiUrl = url.replaceFirst(PREFIX, "https://api.github.com/repos/");
        boolean result = restClient
                .get()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + token)
                .exchange((request, response) -> response.getStatusCode().is2xxSuccessful());
        log.info("Github url {} result {}", url, result);
        return result;
    }

    @Override
    public boolean haveUpdate(String url, ZonedDateTime lastUpdate) {
        String apiUrl = url.replaceFirst(PREFIX, "https://api.github.com/repos/") + "/commits?since="
                + lastUpdate.withZoneSameInstant(ZoneOffset.UTC);

        boolean result = false;
        try {
            List<?> updates = restClient
                    .get()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(List.class);

            result = !updates.isEmpty();
        } catch (Exception ignored) {
        }
        log.info("Update github url {} lastUpdate {} result {}", url, lastUpdate, result);
        return result;
    }

    @Override
    public SdkEnum getSdkEnum() {
        return SdkEnum.GITHUB;
    }
}
