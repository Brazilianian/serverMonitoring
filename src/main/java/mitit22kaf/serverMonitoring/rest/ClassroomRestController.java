package mitit22kaf.serverMonitoring.rest;

import mitit22kaf.serverMonitoring.pojo.ErrorResponse;
import mitit22kaf.serverMonitoring.pojo.classroom.ClassroomResponse;
import mitit22kaf.serverMonitoring.service.ComputerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;

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
            return ResponseEntity.ok(computerService.getClassroomsResponse());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Fatal error"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClassroom(@PathVariable short id) {
        try {
            ClassroomResponse classroomResponse = computerService.getClassroomResponse(id);
            return ResponseEntity.ok(classroomResponse);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Fatal error"));
        }
    }
}
