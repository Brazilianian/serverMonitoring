package mitit22kaf.serverMonitoring.repos;

import mitit22kaf.serverMonitoring.entities.ComputerVariableData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputerVariableDataRepo extends JpaRepository<ComputerVariableData, String> {
    ComputerVariableData findByIpv4(String ipv4);

}
