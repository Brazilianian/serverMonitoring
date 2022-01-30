package mitit22kaf.serverMonitoring.rest;

import mitit22kaf.serverMonitoring.service.ComputerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;

@RestController
@RequestMapping("/api/v1/classroom")
public class AdminRestController {

    private final ComputerService computerService;

    public AdminRestController(ComputerService computerService) {
        this.computerService = computerService;
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
