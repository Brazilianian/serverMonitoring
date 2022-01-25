package mitit22kaf.serverMonitoring.pojo.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ram {
    private String ramInfo;
    private double ramLoad;
}
