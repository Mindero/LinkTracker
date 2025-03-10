package backend.academy.scrapper.controller.dto;

import java.util.List;

public record AddLinkRequest(String link, List<String> tags, List<String> filters) {}
