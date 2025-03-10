package backend.academy.scrapper.controller;

import backend.academy.scrapper.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class BotRestClientConfig {
    @Bean(name = "bot")
    @Primary
    public RestClient restClient(@Autowired BotConfig botConfig) {
        return RestClient.builder().baseUrl(botConfig.url()).build();
    }
}
