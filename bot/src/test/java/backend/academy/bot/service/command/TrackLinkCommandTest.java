package backend.academy.bot.service.command;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import backend.academy.bot.repo.state.StateFSM;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class TrackLinkCommandTest extends AbstractCommandHandlerTest {
    @InjectMocks
    TrackLinkCommand trackLinkCommand;

    @Test
    void match() {
        boolean actualTrue = trackLinkCommand.match("/track sadaf,egmo/start", StateFSM.COOL);
        boolean actualFalse1 = trackLinkCommand.match("/track sadaf,egmo/start", StateFSM.TAGS);
        boolean actualFalse2 = trackLinkCommand.match("/track sadaf,egmo/start", StateFSM.FILTER);

        assertThat(actualTrue).isTrue();
        assertThat(actualFalse1).isFalse();
        assertThat(actualFalse2).isFalse();
    }

    @Test
    void trackLink_successful() {
        Long id = 1L;
        String url = "aboba";

        doNothing().when(repoLink).addUrl(id, url);
        doNothing().when(repoState).setState(id, StateFSM.TAGS);

        String result = trackLinkCommand.handle(id, url);
        assertThat(result).isEqualTo("Введите теги (опционально)." + trackLinkCommand.DELIMITER_MSG);
        verify(repoLink, times(1)).addUrl(id, url);
        verify(repoState, times(1)).setState(id, StateFSM.TAGS);
    }
}
