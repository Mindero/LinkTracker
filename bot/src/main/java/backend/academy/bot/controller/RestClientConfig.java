package backend.academy.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean
    public RestClient restClient(@Autowired ScrapperConfig scrapperConfig){
        String baseUrl = "http://localhost:" + scrapperConfig.port() + "/";
        return RestClient.builder().baseUrl(baseUrl).build();
    }
}
