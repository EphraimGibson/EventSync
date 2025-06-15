package pro.ephraimgibson.ibm.internproject.eventsync.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.ephraimgibson.ibm.internproject.eventsync.controllers.EventRestController;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Event;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;
import pro.ephraimgibson.ibm.internproject.eventsync.service.EventService;
import pro.ephraimgibson.ibm.internproject.eventsync.service.FeedbackService;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    EventService mockEventService;

    @Mock
    FeedbackService mockFeedbackService;

    @InjectMocks
    EventRestController eventRestController;

    @Test
    public void testShouldCreateFeedback_ValidEventId_ReturnOk() {
        //Given
        Long eventId = 1L;
        Event mockEvent = createEvent();
        Feedback inputFeedback = createFeedback();

        Feedback savedFeedback = createFeedback();
        savedFeedback.setEvent(mockEvent);
        savedFeedback.setId(1L);
        savedFeedback.setTopSentiment("POSITIVE");


        when(mockEventService.getSingleEvent(eventId)).thenReturn(mockEvent);
        when(mockFeedbackService.submitFeedback(any(Feedback.class))).thenReturn(savedFeedback);

        // When
        ResponseEntity<Feedback> response = eventRestController.createFeedback(eventId, inputFeedback);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedFeedback, response.getBody());
        Assertions.assertNotNull(response.getBody());

        verify(mockEventService).getSingleEvent(eventId);
        verify(mockFeedbackService).submitFeedback(inputFeedback);
    }

    @Test
    public void testShouldAddEventBeforeCreateFeedback() {
        //Given
        Long eventId = 1L;
        Event mockEvent = createEvent();
        Feedback inputFeedback = createFeedback();

        when(mockEventService.getSingleEvent(eventId)).thenReturn(mockEvent);
        when(mockFeedbackService.submitFeedback(any(Feedback.class))).thenReturn(inputFeedback);

        //Then
        eventRestController.createFeedback(eventId,inputFeedback);

        assertEquals(mockEvent, inputFeedback.getEvent());
    }

    private Event createEvent() {
        return new Event(1L, "NBA all star night",
                "One night of friendly basketball with famous basketball players",
                "2025-09-19", "Los Angeles");
    }

    private Feedback createFeedback() {
        Feedback feedback = new Feedback();
        feedback.setContent("Amazing event!");
        return feedback;
    }
}



