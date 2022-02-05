package mitit22kaf.serverMonitoring.service;

import mitit22kaf.serverMonitoring.entities.NetworkHistoryOfClassroom;
import mitit22kaf.serverMonitoring.repos.ComputerVariableDataRepo;
import mitit22kaf.serverMonitoring.repos.NetworkHistoryClassroomRepo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChartService {

    private final ComputerService computerService;
    private final ComputerVariableDataRepo computerVariableDataRepo;
    private final NetworkHistoryClassroomRepo networkHistoryClassroomRepo;

    //період часу для графіку. 1200с. = 20хв. Поверне останні 20 хв.
    public static long period = 1200;

    public ChartService(ComputerService computerService, ComputerVariableDataRepo computerVariableDataRepo,
                        NetworkHistoryClassroomRepo networkHistoryClassroomRepo) {
        this.computerService = computerService;
        this.computerVariableDataRepo = computerVariableDataRepo;
        this.networkHistoryClassroomRepo = networkHistoryClassroomRepo;
    }

    public List<NetworkHistoryOfClassroom> saveNetworkHistoryOfClassroom(){
        List<NetworkHistoryOfClassroom> networkHistoryOfClassrooms = networkHistoryClassroomRepo.findAll();

        for (Short classroomNumber : computerService.getNumberOfClassrooms()) {

            float sumDownload = (float) computerService.getIpV4OfNumberOfClassroom(classroomNumber)
                    .stream()
                    .mapToDouble(x -> computerVariableDataRepo.findByIpv4(x).getNetworkLoad())
                    .sum();

            float sumUpload = (float) computerService.getIpV4OfNumberOfClassroom(classroomNumber)
                    .stream()
                    .mapToDouble(x -> computerVariableDataRepo.findByIpv4(x).getNetworkUpLoad())
                    .sum();

            NetworkHistoryOfClassroom networkHistoryOfClassroom = new NetworkHistoryOfClassroom(classroomNumber, new Date());

            networkHistoryOfClassroom.setAverageDownload(sumDownload);
            networkHistoryOfClassroom.setAverageUpload(sumUpload);

            networkHistoryClassroomRepo.save(networkHistoryOfClassroom);
        }
        return networkHistoryOfClassrooms;
    }

    public List<NetworkHistoryOfClassroom> getHistory() {
        return networkHistoryClassroomRepo.findAll();
    }

    public Map<Short, List<NetworkHistoryOfClassroom>> getHistoryLast20Minutes() {
       return networkHistoryClassroomRepo.findByDateGreaterThan(new Date(System.currentTimeMillis() - period * 1000))
               .stream()
               .sorted(Comparator.comparing(NetworkHistoryOfClassroom::getDate))
               .collect(Collectors.groupingBy(NetworkHistoryOfClassroom :: getNumberOfClassroom));
    }
}
