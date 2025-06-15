package pro.ephraimgibson.ibm.internproject.eventsync.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Event;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;
import pro.ephraimgibson.ibm.internproject.eventsync.service.EventService;
import pro.ephraimgibson.ibm.internproject.eventsync.service.FeedbackService;
import pro.ephraimgibson.ibm.internproject.eventsync.service.SentimentSummaryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventRestController {
    private final EventService eventService;
    private final SentimentSummaryService sentimentSummaryService;
    private final FeedbackService feedbackService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        Event savedEvent = eventService.addEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    @PostMapping("/{eventId}/feedback")
    public ResponseEntity<Feedback> createFeedback(@PathVariable Long eventId, @Valid @RequestBody Feedback feedback) {
        Event event = eventService.getSingleEvent(eventId);
        feedback.setEvent(event);

        Feedback savedFeedback = feedbackService.submitFeedback(feedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFeedback);
    }

    @GetMapping("/{eventId}/feedback")
    public List<Feedback> getSingleEventFeedbacks(@PathVariable Long eventId) {
        return feedbackService.getAllFeedbacksOfAnEvent(eventId);
    }

    @GetMapping("/{eventId}/summary")
    public Map<String, Object> getSingleEventFeedbackSummary(@PathVariable Long eventId) {
        return sentimentSummaryService.getFeedbackSummary(eventId);
    }

    @GetMapping("/feedback")
    public List<Feedback> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/summary")
    public Map<String,Object> getAllEventFeedbackSummary(){
        return sentimentSummaryService.getAllEventsSentimentSummary();
    }
}
