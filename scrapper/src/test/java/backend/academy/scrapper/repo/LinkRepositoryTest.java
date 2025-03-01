package backend.academy.scrapper.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LinkRepositoryTest {

    LinkRepository repo;

    @BeforeEach
    void setup(){
        repo = new LinkRepository();
    }

    @Test
    void addUser() {
        repo.addUser(1L);

        assertThat(repo.getLinkList(1L)).isEqualTo(new ArrayList<>());
    }

    @Test
    void addTrack() {
        Track track = new Track("aboba", new ArrayList<>(), new ArrayList<>());

        repo.addUser(1L);
        repo.addTrack(1L, track);
        repo.addTrack(1L, track);

        assertThat(repo.getLinkList(1L)).isEqualTo(List.of(track, track));
    }

    @Test
    void unTrack() {
        Track track1 = new Track("aboba", new ArrayList<>(), new ArrayList<>());
        Track track2 = new Track("ddd", new ArrayList<>(), new ArrayList<>());

        repo.addUser(1L);
        repo.addTrack(1L, track1);
        repo.addTrack(1L, track2);
        repo.unTrack(1L, "aboba");

        assertThat(repo.getLinkList(1L)).isEqualTo(List.of(track2));
    }
}
