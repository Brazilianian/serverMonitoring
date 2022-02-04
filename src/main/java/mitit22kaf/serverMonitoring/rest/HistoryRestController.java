package mitit22kaf.serverMonitoring.rest;

import mitit22kaf.serverMonitoring.entities.NetworkHistoryOfClassroom;
import mitit22kaf.serverMonitoring.repos.NetworkHistoryClassroomRepo;
import mitit22kaf.serverMonitoring.service.ChartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
            Map<Short, List<NetworkHistoryOfClassroom>> historyLast20Minutes = chartService.getHistoryLast20Minutes();
            return ResponseEntity.ok(historyLast20Minutes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
