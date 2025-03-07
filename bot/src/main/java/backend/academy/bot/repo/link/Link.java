package backend.academy.bot.repo.link;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Link {
    private final String url;
    private List<String> tags;
    private List<String> filters;

    public Link(String url, List<String> tags, List<String> filters) {
        this.url = url;
        this.tags = tags;
        this.filters = filters;
    }

    public Link(String url) {
        this(url, new ArrayList<>(), new ArrayList<>());
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public String url() {
        return url;
    }

    public List<String> tags() {
        return tags;
    }

    public List<String> filters() {
        return filters;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Link) obj;
        return Objects.equals(this.url, that.url) &&
                Objects.equals(this.tags, that.tags) &&
                Objects.equals(this.filters, that.filters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, tags, filters);
    }

    @Override
    public String toString() {
        return "Link[" +
                "url=" + url + ", " +
                "tags=" + tags + ", " +
                "filters=" + filters + ']';
    }

}
