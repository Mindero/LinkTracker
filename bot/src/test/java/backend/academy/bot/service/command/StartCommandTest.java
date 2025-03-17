package backend.academy.bot.service.command;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import backend.academy.bot.repo.state.StateFSM;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class StartCommandTest extends AbstractCommandHandlerTest {
    @InjectMocks
    StartCommand startCommand;

    @Test
    void match() {
        boolean actualTrue = startCommand.match("/start sadaf,egmo/start", StateFSM.FILTER);
        boolean actualFalse = startCommand.match("/link sadaf,egmo/start", StateFSM.FILTER);

        assertThat(actualTrue).isTrue();
        assertThat(actualFalse).isFalse();
    }

    @Test
    void handle() {
        Long id = 1L;

        doNothing().when(scrapperSender).addChat(id);
        doNothing().when(repoState).setState(id, StateFSM.COOL);

        String result = startCommand.handle(id, "");
        assertThat(result).isEqualTo("Здравствуйте!");
        verify(repoState, times(1)).setState(id, StateFSM.COOL);
        verify(repoState, times(1)).setState(any(), any());
        verify(scrapperSender, times(1)).addChat(id);
    }
}
