package mitit22kaf.serverMonitoring.rest;

import mitit22kaf.serverMonitoring.entities.NetworkHistoryOfClassroom;
import mitit22kaf.serverMonitoring.service.ChartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            List<NetworkHistoryOfClassroom> networkHistoryOfClassroomList
                    = chartService.getHistory().stream()
                    .sorted(Comparator.comparing(NetworkHistoryOfClassroom::getDate))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(networkHistoryOfClassroomList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/last-hour")
    public ResponseEntity<?> getHistoryLastHour() {
        try {
            Map<Short, List<NetworkHistoryOfClassroom>> historyLast20Minutes
                    = chartService.getHistoryLast20Minutes();
            return ResponseEntity.ok(historyLast20Minutes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value="/period/{period}")
    public ResponseEntity<?> setPeriod(@PathVariable Long period) {
        if (period < 0) {
            return ResponseEntity.badRequest().body(period);
        }
        ChartService.period = period * 60;
        return ResponseEntity.ok(ChartService.period);
    }
}
