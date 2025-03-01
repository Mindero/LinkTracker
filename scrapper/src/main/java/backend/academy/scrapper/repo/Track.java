package backend.academy.scrapper.repo;

import java.util.ArrayList;
import java.util.List;

public record Track(String link, List<String> tags, List<String> filters) {
    public Track(String link){
        this(link, new ArrayList<>(), new ArrayList<>());
    }
}
