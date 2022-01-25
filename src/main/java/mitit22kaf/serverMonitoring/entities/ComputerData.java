package mitit22kaf.serverMonitoring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ComputerData {

    @Id
    private String ipv4;
    private String macAddress;
    private byte numberPс;
    private short numberClassroom;

    private String cpuInfo;
    private String motherboardInfo;
    private String ramInfo;
    private String osInfo;

    @ElementCollection
    private List<String> gpuInfo = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<DiskInfo> disks = new ArrayList<>();

    private boolean isLoaded;
}
