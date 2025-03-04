package backend.academy.bot;

import backend.academy.bot.controller.ScrapperConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

// @Import(TestcontainersConfiguration.class)
@SpringBootTest
@EnableConfigurationProperties({BotConfig.class, ScrapperConfig.class})
class BotApplicationTests {

    @Test
    void contextLoads() {}
}
