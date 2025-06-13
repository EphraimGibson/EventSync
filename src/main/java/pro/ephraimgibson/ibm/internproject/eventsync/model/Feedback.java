package pro.ephraimgibson.ibm.internproject.eventsync.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

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

    @Column(name="AI_Analysis")
    private String topSentiment;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Transient
    private SentimentAnalysis SentimentAnalysis;

    public void setSentimentAnalysis(SentimentAnalysis SentimentAnalysis) {
        this.SentimentAnalysis = SentimentAnalysis;
        setTopSentiment(SentimentAnalysis.results().get(0).getLabel());
    }
}
