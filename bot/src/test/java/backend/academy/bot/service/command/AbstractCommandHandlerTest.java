package backend.academy.bot.service.command;

import backend.academy.bot.controller.ScrapperSender;
import backend.academy.bot.repo.link.RepoLink;
import backend.academy.bot.repo.state.RepoState;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractCommandHandlerTest {
    @Mock
    protected ScrapperSender scrapperSender;

    @Mock
    protected RepoState repoState;

    @Mock
    protected RepoLink repoLink;
}
