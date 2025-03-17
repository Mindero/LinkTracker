package backend.academy.bot.service.command;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.academy.bot.repo.link.Link;
import backend.academy.bot.repo.state.StateFSM;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class TrackFilterCommandTest extends AbstractCommandHandlerTest {
    @InjectMocks
    TrackFilterCommand trackFilterCommand;

    @Test
    void match() {
        boolean actualTrue = trackFilterCommand.match("/link sadaf,egmo/start", StateFSM.FILTER);
        boolean actualFalse = trackFilterCommand.match("/link sadaf,egmo/start", StateFSM.TAGS);

        assertThat(actualTrue).isTrue();
        assertThat(actualFalse).isFalse();
    }

    @Test
    void inMemoryRepoUpdate() {
        Long id = 1L;
        List<String> filters = List.of("a", "b");

        doNothing().when(repoLink).addFilters(id, filters);
        doNothing().when(repoState).setState(id, StateFSM.COOL);

        trackFilterCommand.inMemoryRepoUpdate(id, "a;b");
        verify(repoLink, times(1)).addFilters(id, filters);
        verify(repoState, times(1)).setState(id, StateFSM.COOL);
    }

    @Test
    void sendToScrapper_successful() {
        Long id = 1L;

        doNothing().when(scrapperSender).track(anyLong(), any());
        when(repoLink.getLastChatLink(id)).thenReturn(new Link("aboba", new ArrayList<>(), new ArrayList<>()));

        String result = trackFilterCommand.sendToScrapper(id);
        assertThat(result).isEqualTo("Ссылка успешно добавлена");
        verify(scrapperSender, times(1)).track(any(), any());
        verify(repoLink).getLastChatLink(id);
    }
}
