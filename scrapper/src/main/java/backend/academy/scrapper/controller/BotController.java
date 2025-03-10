package backend.academy.scrapper.controller;

import backend.academy.scrapper.controller.dto.AddLinkRequest;
import backend.academy.scrapper.controller.dto.ListLinkResponse;
import backend.academy.scrapper.controller.dto.RemoveLinkRequest;
import backend.academy.scrapper.exception.NotSuchSDKException;
import backend.academy.scrapper.repo.Track;
import backend.academy.scrapper.service.Link;
import backend.academy.scrapper.service.ScrapperService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BotController {
    private final ScrapperService service;

    public BotController(@Autowired ScrapperService scrapperService) {
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

    @PostMapping("/links")
    public void addTrackLink(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody AddLinkRequest linkRequest)
            throws NotSuchSDKException {
        log.info("id = {} Получен запрос на добавление ссылки {}", id, linkRequest);
        service.addTrackLink(id, new Track(id, linkRequest.link(), linkRequest.tags(), linkRequest.filters()));
        log.info("id = {} Успешно добавилась ссылка", id);
    }

    @DeleteMapping("/links")
    public void unTrack(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody RemoveLinkRequest linkRequest) {
        log.info("id = {} Получен запрос на удаление ссылки {}", id, linkRequest);
        service.unTrack(id, linkRequest.link());
        log.info("id = {} Успешно удалилась ссылка", id);
    }

    @GetMapping("/links")
    public ListLinkResponse getLinkList(@RequestHeader("Tg-Chat-Id") Long id) {
        log.info("id = {} Получен запрос на получение всех ссылок", id);
        List<Link> links = service.getLinkList(id);
        log.info("id = {} Успешно получены все ссылки", id);
        return new ListLinkResponse(links, links.size());
    }
}
