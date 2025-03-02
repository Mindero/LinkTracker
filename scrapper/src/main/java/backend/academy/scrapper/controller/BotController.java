package backend.academy.scrapper.controller;

import backend.academy.scrapper.controller.dto.AddLinkRequest;
import backend.academy.scrapper.controller.dto.ListLinkResponse;
import backend.academy.scrapper.controller.dto.RemoveLinkRequest;
import backend.academy.scrapper.exception.NotSuchSDKException;
import backend.academy.scrapper.service.Link;
import backend.academy.scrapper.repo.Track;
import backend.academy.scrapper.service.ScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class BotController {
    private final ScrapperService service;

    public BotController(@Autowired ScrapperService scrapperService){
        service = scrapperService;
    }
    @PostMapping("/tg-chat/{id}")
    public void addChat(@PathVariable("id") Long id){
        service.addUser(id);
    }

    // TODO: add delete chat
    @DeleteMapping("/tg-chat/{id}")
    public void deleteChat(@PathVariable("id") Long id){

    }

    @PostMapping("/links")
    public void addTrackLink(@RequestHeader("Tg-Chat-Id") Long id,
                             @RequestBody AddLinkRequest linkRequest)
        throws NotSuchSDKException {
        service.addTrackLink(id,
            new Track(id,
                linkRequest.link(),
                linkRequest.tags(),
                linkRequest.filters()));
    }
    @DeleteMapping("/links")
    public void unTrack(@RequestHeader("Tg-Chat-Id") Long id,
                        @RequestBody RemoveLinkRequest linkRequest){
        service.unTrack(id, linkRequest.link());
    }
    @GetMapping("/links")
    public ListLinkResponse getLinkList(@RequestHeader("Tg-Chat-Id") Long id){
        List<Link> links = service.getLinkList(id);
        return new ListLinkResponse(links, links.size());
    }
}
