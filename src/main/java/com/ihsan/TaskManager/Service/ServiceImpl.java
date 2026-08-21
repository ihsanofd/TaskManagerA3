package com.ihsan.TaskManager.Service;

import com.ihsan.TaskManager.Dto.TaskRequest;
import com.ihsan.TaskManager.Dto.TaskResponse;
import com.ihsan.TaskManager.Entity.Task;
import com.ihsan.TaskManager.Exception.TaskNotFoundException;
import com.ihsan.TaskManager.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceImpl implements TaskService {

   @Autowired
   private TaskRepository taskRepository;
    @Override
    public TaskResponse createTask(TaskRequest request) {
        Task task=new Task();


        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        Task saved=taskRepository.save(task);
        return mapToResponse(saved);
    }



    @Override
    public List<TaskResponse> getAllTasks() {


        List<Task> tasks=taskRepository.findAll();
        List<TaskResponse> responses=new ArrayList<>();

        for (Task task:tasks){

            responses.add(mapToResponse(task));
        }
        return responses;
    }



    @Override
    public TaskResponse findTaskById(Long taskId) {

        Task task=taskRepository.findById(taskId).orElseThrow
                (()->new TaskNotFoundException("Task not found with :"+ taskId));

        return mapToResponse(task);
    }



    @Override
    public TaskResponse updateTask(Long taskId, TaskRequest request) {

        Task task=taskRepository.findById(taskId).
                orElseThrow(()->new TaskNotFoundException("Task not found with :"+ taskId));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        Task saved=taskRepository.save(task);

        return mapToResponse(saved);

    }

    @Override
    public String deleteTask(Long taskId) {

        Task task=taskRepository.findById(taskId).
                orElseThrow(()->new TaskNotFoundException("Task not found with :"+ taskId));
        taskRepository.delete(task);
      return "task deleted";
    }


    public TaskResponse mapToResponse(Task task){

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt());
    }
}
