package backend.academy.scrapper.service.sdk.stackoverflow;


import backend.academy.scrapper.service.sdk.LinkSDK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StackOverflowSDK implements LinkSDK {
    private static final String PREFIX = "https://stackoverflow.com/questions/";
    private static final String API_PREFIX = "https://api.stackexchange.com/2.3/";
    private final RestClient restClient;

    public StackOverflowSDK(@Autowired @Qualifier("SDK") RestClient restClient) {
        this.restClient = restClient;
    }

    public String fetchId(String url){
        Pattern pattern = Pattern.compile("questions/(.*)/");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1); // Возвращаем ID вопроса
        }
        return null; // Если ID не найден
    }

    @Override
    public boolean validURL(String question) {
        if (!question.startsWith(PREFIX)) return false;
        String id = fetchId(question);
        String url = API_PREFIX + "/questions/" + id + "?site=stackoverflow";
        System.out.println("Id stackoverflow question " + id);
        boolean result =  restClient
            .get()
            .uri(url)
            .exchange((request, response) -> response.getStatusCode().is2xxSuccessful());
        System.out.println("Result of stackOverflow " + result + "\n" + url);
        return result;
    }

    @Override
    public boolean haveUpdate(String url, ZonedDateTime lastUpdate) {
        if (!url.startsWith(PREFIX)) return false;
        String id = fetchId(url);
        String apiUrl = API_PREFIX + "/questions/" + id + "/answers?site=stackoverflow&" +
            "fromdate=" + lastUpdate.withZoneSameInstant(ZoneOffset.UTC).toEpochSecond();
        System.out.println("Update stackoverflow " + apiUrl);
        try{
            StackOverflowUpdateResponse response = restClient.get()
                .uri(apiUrl)
                .retrieve()
                .body(StackOverflowUpdateResponse.class);
            return !response.items().isEmpty();
        }
        catch (Throwable throwable){
            return false;
        }
    }
}
