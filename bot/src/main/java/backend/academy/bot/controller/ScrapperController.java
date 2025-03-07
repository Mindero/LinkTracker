package backend.academy.bot.controller;

import backend.academy.bot.controller.dto.LinkUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ScrapperController {
    Bot bot;

    public ScrapperController(@Autowired Bot bot) {
        this.bot = bot;
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void getUpdates(@RequestBody LinkUpdate linkUpdate) {
        String description = linkUpdate.description();
        // TODO: добавить логирование
        System.out.println("Get update " + description);
        linkUpdate.tgChatIds().forEach(id -> bot.sendMessage(id, description));
    }
}
