package pro.ephraimgibson.ibm.internproject.eventsync.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Event;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;
import pro.ephraimgibson.ibm.internproject.eventsync.model.SentimentAnalysis;
import pro.ephraimgibson.ibm.internproject.eventsync.service.EventService;
import pro.ephraimgibson.ibm.internproject.eventsync.service.FeedbackService;
import pro.ephraimgibson.ibm.internproject.eventsync.service.SentimentService;
import pro.ephraimgibson.ibm.internproject.eventsync.service.SentimentSummaryService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventRestController {
    private final EventService eventService;
    private final SentimentService sentimentService;
    private final SentimentSummaryService sentimentSummaryService;
    private final FeedbackService feedbackService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event savedEvent = eventService.addEvent(event);
        return ResponseEntity.ok(savedEvent);
    }

    @PostMapping("/{eventId}/feedback")
    public ResponseEntity<Feedback> createFeedback(@PathVariable Long eventId, @RequestBody Feedback feedback) {
        Event eventForFeedback = eventService.getSingleEvent(eventId);

        SentimentAnalysis sentimentResponse = sentimentService.getAISentimentAnalysis(feedback.getContent());

        feedback.setEvent(eventForFeedback);
        feedback.setFullSentimentAnalysis(sentimentResponse);
        Feedback savedFeedback = feedbackService.submitFeedback(feedback);

        return ResponseEntity.ok(savedFeedback);
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
