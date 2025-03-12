package backend.academy.scrapper.service.sdk;

import backend.academy.scrapper.service.sdk.LinkSDK;
import backend.academy.scrapper.service.sdk.SdkEnum;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SdkRegistry {
    private final Map<SdkEnum, LinkSDK> sdkMap = new ConcurrentHashMap<>();

    public SdkRegistry(List<LinkSDK> sdkList){
        sdkList.forEach(t -> {
            sdkMap.put(t.getSdkEnum(), t);
        });
    }

    public LinkSDK getSdk(SdkEnum sdkEnum){
        return sdkMap.get(sdkEnum);
    }
}
