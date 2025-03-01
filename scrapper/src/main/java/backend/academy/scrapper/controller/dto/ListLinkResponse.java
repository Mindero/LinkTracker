package backend.academy.scrapper.controller.dto;

import backend.academy.scrapper.service.Link;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ListLinkResponse(@JsonProperty List<Link> links,
                               @JsonProperty Integer size) {
}
