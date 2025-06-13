package pro.ephraimgibson.ibm.internproject.eventsync.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SentimentResult {
    private String label;
    private Double score;

    @JsonCreator
    public SentimentResult(@JsonProperty("label") String label, @JsonProperty("score") double score) {
        this.label = mapLabel(label);
        this.score = score;
    }


    public String mapLabel(String label) {
        return switch (label) {
            case "LABEL_2" -> "POSITIVE";
            case "LABEL_0" -> "NEGATIVE";
            case "LABEL_1" -> "NEUTRAL";
            default -> "Unknown";
        };
    }
}


