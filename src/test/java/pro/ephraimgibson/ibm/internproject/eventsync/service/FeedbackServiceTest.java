package pro.ephraimgibson.ibm.internproject.eventsync.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.ephraimgibson.ibm.internproject.eventsync.data.FeedbackRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;
import pro.ephraimgibson.ibm.internproject.eventsync.model.SentimentAnalysis;
import pro.ephraimgibson.ibm.internproject.eventsync.model.SentimentResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

    @Mock
    private FeedbackRepository mockFeedbackRepository;

    @Mock
    private SentimentService sentimentService;

    @InjectMocks
    FeedbackService feedbackService;

    @Test
    public void testShouldAddSentimentToFeedBackAndSaveNewFeedback() {
        //GIVEN
        Feedback feedback = createFeedback();
        feedback.setContent("The event was wonderful");
        feedback.setId(100L);

        SentimentAnalysis mockSentiment = createASentimentAnalysis();

        when(mockFeedbackRepository.save(feedback)).thenReturn(feedback);
        when(sentimentService.getAISentimentAnalysis("The event was wonderful"))
                .thenReturn(mockSentiment);

        //WHEN
        Feedback result = feedbackService.submitFeedback(feedback);

        //Then
        verify(sentimentService).getAISentimentAnalysis("The event was wonderful");
        verify(mockFeedbackRepository).save(feedback);
        assertEquals(feedback.getFullSentimentAnalysis(), mockSentiment);

    }

    @Test
    public void testThatAllFeedbackFieldWereSetAfterSubmitFeedback(){
        //GIVEN
        Feedback feedback = createFeedback();
        feedback.setContent("The event was wonderful");
        feedback.setId(100L);

        SentimentAnalysis mockSentiment = createASentimentAnalysis();

        when(mockFeedbackRepository.save(feedback)).thenReturn(feedback);
        when(sentimentService.getAISentimentAnalysis("The event was wonderful"))
                .thenReturn(mockSentiment);

        //WHEN
        Feedback result = feedbackService.submitFeedback(feedback);

        // THEN
        assertEquals("POSITIVE", result.getTopSentiment());
        assertEquals(0.8, result.getPositiveScore());
        assertEquals(0.1, result.getNegativeScore());
        assertEquals(0.1, result.getNeutralScore());
        assertEquals(100L, result.getId());
    }


    @Test
    public void testShouldRetrieveAllFeedbacksFromRepo(){
        //GIVEN
        List<Feedback> feedbackList = createFeedbackLists();
        when(mockFeedbackRepository.findAll()).thenReturn(feedbackList);

        //WHEN
        List<Feedback> results = feedbackService.getAllFeedbacks();

        //THEN
        assertEquals(feedbackList.size(), results.size());
        verify(mockFeedbackRepository).findAll();
    }


    private SentimentAnalysis createASentimentAnalysis(){
        return new SentimentAnalysis(List.of(
                new SentimentResult("LABEL_2", 0.8),
                new SentimentResult("LABEL_1", 0.1),
                new SentimentResult("LABEL_0", 0.1)
        ));
    }

    private Feedback createFeedback() {
        return new Feedback();
    }

    private List<Feedback> createFeedbackLists() {
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();

        return List.of(feedback1, feedback2);
    }
}
