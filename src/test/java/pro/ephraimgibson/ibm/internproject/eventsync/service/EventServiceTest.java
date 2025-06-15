package pro.ephraimgibson.ibm.internproject.eventsync.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.ephraimgibson.ibm.internproject.eventsync.data.EventRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Event;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository mockEventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    public void testAddEventShouldAddAnEventToTheRepo() {
        //Given
        Event event = createEvent();
        when(mockEventRepository.save(event)).thenReturn(event);

        //When
        Event result = eventService.addEvent(event);

        //Then
        assertEquals(event, result);
        verify(mockEventRepository).save(event);
    }

    @Test
    public void testRetrieveEventsInTheRepo() {
        //Given
        List<Event> events = createListOfEvents();
        when(mockEventRepository.findAll()).thenReturn(events);

        //When
        List<Event> repoEvents = eventService.getAllEvents();

        //Then
        assertEquals(events, repoEvents);
        assertEquals(2, repoEvents.size());
        verify(mockEventRepository).findAll();
    }

    @Test
    public void testToRetrieveASingleEventFromRepoWithTheID() {
        //Given
        Event event = createEvent();
        when(mockEventRepository.findById(1L)).thenReturn(Optional.of(event));

        //When
        Event result = eventService.getSingleEvent(1L);

        //Then
        assertEquals(event, result);
        verify(mockEventRepository).findById(1L);
    }

    @Test
    public void testFindEventByIdNotFoundException() {
        // Given
        when(mockEventRepository.findById(10L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> {
            eventService.getSingleEvent(10L);
        });
    }


    private Event createEvent() {
        return new Event(1L, "NBA all star night",
                "One night of friendly basketball with famous basketball players",
                "2025-09-19", "Los Angeles");
    }

    private List<Event> createListOfEvents() {
        Event event1 = new Event(2L, "Primavera Festival", "Spring festival with live concert", "2025-01-26", "Porto");
        Event event2 = new Event(3L, "Gravity Vilnius", "Lakeside Event", "2025-05-20", "Vilnius");

        return List.of(event1, event2);
    }



}
