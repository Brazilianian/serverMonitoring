package mitit22kaf.serverMonitoring.service;

import mitit22kaf.serverMonitoring.entities.Task;
import mitit22kaf.serverMonitoring.repos.TaskRepo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public List<Task> getTasks(short classroomNumber, byte pcNumber) {
        return taskRepo.findByClassroomNumberAndPcNumber(classroomNumber, pcNumber);
    }

    public List<Task> getTasks(short classroomNumber) {
        return taskRepo.findByClassroomNumber(classroomNumber);
    }

    public Task createTask(Task task) {
        return taskRepo.save(task);
    }

    public Task editTask(Task task) {
        if (taskRepo.existsById(task.getId())) {
            return taskRepo.save(task);
        } else {
            throw new EntityExistsException("Task with id " + task.getId() + " does not exist");
        }
    }
}
