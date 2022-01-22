package mitit22kaf.serverMonitoring.repos;

import mitit22kaf.serverMonitoring.entities.DiskInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DiskInfoRepo extends CrudRepository<DiskInfo,Long> {
}
