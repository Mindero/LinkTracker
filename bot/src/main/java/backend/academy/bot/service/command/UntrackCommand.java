package backend.academy.bot.service.command;

import backend.academy.bot.controller.ScrapperSender;
import backend.academy.bot.repo.link.RepoLink;
import backend.academy.bot.repo.state.RepoState;
import backend.academy.bot.repo.state.StateFSM;
import backend.academy.dto.RemoveLinkRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UntrackCommand extends AbstractCommandHandler {
    public UntrackCommand(ScrapperSender scrapperSender, RepoState repoState, RepoLink repoLink) {
        super(scrapperSender, repoState, repoLink);
    }

    @Override
    public boolean match(String text, StateFSM stateFSM) {
        return text.startsWith("/untrack");
    }

    @Override
    public String handle(Long id, String link) {
        log.info("id = {} Пользователь собирается удалить ссылку", id);
        scrapperSender.untrack(id, new RemoveLinkRequest(link));
        return "Ссылка удалена";
    }
}
