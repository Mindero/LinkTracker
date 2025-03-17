package backend.academy.dto;

import java.util.List;

public record Link(Long id, String url, List<String> tags, List<String> filters) {}
