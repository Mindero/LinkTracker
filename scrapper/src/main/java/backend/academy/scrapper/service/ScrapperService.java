package backend.academy.scrapper.service;

import backend.academy.scrapper.exception.NotSuchSDKException;
import backend.academy.scrapper.repo.LinkRepository;
import backend.academy.scrapper.repo.Track;
import backend.academy.scrapper.service.sdk.LinkSDK;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
            throw new NotSuchSDKException("Не удалось распарсить такую ссылку");
        }
        linkRepository.addTrack(id, track);
    }

    public void unTrack(Long id, String link) {
        linkRepository.unTrack(id, link);
    }

    public List<Link> getLinkList(Long id) {
        return linkRepository.getLinkList(id).stream()
                .map(track -> new Link(id, track.link(), track.tags(), track.filters()))
                .toList();
    }
}
