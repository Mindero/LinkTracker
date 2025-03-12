package backend.academy.bot.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.academy.bot.controller.ScrapperSender;
import backend.academy.bot.repo.link.Link;
import backend.academy.bot.repo.link.RepoLink;
import backend.academy.bot.repo.state.RepoState;
import backend.academy.bot.repo.state.StateFSM;
import java.util.ArrayList;
import java.util.List;
import backend.academy.dto.ListLinkResponse;
import backend.academy.dto.RemoveLinkRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BotServiceTest {
    @Mock
    ScrapperSender scrapperSender;

    @Mock
    RepoState repoState;

    @Mock
    RepoLink repoLink;

    @InjectMocks
    BotService botService;

    @Test
    void handle() {}

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

    @Test
    void start() {
        Long id = 1L;

        doNothing().when(scrapperSender).addChat(id);
        doNothing().when(repoState).setState(id, StateFSM.COOL);

        String result = botService.start(id);
        assertThat(result).isEqualTo("Здравствуйте!");
        verify(repoState, times(1)).setState(id, StateFSM.COOL);
        verify(repoState, times(1)).setState(any(), any());
        verify(scrapperSender, times(1)).addChat(id);
    }

    @Test
    void trackLink_successful() {
        Long id = 1L;
        String url = "aboba";

        doNothing().when(repoLink).addUrl(id, url);
        doNothing().when(repoState).setState(id, StateFSM.TAGS);

        String result = botService.trackLink(id, url);
        assertThat(result).isEqualTo("Введите теги (опционально)." + botService.DELIMITER_MSG);
        verify(repoLink, times(1)).addUrl(id, url);
        verify(repoState, times(1)).setState(id, StateFSM.TAGS);
    }

    @Test
    void trackTag_successful() {
        Long id = 1L;
        List<String> tags = List.of("a", "b");

        doNothing().when(repoLink).addTags(id, tags);
        doNothing().when(repoState).setState(id, StateFSM.FILTER);

        String result = botService.trackTag(id, "a;b");
        assertThat(result).isEqualTo("Введите фильтры (опционально)." + botService.DELIMITER_MSG);
        verify(repoLink, times(1)).addTags(id, tags);
        verify(repoState, times(1)).setState(id, StateFSM.FILTER);
    }

    @Test
    void trackFilter_successful() {
        Long id = 1L;
        List<String> filters = List.of("a", "b");

        doNothing().when(repoLink).addFilters(id, filters);
        doNothing().when(repoState).setState(id, StateFSM.COOL);

        botService.trackFilter(id, "a;b");
        verify(repoLink, times(1)).addFilters(id, filters);
        verify(repoState, times(1)).setState(id, StateFSM.COOL);
    }

    @Test
    void track_successful() {
        Long id = 1L;

        doNothing().when(scrapperSender).track(anyLong(), any());
        when(repoLink.getLastChatLink(id)).thenReturn(new Link("aboba", new ArrayList<>(), new ArrayList<>()));

        String result = botService.track(id);
        assertThat(result).isEqualTo("Ссылка успешно добавлена");
        verify(scrapperSender, times(1)).track(any(), any());
        verify(repoLink).getLastChatLink(id);
    }

    @Test
    void unTrack_successful() {
        doNothing().when(scrapperSender).untrack(1L, new RemoveLinkRequest("aboba"));

        String result = botService.unTrack(1L, "aboba");
        assertThat(result).isEqualTo("Ссылка удалена");
        verify(scrapperSender, times(1)).untrack(anyLong(), any());
    }

    @Test
    void list_emptyList() {
        ListLinkResponse response = new ListLinkResponse(new ArrayList<>(), 0);

        when(scrapperSender.getLinkList(1L)).thenReturn(response);

        String result = botService.list(1L);
        assertThat(result).isEqualTo("Список ссылок пустой");
    }

    @Test
    void list_notEmptyList() {
        ListLinkResponse response = new ListLinkResponse(
                List.of(
                        new backend.academy.dto.Link(1L, "aboba", new ArrayList<>(), new ArrayList<>()),
                        new backend.academy.dto.Link(1L, "bob", new ArrayList<>(), new ArrayList<>())),
                2);

        when(scrapperSender.getLinkList(1L)).thenReturn(response);

        String result = botService.list(1L);
        String expect = "Ваш список ссылок:\naboba\nbob";
        assertThat(result).isEqualTo(expect);
    }
}
