package pro.ephraimgibson.ibm.internproject.eventsync.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Event;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;
import pro.ephraimgibson.ibm.internproject.eventsync.service.EventService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventRestController {
    private final EventService eventService;

    @GetMapping
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event){
        Event savedEvent = eventService.addEvent(event);
        return ResponseEntity.ok(savedEvent);
    }

    @PostMapping("/{eventId}/feedback")
    public ResponseEntity<Feedback> createFeedback(@PathVariable Long eventId, @RequestBody Feedback feedback){
        Optional<Event> eventForFeedback = eventService.getSingleEvent(eventId);

        if(eventForFeedback.isEmpty()) return ResponseEntity.notFound().build();

        feedback.setEvent(eventForFeedback.get());
        Feedback savedFeedback = eventService.submitFeedback(feedback);

        return ResponseEntity.ok(savedFeedback);
    }

    @GetMapping("/{eventId}/feedback")
    public List<Feedback> getAllFeedbacksFromAnEvent(@PathVariable Long eventId){
        return eventService.getAllFeedBackByEvent(eventId);
    }


}
