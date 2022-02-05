package mitit22kaf.serverMonitoring.rest;

import mitit22kaf.serverMonitoring.pojo.ErrorResponse;
import mitit22kaf.serverMonitoring.pojo.classroom.ClassroomResponse;
import mitit22kaf.serverMonitoring.pojo.classroom.VariableComputerResponse;
import mitit22kaf.serverMonitoring.service.ComputerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.List;

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
            return ResponseEntity.ok(computerService.getMainPageClassroomResponse());
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

    @GetMapping("/{classroomId}/variable")
    public ResponseEntity<?> getVariableDataClassroom(@PathVariable short classroomId) {
        try {
            List<VariableComputerResponse> variableComputerResponseList = computerService.getVariableComputerResponse(classroomId);
            return ResponseEntity.ok(variableComputerResponseList);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Fatal error"));
        }
    }

    @PutMapping("/{oldClassroomNumber}")
    public ResponseEntity<?> editClassroomNumber(@PathVariable short oldClassroomNumber,
                                                 @RequestBody short newClassroomNumber) {
        try {
            computerService.editClassroomNumber(oldClassroomNumber, newClassroomNumber);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(newClassroomNumber);
    }

    @PutMapping("/{classroomNumber}/pc/{oldPcNumber}")
    public ResponseEntity<?> editComputerNumber(@PathVariable short classroomNumber,
                                                @PathVariable byte oldPcNumber,
                                                @RequestBody byte newPcNumber) {
        try {
            computerService.editPcNumber(classroomNumber, oldPcNumber, newPcNumber);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(newPcNumber);
    }
}
