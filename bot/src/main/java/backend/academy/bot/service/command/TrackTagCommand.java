package backend.academy.bot.service.command;

import backend.academy.bot.controller.ScrapperSender;
import backend.academy.bot.repo.link.RepoLink;
import backend.academy.bot.repo.state.RepoState;
import backend.academy.bot.repo.state.StateFSM;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrackTagCommand extends AbstractCommandHandler {
    public TrackTagCommand(ScrapperSender scrapperSender, RepoState repoState, RepoLink repoLink) {
        super(scrapperSender, repoState, repoLink);
    }

    @Override
    public boolean match(String text, StateFSM stateFSM) {
        return stateFSM.equals(StateFSM.TAGS);
    }

    @Override
    public String handle(Long id, String text) {
        log.info("id = {} Пользователь ввёл теги {}", id, text);
        repoLink.addTags(id, Arrays.stream(text.split(DELIMITER)).toList());
        repoState.setState(id, StateFSM.FILTER);
        return "Введите фильтры (опционально)." + DELIMITER_MSG;
    }
}
