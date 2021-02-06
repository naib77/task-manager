package com.naib.taskmanager.service;

import com.naib.taskmanager.dto.TaskDataDTO;
import com.naib.taskmanager.model.TaskStatus;
import com.naib.taskmanager.model.dao.Project;
import com.naib.taskmanager.model.dao.Task;
import com.naib.taskmanager.model.dao.User;
import com.naib.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(TaskDataDTO taskDataDTO){
        Task task = new Task();
        task.setDescription(taskDataDTO.getDescription());

        Project project = new Project();
        project.setId(taskDataDTO.getProject_id());
        task.setProject(project);
        task.setDue_date(taskDataDTO.getDue_date());
//        task.setStatus(TaskStatus.valueOf(taskDataDTO.getStatus()));
        TaskStatus status = TaskStatus.find(taskDataDTO.getStatus());
        task.setStatus(status);

        Set<User> users = new HashSet<>();
        User user = new User();
        user.setId(taskDataDTO.getUser_id());
        users.add(user);
        task.setUser(users);
        return taskRepository.save(task);

    }
}
