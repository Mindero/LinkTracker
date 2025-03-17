package backend.academy.bot.service;

import backend.academy.bot.repo.state.RepoState;
import backend.academy.bot.repo.state.StateFSM;
import backend.academy.bot.service.command.AbstractCommandHandler;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BotService {
    private final RepoState repoState;
    private final List<AbstractCommandHandler> commandHandlers;

    public final String DELIMITER = ";";
    public final String DELIMITER_MSG = "Разделителем является символ " + DELIMITER;

    public BotService(RepoState stateRepository, List<AbstractCommandHandler> commands) {
        repoState = stateRepository;
        commandHandlers = commands;
    }

    public String handle(Long id, String rawText) {
        log.info("id = {} Обработка сообщения {}", id, rawText);
        StateFSM state = repoState.getState(id);
        for (AbstractCommandHandler commandHandler : commandHandlers) {
            if (commandHandler.match(rawText, state)) {
                return commandHandler.handle(id, deleteCommand(rawText));
            }
        }
        return "Пу-пу-пу... я не понимаю ваше сообщение";
    }

    /**
     * Удаляет команду в начале сообщения, пробелы в начале и в конце
     *
     * @param text - сообщение пользователя
     * @return сообщение без команды и пробелов в начале и конце
     */
    public String deleteCommand(String text) {
        int beginIndex = 0;
        if (!text.isEmpty() && text.charAt(0) == '/') {
            for (int i = 0; i < text.length(); ++i) {
                if (text.charAt(i) == ' ') {
                    beginIndex = i;
                    break;
                }
            }
        }
        return text.substring(beginIndex).trim();
    }
}
