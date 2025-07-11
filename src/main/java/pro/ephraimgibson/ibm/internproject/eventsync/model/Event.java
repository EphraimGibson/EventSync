package pro.ephraimgibson.ibm.internproject.eventsync.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "Events")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {
    @Id
    @Column(name = "Event_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required and can not be empty")
    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Date")
    private String date;

    @Column(name="location")
    private String location;
}