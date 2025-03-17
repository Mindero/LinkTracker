package backend.academy.bot.service.command;

import backend.academy.bot.controller.ScrapperSender;
import backend.academy.bot.repo.link.RepoLink;
import backend.academy.bot.repo.state.RepoState;
import backend.academy.bot.repo.state.StateFSM;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrackLinkCommand extends AbstractCommandHandler {
    public TrackLinkCommand(ScrapperSender scrapperSender, RepoState repoState, RepoLink repoLink) {
        super(scrapperSender, repoState, repoLink);
    }

    @Override
    public boolean match(String text, StateFSM stateFSM) {
        return stateFSM == StateFSM.COOL && text.startsWith("/track");
    }

    @Override
    public String handle(Long id, String link) {
        log.info("id = {} Пользователь ввёл ссылку {}", id, link);
        repoLink.addUrl(id, link);
        repoState.setState(id, StateFSM.TAGS);
        return "Введите теги (опционально)." + DELIMITER_MSG;
    }
}
