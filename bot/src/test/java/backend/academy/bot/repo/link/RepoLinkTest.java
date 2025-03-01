package backend.academy.bot.repo.link;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RepoLinkTest {

    RepoLink repoLink;

    @BeforeEach
    void setup(){
        repoLink = new RepoLink();
    }

    @Test
    void addUrl() {
        repoLink.addUrl(1L, "aboba");

        Link result = repoLink.getLastChatLink(1L);
        Link expected = new Link("aboba", new ArrayList<>(), new ArrayList<>());
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void addTags() {
        repoLink.addUrl(1L, "aboba");
        repoLink.addTags(1L, List.of("a", "b"));

        Link result = repoLink.getLastChatLink(1L);
        Link expected = new Link("aboba", List.of("a", "b"), new ArrayList<>());
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void addFilters() {
        repoLink.addUrl(1L, "aboba");
        repoLink.addTags(1L, List.of("a", "b"));
        repoLink.addFilters(1L, List.of("c", "d"));

        Link result = repoLink.getLastChatLink(1L);
        Link expected = new Link("aboba", List.of("a", "b"), List.of("c", "d"));
        assertThat(result).isEqualTo(expected);
    }
}
