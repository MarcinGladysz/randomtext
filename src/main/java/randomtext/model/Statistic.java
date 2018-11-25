package randomtext.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Statistic {

    @JsonProperty("freq_word")
    String mostFrequentWord;

    @JsonProperty("avg_paragraph_size")
    Integer avrParagraphLength;

    @JsonProperty("avg_paragraph_processing_time")
    double avrParagraphProcessingTime;

    @JsonProperty("total_processing_time")
    Long totalProcessingTime;
}
