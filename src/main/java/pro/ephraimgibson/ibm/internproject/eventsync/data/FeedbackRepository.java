package pro.ephraimgibson.ibm.internproject.eventsync.data;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.ephraimgibson.ibm.internproject.eventsync.model.Feedback;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    List<Feedback> findByEvent_Id(Long eventId);
}
