package backend.academy.scrapper.repo;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class Track {
    private final Long id;
    private final String link;
    private final List<String> tags;
    private final List<String> filters;
    private Long lastUpdate;

    public Track(Long id, String link, List<String> tags, List<String> filters, Long lastUpdate) {
        this.id = id;
        this.link = link;
        this.tags = tags;
        this.filters = filters;
        this.lastUpdate = lastUpdate;
    }

    public Track(Long id, String link){
        this(id, link, new ArrayList<>(), new ArrayList<>());
    }
    public Track(Long id, String link, List<String> tags, List<String> filters){
        this(id, link, tags, filters, Instant.now().getLong(ChronoField.MILLI_OF_SECOND));
    }

    public Long id() {
        return id;
    }

    public String link() {
        return link;
    }

    public List<String> tags() {
        return tags;
    }

    public List<String> filters() {
        return filters;
    }

    public Long lastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setCurrentTime(){
        setLastUpdate(Instant.now().getLong(ChronoField.MILLI_OF_SECOND));
    }
}
