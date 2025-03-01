package backend.academy.bot.controller;

import backend.academy.bot.BotConfig;
import backend.academy.bot.service.BotService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotController {
    private final TelegramBot bot;
    private final BotService service;

    public BotController(@Autowired BotConfig botConfig, @Autowired BotService botService) {
        bot = new TelegramBot.Builder(botConfig.telegramToken()).build();
        this.service = botService;
        startListen();
    }

    private void startListen() {
        // Создание Обработчика ошибок
        bot.setUpdatesListener(updates -> {

            // TODO: добавить асинхронность здесь
            updates.forEach(this::handle);

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                // Ошибка из Телеграма
                e.response().errorCode();
                e.response().description();
            } else {
                // Как видно проблема сети
                e            .printStackTrace();
            }
        });
    }

    public void handle (Update update){
        Long chatId = update.message().chat().id();
        String text = update.message().text();

        String message = service.handle(chatId, text);
        bot.execute(new SendMessage(chatId, message));
    }
}
