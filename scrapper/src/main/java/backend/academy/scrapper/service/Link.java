package backend.academy.scrapper.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Link(
        @JsonProperty Long id,
        @JsonProperty String url,
        @JsonProperty List<String> tags,
        @JsonProperty List<String> filters) {}
