package mitit22kaf.serverMonitoring.service;

import mitit22kaf.serverMonitoring.entities.NetworkHistoryOfClassroom;
import mitit22kaf.serverMonitoring.repos.ComputerVariableDataRepo;
import mitit22kaf.serverMonitoring.repos.NetworkHistoryClassroomRepo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChartService {

    private final ComputerService computerService;
    private final ComputerVariableDataRepo computerVariableDataRepo;
    private final NetworkHistoryClassroomRepo networkHistoryClassroomRepo;

    public ChartService(ComputerService computerService, ComputerVariableDataRepo computerVariableDataRepo,
                        NetworkHistoryClassroomRepo networkHistoryClassroomRepo) {
        this.computerService = computerService;
        this.computerVariableDataRepo = computerVariableDataRepo;
        this.networkHistoryClassroomRepo = networkHistoryClassroomRepo;
    }

    public List<NetworkHistoryOfClassroom> saveNetworkHistoryOfClassroom(){
        List<NetworkHistoryOfClassroom> networkHistoryOfClassrooms = networkHistoryClassroomRepo.findAll();

        for (Short classroomNumber : computerService.getNumberOfClassrooms()) {
            if (networkHistoryOfClassrooms.stream()
                    .noneMatch(historyOfClassroom -> historyOfClassroom.getNumberOfClassroom() == classroomNumber)){
                networkHistoryOfClassrooms.add(new NetworkHistoryOfClassroom(classroomNumber));
            }

            float sumDownload = (float) computerService.getIpV4OfNumberOfClassroom(classroomNumber)
                    .stream()
                    .mapToDouble(x -> computerVariableDataRepo.findByIpv4(x).getNetworkLoad())
                    .sum();

            float sumUpload = (float) computerService.getIpV4OfNumberOfClassroom(classroomNumber)
                    .stream()
                    .mapToDouble(x -> computerVariableDataRepo.findByIpv4(x).getNetworkUpLoad())
                    .sum();

            NetworkHistoryOfClassroom networkHistoryOfClassroom = networkHistoryOfClassrooms
                    .stream()
                    .filter(y -> y.getNumberOfClassroom() == classroomNumber)
                    .findFirst()
                    .get();

            networkHistoryOfClassroom.setDate(new Date());

            networkHistoryOfClassroom.setAverageDownload(sumDownload);
            networkHistoryOfClassroom.setAverageUpload(sumUpload);

            networkHistoryClassroomRepo.save(networkHistoryOfClassroom);
        }
        return networkHistoryOfClassrooms;
    }

    public List<NetworkHistoryOfClassroom> getHistory() {
        return networkHistoryClassroomRepo.findAll();
    }

    public List<NetworkHistoryOfClassroom> getHistoryLast20Minutes() {
       return networkHistoryClassroomRepo
               .findByDateGreaterThan(
                       new Date(System.currentTimeMillis() - 1200 * 1000));
    }
}
