package backend.academy.bot.service.command;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import backend.academy.bot.repo.state.StateFSM;
import backend.academy.dto.RemoveLinkRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UntrackCommandTest extends AbstractCommandHandlerTest {
    @InjectMocks
    UntrackCommand untrackCommand;

    @Test
    void match() {
        boolean actualTrue = untrackCommand.match("/untrack testuntrackLink/untrack", StateFSM.COOL);
        boolean actualFalse = untrackCommand.match("testuntrackLink/untrack", StateFSM.COOL);

        assertThat(actualTrue).isTrue();
        assertThat(actualFalse).isFalse();
    }

    @Test
    void handle() {
        Long id = 1L;
        String link = "aboba";

        doNothing().when(scrapperSender).untrack(id, new RemoveLinkRequest(link));

        String actual = untrackCommand.handle(id, link);

        assertThat(actual).isEqualTo("Ссылка удалена");
        verify(scrapperSender, times(1)).untrack(id, new RemoveLinkRequest(link));
        verify(scrapperSender, times(1)).untrack(anyLong(), any());
    }
}
