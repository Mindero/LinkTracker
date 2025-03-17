package backend.academy.bot.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import backend.academy.bot.controller.ScrapperSender;
import backend.academy.bot.repo.link.RepoLink;
import backend.academy.bot.repo.state.RepoState;
import backend.academy.bot.repo.state.StateFSM;
import backend.academy.bot.service.command.AbstractCommandHandler;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class BotServiceTest {
    @Mock
    ScrapperSender scrapperSender;

    @Mock
    RepoState repoState;

    @Mock
    RepoLink repoLink;

    @Mock
    List<AbstractCommandHandler> mockCommandHandlers;

    @InjectMocks
    BotService botService;

    @Autowired
    List<AbstractCommandHandler> commandHandlers;

    @Test
    void handleWrongCommand() {
        String text = "/uknownCommand test";
        Long id = 1L;

        when(repoState.getState(id)).thenReturn(StateFSM.COOL);
        when(mockCommandHandlers.iterator()).thenReturn(Collections.emptyIterator());

        String actual = botService.handle(id, text);
        assertThat(actual).isEqualTo("Пу-пу-пу... я не понимаю ваше сообщение");
    }

    @Test
    void deleteCommand_dontDelete() {
        String result = botService.deleteCommand("simple text");
        assertThat(result).isEqualTo("simple text");
    }

    @Test
    void deleteCommand_DeleteFirstCommand() {
        String result = botService.deleteCommand("/command text text");
        assertThat(result).isEqualTo("text text");
    }
}
