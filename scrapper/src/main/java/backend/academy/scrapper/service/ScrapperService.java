package backend.academy.scrapper.service;

import backend.academy.scrapper.exception.LinkDontExistException;
import backend.academy.scrapper.exception.NotSuchSDKException;
import backend.academy.scrapper.repo.LinkRepository;
import backend.academy.scrapper.repo.Track;
import backend.academy.scrapper.service.sdk.LinkSDK;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScrapperService {
    private final LinkRepository linkRepository;

    private final List<LinkSDK> sdkList;

    public ScrapperService(@Autowired LinkRepository linkRepository, @Autowired List<LinkSDK> sdkList) {
        this.linkRepository = linkRepository;
        this.sdkList = sdkList;
    }

    public void addUser(Long id) {
        linkRepository.addUser(id);
    }

    public void addTrackLink(Long id, Track track) throws NotSuchSDKException {
        if (sdkList.stream().noneMatch(t -> t.validURL(track.link()))) {
            log.warn("id = {} Ссылка {} не валидна", id, track);
            throw new NotSuchSDKException("Не удалось распарсить такую ссылку");
        }
        linkRepository.addTrack(id, track);
    }

    public void unTrack(Long id, String link) {
        if (!linkRepository.unTrack(id, link)) {
            log.warn("id = {} Не существует ссылки {}", id, link);
            throw new LinkDontExistException("Такой ссылки не существует");
        }
    }

    public List<Link> getLinkList(Long id) {
        return linkRepository.getIdLinks(id).stream()
                .map(track -> new Link(id, track.link(), track.tags(), track.filters()))
                .toList();
    }
}
