package backend.academy.bot.repo.link;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RepoLink {
    private final Map<Long, Link> lastChatLink;
    public RepoLink() {
        lastChatLink = new HashMap<>();
    }
    public void addUrl(Long id, String url){
        lastChatLink.put(id, new Link(url));
    }
    public void addTags(Long id, List<String> tags){
        Link currentLink = lastChatLink.get(id);
        lastChatLink.put(id, currentLink.setTags(tags));
    }
    public void addFilters(Long id, List<String> filters){
        Link currentLink = lastChatLink.get(id);
        lastChatLink.put(id, currentLink.setFilters(filters));
    }

    public Link getLastChatLink(Long id){
        return lastChatLink.get(id);
    }
}
