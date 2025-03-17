package backend.academy.bot.service.command;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import backend.academy.bot.repo.state.StateFSM;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class HelpCommandTest extends AbstractCommandHandlerTest {
    @InjectMocks
    HelpCommand helpCommand;

    @Test
    void match() {
        boolean actualTrue = helpCommand.match("/help fmkamsdko/help", StateFSM.COOL);
        boolean actualFalse = helpCommand.match("fmkamsdko /help", StateFSM.COOL);

        assertThat(actualTrue).isTrue();
        assertThat(actualFalse).isFalse();
    }
}
