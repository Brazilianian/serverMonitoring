package mitit22kaf.serverMonitoring.job;

import mitit22kaf.serverMonitoring.service.ChartService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ChartJob {

    private final ChartService chartService;

    public ChartJob(ChartService chartService) {
        this.chartService = chartService;
    }

    @Scheduled(fixedRate = 5000L)
    public void getClassrooms(){
        chartService.saveNetworkHistoryOfClassroom();
    }
}
