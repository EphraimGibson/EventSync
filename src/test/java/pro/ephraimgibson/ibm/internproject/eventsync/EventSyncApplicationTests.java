package pro.ephraimgibson.ibm.internproject.eventsync;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;
import pro.ephraimgibson.ibm.internproject.eventsync.controllers.EventRestController;
import pro.ephraimgibson.ibm.internproject.eventsync.data.FeedbackRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Event;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;
import pro.ephraimgibson.ibm.internproject.eventsync.model.SentimentAnalysis;
import pro.ephraimgibson.ibm.internproject.eventsync.model.SentimentResult;
import pro.ephraimgibson.ibm.internproject.eventsync.service.EventService;
import pro.ephraimgibson.ibm.internproject.eventsync.service.SentimentService;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class EventSyncApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventService eventService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public SentimentService sentimentService() {
            return Mockito.mock(SentimentService.class);
        }
    }

    @Autowired
    private EventRestController eventRestController;

    @Autowired
    private SentimentService mocksentimentService;

    @Autowired
    FeedbackRepository feedbackRepository;

    private final Long eventId = 1L;
    private final Long invalidId = 99L;

    @Test
    void testCreateEvent_withValidJson() throws Exception {
        //GIVEN
        String eventJson = """
                    {
                        "title": "NBA all star night",
                        "description": "One night of friendly basketball with famous basketball players",
                        "date": "2025-09-19",
                        "location": "Los Angeles"
                    }
                """;

        //WHEN && THEN
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("NBA all star night"));
    }


    @Test
    public void testCreateEvent_MissingRequiredFieldsThrowException() {
        //GIVEN
        Event invalidEvent = new Event();

        //WHEN & THEN
        assertThrows(ConstraintViolationException.class, () -> {
            eventRestController.createEvent(invalidEvent);
        });
    }

    @Test
    void testCreateEvent_MissingRequiredFields_ReturnsBadRequest() throws Exception {
        String eventJson = """
                    {
                        "description": "One night of friendly basketball with famous basketball players",
                        "date": "2025-09-19",
                        "location": "Los Angeles"
                    }
                """;

        // WHEN & THEN: Expect HTTP 400 Bad Request due to validation failure
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testShouldThrowExceptionWhenContentIsNotProvided() throws Exception {
        String feedbackJson = """
                    {
                        "content": ""
                    }
                """;

        mockMvc.perform(post("/api/events/{eventId}/feedback", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedbackJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testShouldCreateFeedBackWithValidIdAndContent() throws Exception {
        // GIVEN
        SentimentAnalysis mockAnalysis = createMockSentimentAnalysis();
        when(mocksentimentService.getAISentimentAnalysis(anyString())).thenReturn(mockAnalysis);

        String feedbackJson = """
                    {
                        "content": "Very Bad Event"
                    }
                """;

        // WHEN & THEN
        mockMvc.perform(post("/api/events/{eventId}/feedback", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedbackJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.topSentiment").value("POSITIVE"))
                .andExpect(jsonPath("$.fullSentimentAnalysis.results").isArray())
                .andExpect(jsonPath("$.fullSentimentAnalysis.results.length()").value(3));

        verify(mocksentimentService).getAISentimentAnalysis(anyString());
    }

    @Test
    void testCreateFeedbackShouldReturnNotFound_WhenInvalidId() throws Exception {
        String feedbackJson = """
                    {
                        "content": "Some feedback"
                    }
                """;

        mockMvc.perform(post("/api/events/{eventId}/feedback", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedbackJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetEventFeedbacksShouldThrowExceptionWhenCalled_InvalidID() throws Exception {
        mockMvc.perform(get("/api/events/{eventId}/feedback", invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testShouldProvideSummaryWithMatchingMapFields() throws Exception {

        Event event = createNewEvent();
        List<Feedback> feedbacks = createFeedbackListsWithEventAndScores(event);


        mockMvc.perform(get("/api/events/{eventId}/summary", event.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Summary").exists())
                .andExpect(jsonPath("$.['Event Id']").value(event.getId()))
                .andExpect(jsonPath("$.Summary['Average Feedback Scores']").exists())
                .andExpect(jsonPath("$.Summary['Sentiment Count']").exists())
                .andExpect(jsonPath("$.Summary['Total Feedbacks']").value(feedbacks.size()))
                .andExpect(jsonPath("$.Summary['Percentage of Sentiments']").exists());
    }

    private SentimentAnalysis createMockSentimentAnalysis() {
        return new SentimentAnalysis(List.of(new SentimentResult("LABEL_2", 0.5694),
                new SentimentResult("LABEL_1", 0.1023),
                new SentimentResult("LABEL_0", 0.4034)));
    }

    private Event createNewEvent() {
        Event event = new Event();
        event.setTitle("ALl out hackathon");
        return eventService.addEvent(event);
    }

    private List<Feedback> createFeedbackListsWithEventAndScores(Event event) {
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();

        feedback1.setEvent(event);
        feedback1.setTopSentiment("POSITIVE");
        feedback1.setNeutralScore(0.1);
        feedback1.setPositiveScore(0.7);
        feedback1.setNegativeScore(0.2);
        feedback1.setContent("GOod show");

        feedback2.setEvent(event);
        feedback2.setTopSentiment("NEGATIVE");
        feedback2.setNeutralScore(0.1);
        feedback2.setPositiveScore(0.1);
        feedback2.setNegativeScore(0.8);
        feedback2.setContent("the worst ever");

        return feedbackRepository.saveAll(List.of(feedback1, feedback2));
    }

}
