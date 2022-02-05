package mitit22kaf.serverMonitoring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private short classroomNumber;
    private short pcNumber;

    private String text;
    private LocalDateTime date;
    private byte priority;

    @Enumerated(EnumType.STRING)
    private Status status;
}
