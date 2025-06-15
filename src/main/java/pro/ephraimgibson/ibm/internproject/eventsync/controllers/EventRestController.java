package pro.ephraimgibson.ibm.internproject.eventsync.controllers;

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
@RequestMapping("/events")
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
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event savedEvent = eventService.addEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    @PostMapping("/{eventId}/feedback")
    public ResponseEntity<Feedback> createFeedback(@PathVariable Long eventId, @RequestBody Feedback feedback) {
        Event event = eventService.getSingleEvent(eventId);
        feedback.setEvent(event);

        Feedback savedFeedback = feedbackService.submitFeedback(feedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFeedback);
    }

    @GetMapping("/{eventId}/feedback")
    public List<Feedback> getEventFeedbacks(@PathVariable Long eventId) {
        return feedbackService.getAllFeedbacksOfAnEvent(eventId);
    }

    @GetMapping("/{eventId}/summary")
    public Map<String, Object> getEventFeedbackAnalysisSummary(@PathVariable Long eventId) {
        return sentimentSummaryService.getFeedbackSummary(eventId);
    }

    @GetMapping("/feedback")
    public List<Feedback> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/summary")
    public Map<String,Object> getAllEventSummary(){
        return sentimentSummaryService.getAllEventsSentimentSummary();
    }
}
