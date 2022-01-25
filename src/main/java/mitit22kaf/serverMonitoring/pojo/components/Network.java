package mitit22kaf.serverMonitoring.pojo.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Network {
    private float networkLoad;
    private float networkUpLoad;
}
