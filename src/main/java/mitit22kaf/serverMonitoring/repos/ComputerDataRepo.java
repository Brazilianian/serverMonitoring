package mitit22kaf.serverMonitoring.repos;

import mitit22kaf.serverMonitoring.entities.ComputerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ComputerDataRepo extends JpaRepository<ComputerData,String> {
}
