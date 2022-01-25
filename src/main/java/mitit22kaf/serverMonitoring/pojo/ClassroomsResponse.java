package mitit22kaf.serverMonitoring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomsResponse {

    private short number;
    private float speed;
    private List<ComputerClassroomResponse> computers = new ArrayList<>();

}
