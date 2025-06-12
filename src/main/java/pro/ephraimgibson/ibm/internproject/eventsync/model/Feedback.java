package pro.ephraimgibson.ibm.internproject.eventsync.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name="name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name="content")
    private String content;

    @Column(name="sentiment_score")
    private Double score;

//    @Enumerated(EnumType.STRING)
//    private String sentiment;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;



}
