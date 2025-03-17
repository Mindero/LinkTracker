package backend.academy.bot.service.command;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import backend.academy.bot.repo.state.StateFSM;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class TrackTagCommandTest extends AbstractCommandHandlerTest {

    @InjectMocks
    TrackTagCommand trackTagCommand;

    @Test
    void match() {
        boolean actualTrue = trackTagCommand.match("/link sadaf,egmo/start", StateFSM.TAGS);
        boolean actualFalse1 = trackTagCommand.match("/link sadaf,egmo/start", StateFSM.COOL);
        boolean actualFalse2 = trackTagCommand.match("/link sadaf,egmo/start", StateFSM.FILTER);

        assertThat(actualTrue).isTrue();
        assertThat(actualFalse1).isFalse();
        assertThat(actualFalse2).isFalse();
    }

    @Test
    void trackTag_successful() {
        Long id = 1L;
        List<String> tags = List.of("a", "b");

        doNothing().when(repoLink).addTags(id, tags);
        doNothing().when(repoState).setState(id, StateFSM.FILTER);

        String result = trackTagCommand.handle(id, "a;b");
        assertThat(result).isEqualTo("Введите фильтры (опционально)." + trackTagCommand.DELIMITER_MSG);
        verify(repoLink, times(1)).addTags(id, tags);
        verify(repoState, times(1)).setState(id, StateFSM.FILTER);
    }
}
