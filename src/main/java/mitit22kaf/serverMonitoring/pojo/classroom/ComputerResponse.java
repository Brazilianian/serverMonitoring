package mitit22kaf.serverMonitoring.pojo.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mitit22kaf.serverMonitoring.entities.DiskInfo;
import mitit22kaf.serverMonitoring.pojo.components.Cpu;
import mitit22kaf.serverMonitoring.pojo.components.Network;
import mitit22kaf.serverMonitoring.pojo.components.Ram;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComputerResponse {

    private String ipv4;
    private String macAddress;

    private byte numberPc;
    private short numberClassroom;

    private Cpu cpu;
    private Ram ram;
    private Network network;

    private String osInfo;
    private String motherboardInfo;

    private List<String> gpuInfo = new ArrayList<>();
    private List<DiskInfo> disks = new ArrayList<>();

    private boolean isLoaded;
}
