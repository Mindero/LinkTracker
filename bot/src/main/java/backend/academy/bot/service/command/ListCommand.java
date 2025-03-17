package backend.academy.bot.service.command;

import backend.academy.bot.controller.ScrapperSender;
import backend.academy.bot.repo.link.RepoLink;
import backend.academy.bot.repo.state.RepoState;
import backend.academy.bot.repo.state.StateFSM;
import backend.academy.dto.ListLinkResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListCommand extends AbstractCommandHandler {
    public ListCommand(ScrapperSender scrapperSender, RepoState repoState, RepoLink repoLink) {
        super(scrapperSender, repoState, repoLink);
    }

    @Override
    public boolean match(String text, StateFSM stateFSM) {
        return text.startsWith("/list");
    }

    @Override
    public String handle(Long id, String text) {
        log.info("id = {} Пользователь собирается получить все отслеживаемые ссылки", id);
        ListLinkResponse response = scrapperSender.getLinkList(id);
        List<String> urls =
                response.links().stream().map(backend.academy.dto.Link::url).toList();
        if (urls.isEmpty()) return "Список ссылок пустой";
        return "Ваш список ссылок:\n" + String.join("\n", urls);
    }
}
