package mitit22kaf.serverMonitoring.repos;

import mitit22kaf.serverMonitoring.entities.ComputerVariableData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComputerVariableDataRepo extends JpaRepository<ComputerVariableData, String> {
    ComputerVariableData findByIpv4(String ipv4);

}
