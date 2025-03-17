package backend.academy.bot.service.command;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import backend.academy.bot.repo.state.StateFSM;
import backend.academy.dto.ListLinkResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class ListCommandTest extends AbstractCommandHandlerTest {
    @InjectMocks
    ListCommand listCommand;

    @Test
    void match() {
        boolean actualTrue = listCommand.match("/list fmkamsdko/list", StateFSM.COOL);
        boolean actualFalse = listCommand.match("/help fmkamsdko /list", StateFSM.COOL);

        assertThat(actualTrue).isTrue();
        assertThat(actualFalse).isFalse();
    }

    @Test
    void list_emptyList() {
        ListLinkResponse response = new ListLinkResponse(new ArrayList<>(), 0);

        when(scrapperSender.getLinkList(1L)).thenReturn(response);

        String result = listCommand.handle(1L, "test");
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

        String result = listCommand.handle(1L, "test");
        String expect = "Ваш список ссылок:\naboba\nbob";
        assertThat(result).isEqualTo(expect);
    }
}
