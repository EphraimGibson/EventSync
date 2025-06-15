package pro.ephraimgibson.ibm.internproject.eventsync.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.ephraimgibson.ibm.internproject.eventsync.data.EventRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Event;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getSingleEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(()-> new EntityNotFoundException("Event not found with id: " + eventId));
    }
}

