package backend.academy.scrapper.repo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode
@Getter
@ToString
public class Track {
    private final Long id;
    private final String link;
    private final List<String> tags;
    private final List<String> filters;
    @Setter
    private ZonedDateTime lastUpdate;

    public Track(Long id, String link, List<String> tags, List<String> filters, ZonedDateTime lastUpdate) {
        this.id = id;
        this.link = link;
        this.tags = tags;
        this.filters = filters;
        this.lastUpdate = lastUpdate;
    }

    public Track(Long id, String link) {
        this(id, link, new ArrayList<>(), new ArrayList<>());
    }

    public Track(Long id, String link, List<String> tags, List<String> filters) {
        this(id, link, tags, filters, ZonedDateTime.now());
    }

    public void setCurrentTime() {
        lastUpdate(ZonedDateTime.now());
    }
}
