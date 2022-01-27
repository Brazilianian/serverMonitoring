package mitit22kaf.serverMonitoring.pojo.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomResponse {
    private short classroomNumber;
    private List<ComputerResponse> computerClassroomResponse = new ArrayList<>();
}
