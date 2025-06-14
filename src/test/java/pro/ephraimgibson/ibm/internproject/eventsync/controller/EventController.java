package pro.ephraimgibson.ibm.internproject.eventsync.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import pro.ephraimgibson.ibm.internproject.eventsync.controllers.EventRestController;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Event;

//@SpringBootTest
//public class EventController {
//
//    private MockMvc mockMvc;
//
//    @Test
//    public void canCreateEvent(){
//
//
//        Event event = new Event(1L,"NBA all star night",
//                "One night of friendly basketball with famous basketball players",
//                "2025-09-19", "Los Angeles");
//
//        ResponseEntity<Event> savedEvent = eventRestController.createEvent(event);
//
//
//
//    }
//}
