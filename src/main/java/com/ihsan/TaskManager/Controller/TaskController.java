package com.ihsan.TaskManager.Controller;

import com.ihsan.TaskManager.Dto.TaskRequest;
import com.ihsan.TaskManager.Dto.TaskResponse;
import com.ihsan.TaskManager.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("task")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request){
        TaskResponse response=taskService.createTask(request);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @GetMapping("tasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks(){
        List<TaskResponse> responses=taskService.getAllTasks();
        return new ResponseEntity<>(responses , HttpStatus.OK);
    }

    @GetMapping("task/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long taskId){
        TaskResponse response=taskService.findTaskById(taskId);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PutMapping("task/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long taskId , @RequestBody TaskRequest request){
        TaskResponse response=taskService.updateTask(taskId , request);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @DeleteMapping("task/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
        return new ResponseEntity<>("task deleted" , HttpStatus.OK);
    }
}
