package backend.academy.bot.repo.state;

import backend.academy.bot.repo.link.RepoLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RepoStateTest {
    RepoState repoState;

    @BeforeEach
    void setup(){
        repoState = new RepoState();
    }
    @Test
    void setState() {
        repoState.setState(1L, StateFSM.COOL);

        assertThat(repoState.getState(1L)).isEqualTo(StateFSM.COOL);
    }
}
