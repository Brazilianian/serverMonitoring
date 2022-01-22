package mitit22kaf.serverMonitoring.service;

import mitit22kaf.serverMonitoring.entities.ComputerData;
import mitit22kaf.serverMonitoring.pojo.ClassroomsResponse;
import mitit22kaf.serverMonitoring.pojo.ComputerResponse;
import mitit22kaf.serverMonitoring.repos.ComputerDataRepo;
import mitit22kaf.serverMonitoring.repos.ComputerVariableDataRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ComputerService {

    private final ComputerDataRepo computerDataRepo;
    private final ComputerVariableDataRepo computerVariableDataRepo;

    public ComputerService(ComputerDataRepo computerDataRepo, ComputerVariableDataRepo computerVariableDataRepo) {
        this.computerDataRepo = computerDataRepo;
        this.computerVariableDataRepo = computerVariableDataRepo;
    }

    public List<ClassroomsResponse> getClassroomsResponses() {

        Map<Short, List<ComputerData>> computerDataByClassrooms = computerDataRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(ComputerData :: getNumberClassroom))
                .sorted(Comparator.comparing(ComputerData :: getNumberPC))
                .collect(Collectors.groupingBy(ComputerData::getNumberClassroom));

        List<ClassroomsResponse> classroomsResponses = new ArrayList<>();

        for (Map.Entry<Short, List<ComputerData>> computerDataList :
                computerDataByClassrooms.entrySet()) {

            ClassroomsResponse classroomsResponse = new ClassroomsResponse();
            classroomsResponse.setNumber(computerDataList.getKey());
            classroomsResponse.setSpeed((float) computerDataList.getValue()
                    .stream()
                    .mapToDouble(i -> {
                        classroomsResponse.getComputers().add(new ComputerResponse(i.getNumberPC(), i.isLoaded()));

                        return computerVariableDataRepo.findByIpv4(i.getIpv4()).getNetworkLoad();
                    })
                    .sum());
            classroomsResponses.add(classroomsResponse);
        }

        return classroomsResponses;
    }
}
