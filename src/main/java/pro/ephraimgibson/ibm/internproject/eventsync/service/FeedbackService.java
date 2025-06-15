package pro.ephraimgibson.ibm.internproject.eventsync.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.ephraimgibson.ibm.internproject.eventsync.data.FeedbackRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;
import pro.ephraimgibson.ibm.internproject.eventsync.model.SentimentAnalysis;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final SentimentService sentimentService;

    public Feedback submitFeedback(Feedback feedback) {

        SentimentAnalysis sentimentAnalysis = sentimentService.getAISentimentAnalysis(feedback.getContent());
        feedback.setFullSentimentAnalysis(sentimentAnalysis);

        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacksOfAnEvent(Long eventId) {
        List<Feedback> feedbacks = feedbackRepository.findByEvent_Id(eventId);
        if (feedbacks.isEmpty()) throw new EntityNotFoundException("No Feedback found for this event:" + eventId);

        return feedbacks;
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
}


