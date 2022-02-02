package mitit22kaf.serverMonitoring.repos;

import mitit22kaf.serverMonitoring.entities.NetworkHistoryOfClassroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkHistoryClassroomRepo extends JpaRepository<NetworkHistoryOfClassroom, Short> {
    boolean existsByNumberOfClassroom(Short number);
}
