package backend.academy.scrapper.repo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class LinkRepository {
    private final Map<Long, Set<Track>> userLinks;

    public LinkRepository() {
        userLinks = new ConcurrentHashMap<>();
    }

    public void addUser(Long id) {
        userLinks.putIfAbsent(id, new HashSet<>());
    }

    public void addTrack(Long id, Track track) {
        addUser(id);
        userLinks.get(id).add(track);
    }

    public boolean unTrack(Long id, String link) {
        Set<Track> userTracks = userLinks.get(id);
        return userTracks.removeIf(t -> t.link().equals(link));
    }

    public Set<Track> getIdLinks(Long id) {
        return userLinks.getOrDefault(id, new HashSet<>());
    }

    public Set<Track> getALlTracks() {
        return userLinks.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public void changeLastUpdate(Long id, String url) {
        Set<Track> links = userLinks.get(id);
        links.stream().filter(t -> t.link().equals(url)).forEach(Track::setCurrentTime);
    }
}
