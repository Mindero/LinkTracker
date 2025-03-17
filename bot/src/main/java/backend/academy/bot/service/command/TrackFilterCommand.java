package backend.academy.bot.service.command;

import backend.academy.bot.controller.ScrapperSender;
import backend.academy.bot.repo.link.Link;
import backend.academy.bot.repo.link.RepoLink;
import backend.academy.bot.repo.state.RepoState;
import backend.academy.bot.repo.state.StateFSM;
import backend.academy.dto.AddLinkRequest;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class TrackFilterCommand extends AbstractCommandHandler {
    public TrackFilterCommand(ScrapperSender scrapperSender, RepoState repoState, RepoLink repoLink) {
        super(scrapperSender, repoState, repoLink);
    }

    @Override
    public boolean match(String text, StateFSM stateFSM) {
        return stateFSM.equals(StateFSM.FILTER);
    }

    @Override
    public String handle(Long id, String text) {
        inMemoryRepoUpdate(id, text);
        return sendToScrapper(id);
    }

    @NotNull
    public String sendToScrapper(Long id) {
        log.info("id = {} Пользователь собирается отслеживать новую ссылку", id);
        Link link = repoLink.getLastChatLink(id);
        scrapperSender.track(id, new AddLinkRequest(link.url(), link.tags(), link.filters()));
        return "Ссылка успешно добавлена";
    }

    public void inMemoryRepoUpdate(Long id, String text) {
        log.info("id = {} Пользователь ввёл фильтры {}", id, text);
        repoLink.addFilters(id, Arrays.stream(text.split(DELIMITER)).toList());
        repoState.setState(id, StateFSM.COOL);
    }
}
