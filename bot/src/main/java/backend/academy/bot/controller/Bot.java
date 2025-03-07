package backend.academy.bot.controller;

import backend.academy.bot.BotConfig;
import backend.academy.bot.service.BotService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bot {
    private final TelegramBot bot;
    private final BotService service;

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
                        // Ошибка из Телеграма
                        e.response().errorCode();
                        e.response().description();
                    } else {
                        // TODO: добавить логирование
                        e.printStackTrace();
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
            // TODO: добавить логирование
            System.out.println("Ошибка обработки сообщения: " + e.getMessage());
            e.printStackTrace();
            sendMessage(chatId, "Произошла ошибка:\n" + e.getMessage());
        }
    }

    public void sendMessage(Long chatId, String msg) {
        bot.execute(new SendMessage(chatId, msg));
    }
}
