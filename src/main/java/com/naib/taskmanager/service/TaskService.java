package com.naib.taskmanager.service;

import com.naib.taskmanager.controller.TaskController;
import com.naib.taskmanager.dto.TaskDataDTO;
import com.naib.taskmanager.dto.TaskResponseDataDTO;
import com.naib.taskmanager.dto.TaskUpdateDataDTO;
import com.naib.taskmanager.model.Role;
import com.naib.taskmanager.model.TaskStatus;
import com.naib.taskmanager.model.dao.Project;
import com.naib.taskmanager.model.dao.Task;
import com.naib.taskmanager.model.dao.User;
import com.naib.taskmanager.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class TaskService {
    public static Logger LOG = LoggerFactory.getLogger(TaskService.class);
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(TaskDataDTO taskDataDTO) {
        Task task = new Task();
        task.setDescription(taskDataDTO.getDescription());

        if (taskDataDTO.getProject_id() != null) {
            Project project = new Project();
            project.setId(taskDataDTO.getProject_id());
            task.setProject(project);
        }
        task.setName(taskDataDTO.getName());
        task.setTaskDueDate(taskDataDTO.getDue_date());
        TaskStatus status = TaskStatus.find(taskDataDTO.getStatus());
        task.setStatus(status);

        Set<User> users = new HashSet<>();
        User user = new User();
        user.setId(taskDataDTO.getUser_id());
        users.add(user);
        task.setUser(users);
        return taskRepository.save(task);

    }

    public TaskResponseDataDTO updateTask(TaskUpdateDataDTO taskDataDTO, HttpServletRequest request) {
        User loggedInUser = null;
        Task task = null;
        TaskResponseDataDTO responseDataDTO = new TaskResponseDataDTO();
        if (request.isUserInRole(Role.ADMIN.name())) {
            loggedInUser = userService.loggedInUser(request);
            task = taskRepository.getById(taskDataDTO.getId());
        } else if (request.isUserInRole(Role.USER.name())) {
            loggedInUser = userService.loggedInUser(request);
            task = taskRepository.getByIdAndUser(taskDataDTO.getId(), loggedInUser);
        }
         if (task != null){
             if (task.getStatus().getValue().equalsIgnoreCase(TaskStatus.CLOSED.getValue())) {
                 responseDataDTO.setMessage("This task is already closed !!");
             } else {
                 if (taskDataDTO.getDescription() != null) {
                     task.setDescription(taskDataDTO.getDescription());
                 }
                 if (taskDataDTO.getStatus() != null) {
                     task.setStatus(TaskStatus.find(taskDataDTO.getStatus()));
                 }
                 if (taskDataDTO.getDue_date() != null) {
                     task.setTaskDueDate(taskDataDTO.getDue_date());
                 }
                 taskRepository.save(task);
                 responseDataDTO.setMessage("Task Updated Successfully");
                 LOG.info("Task with task_id: {} is updated by {}", taskDataDTO.getId(),loggedInUser.getUsername());
             }
         }else {
             responseDataDTO.setMessage("Task is not found for this user");
         }

        return responseDataDTO;
    }

    public TaskResponseDataDTO getTaskById(Integer taskId, HttpServletRequest request) {
        Task task = null;
        TaskResponseDataDTO taskResponseDataDTO = new TaskResponseDataDTO();
        if (request.isUserInRole(Role.ADMIN.name())) {
            task = taskRepository.getById(taskId);
        } else if (request.isUserInRole(Role.USER.name())) {
            User loggedInUser = userService.loggedInUser(request);
            task = taskRepository.getByIdAndUser(taskId, loggedInUser);
        }
        if (task == null){
            taskResponseDataDTO.setMessage("No task available!");
        }else {
            taskResponseDataDTO = modelMapper.map(task, TaskResponseDataDTO.class);
        }
        return taskResponseDataDTO;
    }

    public List<TaskResponseDataDTO> getAllTaskByUserId(Integer userId){
        List<Task> taskList =taskRepository.findAllByUser_id(userId);
        return getTaskResponseDataDTOList(taskList);
    }

    public List<TaskResponseDataDTO> getAllTaskByProjectId(Integer projectId, HttpServletRequest request) {
        List<Task> taskList = null;
        if (request.isUserInRole(Role.ADMIN.name())) {
            taskList = taskRepository.findAllByProject_Id(projectId);
        } else if (request.isUserInRole(Role.USER.name())) {
            User loggedInUser = userService.loggedInUser(request);
            taskList = taskRepository.findAllByProject_IdAndUser(projectId, loggedInUser);
        }
        return getTaskResponseDataDTOList(taskList);
    }

    public List<TaskResponseDataDTO> getAllTaskByStatus(String status, HttpServletRequest request) {
        List<Task> taskList = null;
        TaskStatus taskStatus = TaskStatus.find(status);
        if (request.isUserInRole(Role.ADMIN.name())) {
            taskList = taskRepository.findAllByStatus(taskStatus);
        } else if (request.isUserInRole(Role.USER.name())) {
            User loggedInUser = userService.loggedInUser(request);
            taskList = taskRepository.findAllByStatusAndUser(taskStatus, loggedInUser);
        }
        return getTaskResponseDataDTOList(taskList);
    }
    public List<TaskResponseDataDTO> getAllExpiredTask(HttpServletRequest request) {
        List<Task> taskList = null;
        if (request.isUserInRole(Role.ADMIN.name())) {
            taskList = taskRepository.findAllByTaskDueDateBefore(new Date());
        } else if (request.isUserInRole(Role.USER.name())) {
            User loggedInUser = userService.loggedInUser(request);
            taskList = taskRepository.findAllByTaskDueDateBeforeAndUser(new Date(), loggedInUser);
        }
        return getTaskResponseDataDTOList(taskList);
    }

    private List<TaskResponseDataDTO> getTaskResponseDataDTOList(List<Task> taskList){
        List<TaskResponseDataDTO> taskResponseDataDTOList = new ArrayList<>();
        if (taskList.size()>0){
            taskList.forEach(task -> {
                TaskResponseDataDTO taskDataDTO = new TaskResponseDataDTO();
                taskDataDTO.setDescription(task.getDescription());
                taskDataDTO.setDue_date(task.getTaskDueDate());
                taskDataDTO.setStatus(task.getStatus().getValue());
                taskResponseDataDTOList.add(taskDataDTO);
            });
        }
        return taskResponseDataDTOList;
    }

}
