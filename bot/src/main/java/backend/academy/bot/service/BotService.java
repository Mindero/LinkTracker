package backend.academy.bot.service;

import backend.academy.bot.controller.dto.AddLinkRequest;
import backend.academy.bot.controller.dto.ListLinkResponse;
import backend.academy.bot.controller.dto.RemoveLinkRequest;
import backend.academy.bot.repo.link.Link;
import backend.academy.bot.repo.link.RepoLink;
import backend.academy.bot.repo.state.RepoState;
import backend.academy.bot.repo.state.StateFSM;
import backend.academy.bot.controller.ScrapperController;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class BotService {
    private final ScrapperController scrapperController;
    private final RepoState repoState;
    private final RepoLink repoLink;

    public BotService(@Autowired ScrapperController controller,
                      @Autowired RepoState stateRepository,
                      @Autowired RepoLink linkRepository){
        scrapperController = controller;
        repoState = stateRepository;
        repoLink = linkRepository;
    }

    public String handle(Long id, String text) {
        StateFSM state = repoState.getState(id);
        if (state.equals(StateFSM.TAGS)) return trackTag(id, text);
        if (state.equals(StateFSM.FILTER)){
            trackFilter(id, text);
            return track(id);
        }
        if (text.startsWith("/start"))   return start(id);
        if (text.startsWith("/help"))    return help();
        if (text.startsWith("/track"))   return trackLink(id, deleteCommand(text));
        if (text.startsWith("/unTrack")) return unTrack(id, deleteCommand(text));
        if (text.startsWith("/list"))    return list(id);
        return "Пу-пу-пу... я не понимаю ваше сообщение";
    }

    public String deleteCommand(String text){
        int beginIndex = 0;
        if (!text.isEmpty() && text.charAt(0) == '/'){
            for (int i = 0; i < text.length(); ++i){
                if (text.charAt(i) == ' '){
                    beginIndex = i;
                    break;
                }
            }
        }
        return text.substring(beginIndex).trim();
    }

    // TODO: add help
    public String help(){
        return "Help";
    }

    public String start(Long id){
        scrapperController.addChat(id);
        repoState.setState(id, StateFSM.COOL);
        return "Здравствуйте!";
    }

    public String trackLink(Long id, String link){
        repoLink.addUrl(id, link);
        repoState.setState(id, StateFSM.TAGS);
        return "Введите тэги (опционально)";
    }
    public String trackTag(Long id, String text){
        repoLink.addTags(id, Arrays.stream(text.split(" ")).toList());
        repoState.setState(id, StateFSM.FILTER);
        return "Введите фильтры (опционально)";
    }

    public void trackFilter(Long id, String text){
        repoLink.addFilters(id, Arrays.stream(text.split(" ")).toList());
        repoState.setState(id, StateFSM.COOL);
    }

    public String track(Long id) {
        Link link = repoLink.getLastChatLink(id);
        scrapperController.track(id,
            new AddLinkRequest(link.url(), link.tags(), link.filters()));
        return "Ссылка успешно добавлена";
    }

    public String unTrack(Long id, String link){
        scrapperController.untrack(id, new RemoveLinkRequest(link));
        return "Ссылка удалена";
    }

    public String list (Long id){
        ListLinkResponse response = scrapperController.getLinkList(id);
        List<String> urls = response.links()
            .stream()
            .map(ListLinkResponse.Link::url)
            .toList();
        if (urls.isEmpty()) return "Список ссылок пустой";
        return "Ваш список ссылок:\n" + String.join("\n", urls);
    }

}
