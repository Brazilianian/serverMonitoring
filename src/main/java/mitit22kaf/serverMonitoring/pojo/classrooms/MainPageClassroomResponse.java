package mitit22kaf.serverMonitoring.pojo.classrooms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPageClassroomResponse {

    private short number;
    private float speed;
    private List<MainPageComputerResponse> computers = new ArrayList<>();

}
