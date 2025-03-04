package backend.academy.scrapper.service.sdk.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackOverflowUpdateResponse(@JsonProperty List<?> items) {
}
