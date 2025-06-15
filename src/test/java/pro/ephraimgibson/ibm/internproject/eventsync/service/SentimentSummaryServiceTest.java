package pro.ephraimgibson.ibm.internproject.eventsync.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SentimentSummaryServiceTest {

    @Mock
    FeedbackService feedbackService;

    @Mock
    SummaryAnalyzer summaryAnalyzer;

    @InjectMocks
    SentimentSummaryService sentimentSummaryService;


    @Test
    void testGetEventFeedbackSummary_ReturnsCorrectFormat() {
        // GIVEN
        Long eventId = 123L;
        List<Feedback> mockFeedbacks = createFeedbackLists();

        Map<String, Object> mockSummary = Map.of("total", 5, "average", 8.0);

        when(feedbackService.getAllFeedbacksOfAnEvent(eventId)).thenReturn(mockFeedbacks);
        when(summaryAnalyzer.countFeedbacks(any())).thenReturn(Map.of("total", 5));
        when(summaryAnalyzer.sumUpAndCalculateAverageOfAllScores(any())).thenReturn(Map.of("average", 8.0));

        // WHEN
        Map<String, Object> result = sentimentSummaryService.getFeedbackSummary(eventId);

        //THEN
        assertThat(result.get("Event Id")).isEqualTo(eventId);
        assertThat(result.get("Summary")).isEqualTo(mockSummary);

        verify(feedbackService).getAllFeedbacksOfAnEvent(eventId);
    }


    @Test
    void testGetAllFeedbackSummary_ReturnsCorrectFormat() {
        // GIVEN
        List<Feedback> mockFeedbacks = createFeedbackLists();

        Map<String, Object> mockSummary = Map.of("total", 5, "average", 8.0);

        when(feedbackService.getAllFeedbacks()).thenReturn(mockFeedbacks);
        when(summaryAnalyzer.countFeedbacks(any())).thenReturn(Map.of("total", 5));
        when(summaryAnalyzer.sumUpAndCalculateAverageOfAllScores(any())).thenReturn(Map.of("average", 8.0));

        // WHEN
        Map<String, Object> result = sentimentSummaryService.getAllEventsSentimentSummary();

        //THEN
        assertThat(result.get("Summary")).isEqualTo(mockSummary);
        verify(feedbackService).getAllFeedbacks();
    }

    private List<Feedback> createFeedbackLists() {
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();

        return List.of(feedback1, feedback2);
    }

}
