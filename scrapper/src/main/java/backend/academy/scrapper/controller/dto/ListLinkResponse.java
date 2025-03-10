package backend.academy.scrapper.controller.dto;

import backend.academy.scrapper.service.Link;
import java.util.List;

public record ListLinkResponse(List<Link> links, Integer size) {}
