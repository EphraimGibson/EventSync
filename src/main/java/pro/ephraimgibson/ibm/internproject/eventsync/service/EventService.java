package pro.ephraimgibson.ibm.internproject.eventsync.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.ephraimgibson.ibm.internproject.eventsync.data.EventRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.data.FeedbackRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Event;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final FeedbackRepository feedbackRepository;

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    public Event addEvent(Event event){
        return eventRepository.save(event);
    }

    public Feedback submitFeedback(Feedback feedback){
        return feedbackRepository.save(feedback);
    }

    public Optional<Event> getSingleEvent(Long eventId){
        return eventRepository.findById(eventId);
    }

    public List<Feedback> getAllFeedBackByEvent(Long eventId){
        return feedbackRepository.findByEvent_Id(eventId);
    }
}
