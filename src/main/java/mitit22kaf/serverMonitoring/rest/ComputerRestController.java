package mitit22kaf.serverMonitoring.rest;

import mitit22kaf.serverMonitoring.pojo.ComputerResponse;
import mitit22kaf.serverMonitoring.service.ComputerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ComputerRestController {

    private final ComputerService computerService;

    public ComputerRestController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/classroom/{classroomId}/pc/{pcId}")
    public ResponseEntity<?> getComputer(@PathVariable short classroomId,
                                         @PathVariable byte pcId) {

        try {
            ComputerResponse computerResponse = computerService.getComputerResponse(classroomId, pcId);
            return ResponseEntity.ok(computerResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
