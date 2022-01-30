package mitit22kaf.serverMonitoring.pojo.classrooms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mitit22kaf.serverMonitoring.pojo.components.Cpu;
import mitit22kaf.serverMonitoring.pojo.components.Network;
import mitit22kaf.serverMonitoring.pojo.components.Ram;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariableComputerResponse {
    private short classroomNumber;
    private byte pcNumber;

    private Cpu cpu;
    private Network network;
    private Ram ram;

    private boolean isLoaded;
}
