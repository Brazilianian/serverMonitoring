package mitit22kaf.serverMonitoring.rest;

import mitit22kaf.serverMonitoring.pojo.ErrorResponse;
import mitit22kaf.serverMonitoring.service.ComputerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/classroom")
public class ClassroomRestController {

    private final ComputerService computerService;

    public ClassroomRestController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(computerService.getClassroomsResponses());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Fatal error"));
        }
    }
}
