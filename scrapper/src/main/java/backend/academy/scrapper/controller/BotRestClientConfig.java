package backend.academy.scrapper.controller;

import backend.academy.scrapper.BotPortConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class BotRestClientConfig {
    @Bean(name = "bot")
    @Primary
    public RestClient restClient(@Autowired BotPortConfig botPortConfig) {
        return RestClient.builder().baseUrl(botPortConfig.url()).build();
    }
}
