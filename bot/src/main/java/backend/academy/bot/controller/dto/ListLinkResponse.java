package backend.academy.bot.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ListLinkResponse(@JsonProperty List<Link> links, @JsonProperty int size) {
    public record Link(
            @JsonProperty Long id,
            @JsonProperty String url,
            @JsonProperty List<String> tags,
            @JsonProperty List<String> filters) {}
}
