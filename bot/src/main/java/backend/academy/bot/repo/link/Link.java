package backend.academy.bot.repo.link;

import java.util.ArrayList;
import java.util.List;

public record Link(String url, List<String> tags, List<String> filters) {
    public Link(String url) {
        this(url, new ArrayList<>(), new ArrayList<>());
    }

    public Link setTags(List<String> tags) {
        return new Link(url, tags, filters);
    }

    public Link setFilters(List<String> filters) {
        return new Link(url, tags, filters);
    }
}
