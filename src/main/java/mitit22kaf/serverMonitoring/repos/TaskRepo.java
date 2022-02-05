package mitit22kaf.serverMonitoring.repos;

import mitit22kaf.serverMonitoring.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByClassroomNumberAndPcNumber(short classroomNumber, short pcNumber);

    List<Task> findByClassroomNumber(short classroomNumber);
}
