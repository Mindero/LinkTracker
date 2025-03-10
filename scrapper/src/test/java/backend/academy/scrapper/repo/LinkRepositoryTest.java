package backend.academy.scrapper.repo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkRepositoryTest {

    LinkRepository repo;

    @BeforeEach
    void setup() {
        repo = new LinkRepository();
    }

    @Test
    void addUser() {
        repo.addUser(1L);

        assertThat(repo.getLinkList(1L)).isEqualTo(new ArrayList<>());
    }

    @Test
    void addTrack() {
        Track track = new Track(1L, "aboba", new ArrayList<>(), new ArrayList<>());

        repo.addUser(1L);
        repo.addTrack(1L, track);
        repo.addTrack(1L, track);

        assertThat(repo.getLinkList(1L)).isEqualTo(List.of(track, track));
    }

    @Test
    void unTrack() {
        Track track1 = new Track(1L, "aboba", new ArrayList<>(), new ArrayList<>());
        Track track2 = new Track(1L, "ddd", new ArrayList<>(), new ArrayList<>());

        repo.addUser(1L);
        repo.addTrack(1L, track1);
        repo.addTrack(1L, track2);
        repo.unTrack(1L, "aboba");

        assertThat(repo.getLinkList(1L)).isEqualTo(List.of(track2));
    }

    @Test
    void getAllTracks() {
        Track track1 = new Track(1L, "a", new ArrayList<>(), new ArrayList<>());
        Track track2 = new Track(2L, "b", List.of("t"), List.of("f"));

        repo.addUser(1L);
        repo.addUser(2L);
        repo.addTrack(1L, track1);
        repo.addTrack(2L, track2);

        assertThat(repo.getALlTracks()).isEqualTo(List.of(track1, track2));
    }

    @Test
    void changeLastUpdate() {
        Track track1 = new Track(1L, "a", new ArrayList<>(), new ArrayList<>());

        repo.addTrack(1L, track1);
        repo.changeLastUpdate(1L, track1.link());
        ZonedDateTime expectedLastUpdate = ZonedDateTime.now();
        ZonedDateTime actualLastUpdate = repo.getLinkList(1L).getFirst().lastUpdate();


        assertThat(actualLastUpdate).isBetween(
            expectedLastUpdate.minusMinutes(1),
            expectedLastUpdate.plusMinutes(1));
    }
}
