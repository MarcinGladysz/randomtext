package randomtext.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RandomText {
    @JsonProperty("type")
    String type;

    @JsonProperty("amount")
    int amount;

    @JsonProperty("number")
    int number;

    @JsonProperty("number_max")
    int number_max;

    @JsonProperty("format")
    String format;

    @JsonProperty("time")
    String time;

    @JsonProperty("text_out")
    String text_out;

}
