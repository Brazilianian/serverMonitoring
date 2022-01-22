package mitit22kaf.serverMonitoring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComputerResponse {

    private byte number;
    private boolean isLoaded;
}
