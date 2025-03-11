package backend.academy.scrapper.scheduler;

import backend.academy.scrapper.controller.BotSender;
import backend.academy.scrapper.controller.dto.LinkUpdate;
import backend.academy.scrapper.repo.LinkRepository;
import backend.academy.scrapper.repo.Track;
import backend.academy.scrapper.service.sdk.LinkSDK;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateScheduler {
    private final LinkRepository repo;
    private final List<LinkSDK> sdkList;
    private final BotSender botSender;

    private static final String DESCRIPTION_MSG = "Обнаружено обновление на странице ";

    public UpdateScheduler(
            @Autowired LinkRepository repository, @Autowired List<LinkSDK> linkSDKList, @Autowired BotSender sender) {
        repo = repository;
        sdkList = linkSDKList;
        botSender = sender;
    }

    @Scheduled(fixedRate = 60000)
    public void checkAllLinksForUpdate() {
        log.info("Планировщик начал искать обновления");
        Set<Track> allTracks = repo.getALlTracks();
        allTracks.forEach(this::askForUpdate);
        log.info("Планировщик обновлений закончил работу");
    }

    public void askForUpdate(Track track) {
        if (sdkList.stream().anyMatch(t -> t.haveUpdate(track.link(), track.lastUpdate()))) {
            log.info(
                    "Нашлось обновление в ссылке {}\t время последнего обновления = {}",
                    track.link(),
                    track.lastUpdate());
            repo.changeLastUpdate(track.id(), track.link());
            botSender.update(
                    new LinkUpdate(track.id(), track.link(), DESCRIPTION_MSG + track.link(), List.of(track.id())));
        }
    }
}
