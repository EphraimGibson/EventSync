package pro.ephraimgibson.ibm.internproject.eventsync.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.ephraimgibson.ibm.internproject.eventsync.data.FeedbackRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

    @Mock
    private FeedbackRepository mockFeedbackRepository;

    @InjectMocks
    FeedbackService feedbackService;

    private Feedback createFeedback() {
        return new Feedback();
    }

    private List<Feedback> createFeedbackLists() {
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();

        return List.of(feedback1, feedback2);
    }

    @Test
    public void testSubmitFeedback(){
        //Given
        Feedback feedback = createFeedback();
        feedback.setId(100L);
        when(mockFeedbackRepository.save(feedback)).thenReturn(feedback);

        //When
        Feedback result = feedbackService.submitFeedback(feedback);

        //Then
        verify(mockFeedbackRepository).save(feedback);
        assertEquals(100L, result.getId());
    }

    @Test
    public void testShouldRetrieveAllFeedbacksFromRepo(){
        //Given
        List<Feedback> feedbackList = createFeedbackLists();
        when(mockFeedbackRepository.findAll()).thenReturn(feedbackList);

        //When
        List<Feedback> results = feedbackService.getAllFeedbacks();

        //Then
        assertEquals(feedbackList.size(), results.size());
        verify(mockFeedbackRepository).findAll();
    }
}
