package backend.academy.bot.service.command;

import backend.academy.bot.controller.ScrapperSender;
import backend.academy.bot.repo.link.RepoLink;
import backend.academy.bot.repo.state.RepoState;
import backend.academy.bot.repo.state.StateFSM;

public class HelpCommand extends AbstractCommandHandler {
    public HelpCommand(ScrapperSender scrapperSender, RepoState repoState, RepoLink repoLink) {
        super(scrapperSender, repoState, repoLink);
    }

    @Override
    public boolean match(String text, StateFSM stateFSM) {
        return text.startsWith("/help");
    }

    @Override
    public String handle(Long id, String text) {
        return """
            /start - регистрация пользователя.
            /help - вывод списка доступных команд.
            /track - начать отслеживание ссылки.
            /untrack - прекратить отслеживание ссылки.
            /list - показать список отслеживаемых ссылок (cписок ссылок, полученных при /track)""";
    }
}
