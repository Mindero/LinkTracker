package backend.academy.scrapper.service.sdk.stackoverflow;

import backend.academy.scrapper.service.sdk.LinkSDK;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class StackOverflowSDK implements LinkSDK {
    private static final String PREFIX = "https://stackoverflow.com/questions/";
    private static final String API_PREFIX = "https://api.stackexchange.com/2.3/";
    private static final Pattern pattern = Pattern.compile("questions/(.*)/");
    private final RestClient restClient;

    public StackOverflowSDK(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public Optional<String> fetchId(String url) {
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return Optional.of(matcher.group(1)); // Возвращаем ID вопроса
        }
        return Optional.empty(); // Если ID не найден
    }

    @Override
    public boolean validURL(String url) {
        if (!url.startsWith(PREFIX)) return false;
        Optional<String> id = fetchId(url);
        if (id.isEmpty()) return false;
        String apiUrl = API_PREFIX + "/questions/" + id.get() + "?site=stackoverflow";
        boolean result = restClient.get().uri(apiUrl).exchange((request, response) -> response.getStatusCode()
                .is2xxSuccessful());
        log.info("Stackoverflow url {} [id {}} result {}", url, id, result);
        return result;
    }

    @Override
    public boolean haveUpdate(String url, ZonedDateTime lastUpdate) {
        if (!url.startsWith(PREFIX)) return false;
        Optional<String> id = fetchId(url);
        if (id.isEmpty()) return false;
        String apiUrl = API_PREFIX + "/questions/" + id.get() + "/answers?site=stackoverflow&" + "fromdate="
                + lastUpdate.withZoneSameInstant(ZoneOffset.UTC).toEpochSecond();

        boolean result = false;
        try {
            StackOverflowUpdateResponse response =
                    restClient.get().uri(apiUrl).retrieve().body(StackOverflowUpdateResponse.class);
            result = !response.items().isEmpty();
        } catch (Exception ignored) {
        }
        log.info("Update stackoverflow url {} lastUpdate {} result {}", url, lastUpdate, result);
        return result;
    }
}
