package pro.ephraimgibson.ibm.internproject.eventsync.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
