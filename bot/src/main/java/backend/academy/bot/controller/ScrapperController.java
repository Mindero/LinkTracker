package backend.academy.bot.controller;

import backend.academy.bot.controller.dto.LinkUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ScrapperController {
    BotController botController;

    public ScrapperController(@Autowired BotController botController) {
        this.botController = botController;
    }

    @PostMapping("/update")
    public ResponseEntity<Void> getUpdates(@RequestBody LinkUpdate linkUpdate) {
        String description = linkUpdate.description();
        System.out.println("Get update " + description);
        linkUpdate.tgChatIds().forEach(t -> botController.sendMessage(t, description));

        return ResponseEntity.ok().build();
    }
}
