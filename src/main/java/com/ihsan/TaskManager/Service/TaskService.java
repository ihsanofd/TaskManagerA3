package com.ihsan.TaskManager.Service;


import com.ihsan.TaskManager.Dto.TaskRequest;
import com.ihsan.TaskManager.Dto.TaskResponse;

import java.util.List;


public interface TaskService {


    TaskResponse createTask(TaskRequest request);

    List<TaskResponse> getAllTasks();

    TaskResponse findTaskById(Long taskId);

    TaskResponse updateTask(Long taskId, TaskRequest request);

    String deleteTask(Long taskId);
}
