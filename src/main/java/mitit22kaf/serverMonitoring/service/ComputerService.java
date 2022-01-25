package mitit22kaf.serverMonitoring.service;

import mitit22kaf.serverMonitoring.entities.ComputerData;
import mitit22kaf.serverMonitoring.entities.ComputerVariableData;
import mitit22kaf.serverMonitoring.pojo.classroom.ClassroomResponse;
import mitit22kaf.serverMonitoring.pojo.classroom.ComputerClassroomResponse;
import mitit22kaf.serverMonitoring.pojo.classrooms.ClassroomsResponse;
import mitit22kaf.serverMonitoring.pojo.classrooms.ComputerClassroomsResponse;
import mitit22kaf.serverMonitoring.pojo.ComputerResponse;
import mitit22kaf.serverMonitoring.pojo.components.Cpu;
import mitit22kaf.serverMonitoring.pojo.components.Network;
import mitit22kaf.serverMonitoring.pojo.components.Ram;
import mitit22kaf.serverMonitoring.repos.ComputerDataRepo;
import mitit22kaf.serverMonitoring.repos.ComputerVariableDataRepo;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
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

    public ComputerResponse getComputerResponse(short classroomId, byte pcId) {

        ComputerResponse computerResponse;

        if (computerDataRepo.existsByNumberClassroomAndNumberPс(classroomId, pcId)) {
            computerResponse = new ComputerResponse();

            ComputerData computerData = computerDataRepo.findByNumberClassroomAndNumberPс(classroomId, pcId);
            ComputerVariableData computerVariableData = computerVariableDataRepo.findByIpv4(computerData.getIpv4());

            computerResponse.setIpv4(computerData.getIpv4());
            computerResponse.setMacAddress(computerData.getMacAddress());

            computerResponse.setNumberClassroom(classroomId);
            computerResponse.setNumberPc(pcId);

            computerResponse.setCpu(new Cpu(computerData.getCpuInfo(), computerVariableData.getCpuLoad()));
            computerResponse.setRam(new Ram(computerData.getRamInfo(), computerVariableData.getRamLoad()));
            computerResponse.setNetwork(new Network(computerVariableData.getNetworkLoad(), computerVariableData.getNetworkUpLoad()));

            computerResponse.setOsInfo(computerData.getOsInfo());
            computerResponse.setMotherboardInfo(computerData.getMotherboardInfo());

            computerResponse.setGpuInfo(computerData.getGpuInfo());
            computerResponse.setDisks(computerData.getDisks());

            computerResponse.setLoaded(computerData.isLoaded());
        } else {
            throw new EntityExistsException("There is no pc with number classroom " + classroomId + " and pc number " + pcId);
        }

        return computerResponse;
    }

    public List<ClassroomsResponse> getClassroomsResponse() {

        Map<Short, List<ComputerData>> computerDataByClassrooms = computerDataRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(ComputerData :: getNumberClassroom))
                .sorted(Comparator.comparing(ComputerData ::getNumberPс))
                .collect(Collectors.groupingBy(ComputerData::getNumberClassroom));

        List<ClassroomsResponse> classroomsResponses = new ArrayList<>();

        for (Map.Entry<Short, List<ComputerData>> computerDataList :
                computerDataByClassrooms.entrySet()) {

            ClassroomsResponse classroomsResponse = new ClassroomsResponse();
            classroomsResponse.setNumber(computerDataList.getKey());
            classroomsResponse.setSpeed((float) computerDataList.getValue()
                    .stream()
                    .mapToDouble(i -> {
                        classroomsResponse.getComputers().add(new ComputerClassroomsResponse(i.getNumberPс(), i.isLoaded()));

                        return computerVariableDataRepo.findByIpv4(i.getIpv4()).getNetworkLoad();
                    })
                    .sum());
            classroomsResponses.add(classroomsResponse);
        }

        return classroomsResponses;
    }

    public ClassroomResponse getClassroomResponse(short classroomNumber) {
        ClassroomResponse classroomResponse = new ClassroomResponse();

        if (computerDataRepo.existsByNumberClassroom(classroomNumber)) {
            classroomResponse.setClassroomNumber(classroomNumber);

            List<ComputerData> computerDataList =
                    computerDataRepo.findByNumberClassroom(classroomNumber)
                    .stream()
                            .sorted(Comparator.comparing(ComputerData::getNumberPс))
                            .collect(Collectors.toList());

            for (ComputerData computerData :
                 computerDataList) {
                ComputerVariableData computerVariableData = computerVariableDataRepo.findByIpv4(computerData.getIpv4());

                ComputerClassroomResponse computerClassroomResponse = new ComputerClassroomResponse();

                computerClassroomResponse.setPcNumber(computerData.getNumberPс());

                computerClassroomResponse.setNetwork(new Network(computerVariableData.getNetworkLoad(), computerVariableData.getNetworkUpLoad()));
                computerClassroomResponse.setCpu(new Cpu(computerData.getCpuInfo(), computerVariableData.getCpuLoad()));
                computerClassroomResponse.setRam(new Ram(computerData.getRamInfo(), computerVariableData.getRamLoad()));

                computerClassroomResponse.setIpv4(computerData.getIpv4());
                computerClassroomResponse.setMacAddress(computerData.getMacAddress());

                computerClassroomResponse.setOsInfo(computerData.getOsInfo());
                computerClassroomResponse.setMotherboardInfo(computerData.getMotherboardInfo());

                computerClassroomResponse.setGpuInfo(computerData.getGpuInfo());

                classroomResponse.getComputerClassroomResponse().add(computerClassroomResponse);
            }
        }
        return classroomResponse;
    }
}
