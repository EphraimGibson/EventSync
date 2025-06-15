package pro.ephraimgibson.ibm.internproject.eventsync;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.ephraimgibson.ibm.internproject.eventsync.controllers.EventRestController;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Event;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class EventSyncApplicationTests {


    @Test
    void contextLoads() {

    }

    private Map<String, Object> createMap() {

        return Map.of("EventId", 1L,
                "Total Feedbacks", 16,
                "Sentiment Count", Map.of(
                        "POSITIVE", 5,
                        "NEGATIVE", 3,
                        "NEUTRAL", 8
                ),
                "Percentage of Sentiments", Map.of(
                        "POSITIVE", 31.25,
                        "NEGATIVE", 18.75,
                        "NEUTRAL", 50
                ),
                "Overall Sentiment", 43,

                "Average Feedback Scores", Map.of(
                        "POSITIVE", 35,
                        "NEGATIVE", 65,
                        "NEUTRAL", 65,
                        "Overall Sentiment Score", 32)
        );
    }

}
