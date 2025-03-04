package backend.academy.scrapper.repo;

import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class LinkRepository {
    private final Map<Long, List<Track>> userLinks;

    public LinkRepository(){
        userLinks = new HashMap<>();
    }

    public void addUser(Long id){
        userLinks.putIfAbsent(id, new ArrayList<>());
    }

    public void addTrack(Long id, Track track){
        addUser(id);
        userLinks.get(id).add(track);
    }
    public void unTrack(Long id, String link){
        List<Track> userTracks = userLinks.get(id);
        userTracks.removeIf(t -> t.link().equals(link));
    }
    public List<Track> getLinkList(Long id){
        return userLinks.getOrDefault(id, new ArrayList<>());
    }

    // TODO: add test
    public List<Track> getALlTracks(){
        return userLinks.values()
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    // TODO: add test
    public void changeLastUpdate(Long id, String url){
        List<Track> links = userLinks.get(id);
        links.stream()
            .filter(t -> t.link().equals(url))
            .forEach(Track::setCurrentTime);
    }
}
