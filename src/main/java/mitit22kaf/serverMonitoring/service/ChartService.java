package mitit22kaf.serverMonitoring.service;

import mitit22kaf.serverMonitoring.entities.AverageNetworkOfClassroom;
import mitit22kaf.serverMonitoring.entities.NetworkHistoryOfClassroom;
import mitit22kaf.serverMonitoring.repos.ComputerVariableDataRepo;
import mitit22kaf.serverMonitoring.repos.NetworkHistoryClassroomRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChartService {

    private final ComputerService computerService;
    private final ComputerVariableDataRepo computerVariableDataRepo;
    private final NetworkHistoryClassroomRepo nhcr;

    public ChartService(ComputerService computerService, ComputerVariableDataRepo computerVariableDataRepo,
                        NetworkHistoryClassroomRepo nhcr) {
        this.computerService = computerService;
        this.computerVariableDataRepo = computerVariableDataRepo;
        this.nhcr = nhcr;
    }


    public List<NetworkHistoryOfClassroom> saveNetworkHistoryOfClassroom(){
        List<NetworkHistoryOfClassroom> networkHistoryOfClassrooms = nhcr.findAll();
        for (Short i : computerService.getNumberOfClassrooms()) {
            if (networkHistoryOfClassrooms.stream().noneMatch(o -> o.getNumberOfClassroom() == i)){
                networkHistoryOfClassrooms.add(new NetworkHistoryOfClassroom(i, new ArrayList<>(), new ArrayList<>()));
            }
            float sumDownload = (float) computerService.getIpV4OfNumberOfClassroom(i)
                    .stream()
                    .mapToDouble(x -> computerVariableDataRepo.findByIpv4(x).getNetworkLoad())
                    .sum();

            float sumUpload = (float) computerService.getIpV4OfNumberOfClassroom(i)
                    .stream()
                    .mapToDouble(x -> computerVariableDataRepo.findByIpv4(x).getNetworkUpLoad())
                    .sum();

            NetworkHistoryOfClassroom networkHistoryOfClassroom = networkHistoryOfClassrooms
                    .stream()
                    .filter(y -> y.getNumberOfClassroom() == i)
                    .findFirst()
                    .get();

            networkHistoryOfClassroom.getAverageUpload().add(
                    new AverageNetworkOfClassroom(new Date(), sumUpload));

            networkHistoryOfClassroom.getAverageDownload().add(
                    new AverageNetworkOfClassroom(new Date(), sumDownload));


        }
        return nhcr.saveAll(networkHistoryOfClassrooms);
    }
}
