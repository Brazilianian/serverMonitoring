package mitit22kaf.serverMonitoring.rest;

import mitit22kaf.serverMonitoring.entities.Task;
import mitit22kaf.serverMonitoring.pojo.ErrorResponse;
import mitit22kaf.serverMonitoring.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskRestController {

    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<?> getTask(@RequestBody short classroomNumber,
                                     @RequestBody byte pcNumber) {
        try {
            List<Task> tasks = taskService.getTasks(classroomNumber, pcNumber);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            task = taskService.createTask(task);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> editTask(@RequestBody Task task) {
        try {
            task = taskService.editTask(task);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getTask(@RequestBody short classroomNumber) {
        try {
            List<Task> tasks = taskService.getTasks(classroomNumber);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
