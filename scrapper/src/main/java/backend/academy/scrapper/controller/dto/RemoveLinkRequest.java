package backend.academy.scrapper.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RemoveLinkRequest(@JsonProperty String link) {}
