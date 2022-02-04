package mitit22kaf.serverMonitoring.repos;

import mitit22kaf.serverMonitoring.entities.NetworkHistoryOfClassroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NetworkHistoryClassroomRepo extends JpaRepository<NetworkHistoryOfClassroom, Short> {
    boolean existsByNumberOfClassroom(Short number);

    List<NetworkHistoryOfClassroom> findByDateGreaterThan(Date date);

}
