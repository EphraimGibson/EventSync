package pro.ephraimgibson.ibm.internproject.eventsync.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.ephraimgibson.ibm.internproject.eventsync.data.FeedbackRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public Feedback submitFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacksOfAnEvent(Long eventId) {
        return feedbackRepository.findByEvent_Id(eventId);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
}


