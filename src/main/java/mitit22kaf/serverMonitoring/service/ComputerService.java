package mitit22kaf.serverMonitoring.service;

import mitit22kaf.serverMonitoring.entities.ComputerData;
import mitit22kaf.serverMonitoring.entities.ComputerVariableData;
import mitit22kaf.serverMonitoring.pojo.classroom.ClassroomResponse;
import mitit22kaf.serverMonitoring.pojo.classroom.ComputerResponse;
import mitit22kaf.serverMonitoring.pojo.classroom.VariableComputerResponse;
import mitit22kaf.serverMonitoring.pojo.classrooms.MainPageClassroomResponse;
import mitit22kaf.serverMonitoring.pojo.classrooms.MainPageComputerResponse;
import mitit22kaf.serverMonitoring.pojo.components.Cpu;
import mitit22kaf.serverMonitoring.pojo.components.Network;
import mitit22kaf.serverMonitoring.pojo.components.Ram;
import mitit22kaf.serverMonitoring.repos.ComputerDataRepo;
import mitit22kaf.serverMonitoring.repos.ComputerVariableDataRepo;
import mitit22kaf.serverMonitoring.repos.NetworkHistoryClassroomRepo;
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
    private final NetworkHistoryClassroomRepo nhcr;

    public ComputerService(ComputerDataRepo computerDataRepo, ComputerVariableDataRepo computerVariableDataRepo,
                           NetworkHistoryClassroomRepo nhcr) {
        this.computerDataRepo = computerDataRepo;
        this.computerVariableDataRepo = computerVariableDataRepo;
        this.nhcr = nhcr;
    }

    public List<MainPageClassroomResponse> getMainPageClassroomResponse() {

        Map<Short, List<ComputerData>> computerDataByClassrooms = computerDataRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(ComputerData::getNumberClassroom))
                .sorted(Comparator.comparing(ComputerData::getNumberPс))
                .collect(Collectors.groupingBy(ComputerData::getNumberClassroom));

        List<MainPageClassroomResponse> mainPageClassroomResponseList = new ArrayList<>();

        for (Map.Entry<Short, List<ComputerData>> computerDataList :
                computerDataByClassrooms.entrySet()) {

            MainPageClassroomResponse mainPageClassroomResponse = new MainPageClassroomResponse();
            mainPageClassroomResponse.setNumber(computerDataList.getKey());
            mainPageClassroomResponse.setSpeed((float) computerDataList.getValue()
                    .stream()
                    .mapToDouble(i -> {
                        mainPageClassroomResponse.getComputers().add(new MainPageComputerResponse(i.getNumberPс(), i.isLoaded()));

                        return computerVariableDataRepo.findByIpv4(i.getIpv4()).getNetworkLoad();
                    })
                    .sum());
            mainPageClassroomResponseList.add(mainPageClassroomResponse);
        }

        return mainPageClassroomResponseList;
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

                ComputerResponse computerResponse = new ComputerResponse();

                computerResponse.setNumberPc(computerData.getNumberPс());

                computerResponse.setNetwork(new Network(computerVariableData.getNetworkLoad(), computerVariableData.getNetworkUpLoad()));
                computerResponse.setCpu(new Cpu(computerData.getCpuInfo(), computerVariableData.getCpuLoad()));
                computerResponse.setRam(new Ram(computerData.getRamInfo(), computerVariableData.getRamLoad()));

                computerResponse.setIpv4(computerData.getIpv4());
                computerResponse.setMacAddress(computerData.getMacAddress());

                computerResponse.setOsInfo(computerData.getOsInfo());
                computerResponse.setMotherboardInfo(computerData.getMotherboardInfo());

                computerResponse.setGpuInfo(computerData.getGpuInfo());
                computerResponse.setDisks(computerData.getDisks());
                computerResponse.setLoaded(computerData.isLoaded());

                classroomResponse.getComputerClassroomResponse().add(computerResponse);
            }
        }
        classroomResponse.setComputerClassroomResponse(classroomResponse.getComputerClassroomResponse().stream()
                .sorted(Comparator.comparing(ComputerResponse :: getNumberPc)).collect(Collectors.toList()));
        return classroomResponse;
    }

    public List<VariableComputerResponse> getVariableComputerResponse(short classroomId) {

        List<VariableComputerResponse> variableComputerResponseList = new ArrayList<>();

        List<ComputerData> computerDataList = computerDataRepo.findByNumberClassroom(classroomId);

        for (ComputerData computerData :
                computerDataList) {
            ComputerVariableData computerVariableData = computerVariableDataRepo.findByIpv4(computerData.getIpv4());

            VariableComputerResponse variableComputerResponse = new VariableComputerResponse();

            variableComputerResponse.setPcNumber(computerData.getNumberPс());
            variableComputerResponse.setClassroomNumber(classroomId);

            variableComputerResponse.setCpu(new Cpu(computerData.getCpuInfo(), computerVariableData.getCpuLoad()));
            variableComputerResponse.setNetwork(new Network(computerVariableData.getNetworkLoad(), computerVariableData.getNetworkUpLoad()));
            variableComputerResponse.setRam(new Ram(computerData.getRamInfo(), computerVariableData.getRamLoad()));

            variableComputerResponse.setLoaded(computerData.isLoaded());

            variableComputerResponseList.add(variableComputerResponse);
        }
            variableComputerResponseList = variableComputerResponseList.stream()
                    .sorted(Comparator.comparing(VariableComputerResponse :: getPcNumber))
                    .collect(Collectors.toList());
        return variableComputerResponseList;
    }

    public void editClassroomNumber(short oldNumber, short newNumber) {
        if (!computerDataRepo.existsByNumberClassroom(oldNumber)) {
            throw new EntityExistsException("There is no one classroom with number " + oldNumber);
        }
        
        List<ComputerData> computerDataList = computerDataRepo.findByNumberClassroom(oldNumber);

        for (ComputerData computerData :
                computerDataList) {
            computerData.setNumberClassroom(newNumber);
            computerDataRepo.save(computerData);
        }
    }

    public void editPcNumber(short classroomNumber, byte oldPcNumber, byte newPcNumber) {
        if (!computerDataRepo.existsByNumberClassroom(classroomNumber)) {
            throw new EntityExistsException("There is no one classroom with number " + classroomNumber);
        }

        List<ComputerData> computerDataList = computerDataRepo.findByNumberClassroom(classroomNumber);

        boolean presentOldPcNumber = false;
        for (ComputerData computerData :
                computerDataList) {
            if (computerData.getNumberPс() == oldPcNumber) {
                computerData.setNumberPс(newPcNumber);
                computerDataRepo.save(computerData);
                presentOldPcNumber = true;
            }
        }

        if (!presentOldPcNumber) {
            throw new EntityExistsException("There is no one pc with number " + oldPcNumber + " in classroom " + classroomNumber);
        }
    }

    public List<Short> getNumberOfClassrooms(){
        return computerDataRepo.findAllNumberOfClassrooms();
    }

    public List<String> getIpV4OfNumberOfClassroom(Short number){
        return computerDataRepo.findAllIpv4ByNumberOfClassroom(number);
    }


}
