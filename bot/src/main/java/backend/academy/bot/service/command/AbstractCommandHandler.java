package backend.academy.bot.service.command;

import backend.academy.bot.controller.ScrapperSender;
import backend.academy.bot.repo.link.RepoLink;
import backend.academy.bot.repo.state.RepoState;
import backend.academy.bot.repo.state.StateFSM;

public abstract class AbstractCommandHandler {
    protected final ScrapperSender scrapperSender;
    protected final RepoState repoState;
    protected final RepoLink repoLink;

    public final String DELIMITER = ";";
    public final String DELIMITER_MSG = "Разделителем является символ " + DELIMITER;

    public AbstractCommandHandler(ScrapperSender scrapperSender, RepoState repoState, RepoLink repoLink) {
        this.scrapperSender = scrapperSender;
        this.repoState = repoState;
        this.repoLink = repoLink;
    }

    public abstract boolean match(String text, StateFSM stateFSM);

    public abstract String handle(Long id, String text);
}
