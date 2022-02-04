package mitit22kaf.serverMonitoring.rest;

import mitit22kaf.serverMonitoring.entities.NetworkHistoryOfClassroom;
import mitit22kaf.serverMonitoring.repos.NetworkHistoryClassroomRepo;
import mitit22kaf.serverMonitoring.service.ChartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
public class HistoryRestController {

    private final ChartService chartService;

    public HistoryRestController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping
    public ResponseEntity<?> getHistory() {
        try {
            List<NetworkHistoryOfClassroom> networkHistoryOfClassroomList = chartService.getHistory();
            return ResponseEntity.ok(networkHistoryOfClassroomList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/last-hour")
    public ResponseEntity<?> getHistoryLastHour() {
        try {
            System.out.println("start");
            List<NetworkHistoryOfClassroom> networkHistoryOfClassroomList = chartService.getHistoryLast20Minutes();
            System.out.println("end");
            return ResponseEntity.ok(networkHistoryOfClassroomList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
