package pro.ephraimgibson.ibm.internproject.eventsync.data;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
