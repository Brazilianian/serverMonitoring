package mitit22kaf.serverMonitoring.pojo.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cpu {
    private String cpuInfo;
    private int cpuLoad;
}
