package backend.academy.scrapper.repo;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
