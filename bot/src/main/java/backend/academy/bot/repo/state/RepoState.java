package backend.academy.bot.repo.state;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RepoState {
    private final Map<Long, StateFSM> chatState;

    public RepoState(){
        chatState = new HashMap<>();
    }

    public StateFSM getState(Long id){
        return chatState.getOrDefault(id, StateFSM.COOL);
    }
    public void setState(Long id, StateFSM  state){
        chatState.put(id, state);
    }
}
