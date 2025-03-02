package backend.academy.scrapper.service.sdk;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class SDKRestClientConfig {
    @Bean(name="SDK")
    public RestClient restClient(){
        return RestClient.builder().build();
    }
}
