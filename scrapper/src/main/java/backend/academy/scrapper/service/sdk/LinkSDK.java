package backend.academy.scrapper.service.sdk;

import java.time.ZonedDateTime;

public interface LinkSDK {
    boolean validURL(String url);

    boolean haveUpdate(String url, ZonedDateTime lastUpdate);
}
