package backend.academy.bot.controller;

import backend.academy.bot.BotConfig;
import backend.academy.bot.service.BotService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bot {
    private final TelegramBot bot;
    private final BotService service;

//    private final Logger
    public Bot(@Autowired BotConfig botConfig, @Autowired BotService botService) {
        bot = new TelegramBot.Builder(botConfig.telegramToken()).build();
        this.service = botService;
        startListen();
    }

    private void startListen() {
        bot.setUpdatesListener(
                updates -> {
                    CompletableFuture.runAsync(() -> updates.forEach(this::handle));
                    return UpdatesListener.CONFIRMED_UPDATES_ALL;
                },
                e -> {
                    if (e.response() != null) {
                        log.error("Ошибка при получении сообщения из ТГ ", e);
                    } else {
                        log.error("Ошибка при получении сообщения из ТГ");
                    }
                });
    }

    private void handle(Update update) {
        Long chatId = update.message().chat().id();
        String text = update.message().text();
        try {
            String message = service.handle(chatId, text);
            sendMessage(chatId, message);
        } catch (RuntimeException e) {
            log.warn("Ошибка обработки сообщения:", e);
            sendMessage(chatId, "Произошла ошибка:\n" + e.getMessage());
        }
    }

    public void sendMessage(Long chatId, String msg) {
        log.info("id = {} Вывод сообщения {}", chatId, msg);
        bot.execute(new SendMessage(chatId, msg));
    }
}
