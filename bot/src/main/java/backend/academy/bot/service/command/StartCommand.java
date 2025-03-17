package backend.academy.bot.service.command;

import backend.academy.bot.controller.ScrapperSender;
import backend.academy.bot.repo.link.RepoLink;
import backend.academy.bot.repo.state.RepoState;
import backend.academy.bot.repo.state.StateFSM;
import org.springframework.stereotype.Component;

@Component
public class StartCommand extends AbstractCommandHandler {
    public StartCommand(ScrapperSender scrapperSender, RepoState repoState, RepoLink repoLink) {
        super(scrapperSender, repoState, repoLink);
    }

    @Override
    public boolean match(String text, StateFSM stateFSM) {
        return text.startsWith("/start");
    }

    @Override
    public String handle(Long id, String text) {
        scrapperSender.addChat(id);
        repoState.setState(id, StateFSM.COOL);
        return "Здравствуйте!";
    }
}
