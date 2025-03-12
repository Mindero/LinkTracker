package backend.academy.scrapper.controller;

import backend.academy.scrapper.service.ScrapperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ChatController {
    private final ScrapperService service;

    public ChatController(ScrapperService scrapperService) {
        service = scrapperService;
    }

    @PostMapping("/tg-chat/{id}")
    public void addChat(@PathVariable("id") Long id) {
        log.info("id = {} Получен запрос на добавление чата", id);
        service.addUser(id);
        log.info("id = {} Успешно добавился чат", id);
    }

    // TODO: add delete chat
    @DeleteMapping("/tg-chat/{id}")
    public void deleteChat(@PathVariable("id") Long id) {
        log.info("id = {} Получен запрос на удаление чата", id);
        //
        log.info("id = {} Успешно удалился чат", id);
    }
}
