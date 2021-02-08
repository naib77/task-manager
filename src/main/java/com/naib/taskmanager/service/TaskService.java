package com.naib.taskmanager.service;

import com.naib.taskmanager.dto.TaskDataDTO;
import com.naib.taskmanager.dto.TaskResponseDataDTO;
import com.naib.taskmanager.model.Role;
import com.naib.taskmanager.model.TaskStatus;
import com.naib.taskmanager.model.dao.Project;
import com.naib.taskmanager.model.dao.Task;
import com.naib.taskmanager.model.dao.User;
import com.naib.taskmanager.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class TaskService {

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

    public Task updateTask(TaskDataDTO taskDataDTO) {
        Task task = taskRepository.getOne(taskDataDTO.getId());
        if (task.getStatus().getValue().equalsIgnoreCase(TaskStatus.CLOSED.getValue())) {
            return task;
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
        }
        return task;
    }

    public TaskResponseDataDTO getTaskById(Integer taskId, HttpServletRequest request) {
        Task task = null;
        TaskResponseDataDTO taskResponseDataDTO = new TaskResponseDataDTO();
        if (request.isUserInRole(Role.ADMIN.name())) {
            task = taskRepository.getOne(taskId);
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

    public List<TaskDataDTO> getAllTaskByUserId(Integer userId){
        List<Task> taskList =taskRepository.findAllByUser_id(userId);
        List<TaskDataDTO> taskDataDTOList = new ArrayList<>();
        taskList.forEach(task -> {
            TaskDataDTO taskDataDTO = new TaskDataDTO();
            taskDataDTO.setDescription(task.getDescription());
            taskDataDTO.setDue_date(task.getTaskDueDate());
            taskDataDTO.setStatus(task.getStatus().getValue());
            taskDataDTOList.add(taskDataDTO);
        });
        return taskDataDTOList;
    }

    public List<TaskDataDTO> getAllTaskByProjectId(Integer projectId, HttpServletRequest request) {
        List<Task> taskList = null;
        if (request.isUserInRole(Role.ADMIN.name())) {
            taskList = taskRepository.findAllByProject_Id(projectId);
        } else if (request.isUserInRole(Role.USER.name())) {
            User loggedInUser = userService.loggedInUser(request);
            taskList = taskRepository.findAllByProject_IdAndUser(projectId, loggedInUser);
        }
        List<TaskDataDTO> taskDataDTOList = new ArrayList<>();
        taskList.forEach(task -> {
            TaskDataDTO taskDataDTO = new TaskDataDTO();
            taskDataDTO.setDescription(task.getDescription());
            taskDataDTO.setDue_date(task.getTaskDueDate());
            taskDataDTO.setStatus(task.getStatus().getValue());
            taskDataDTOList.add(taskDataDTO);
        });
        return taskDataDTOList;
    }

    public List<TaskDataDTO> getAllTaskByStatus(String status, HttpServletRequest request) {
        List<Task> taskList = null;
        TaskStatus taskStatus = TaskStatus.find(status);
        if (request.isUserInRole(Role.ADMIN.name())) {
            taskList = taskRepository.findAllByStatus(taskStatus);
        } else if (request.isUserInRole(Role.USER.name())) {
            User loggedInUser = userService.loggedInUser(request);
            taskList = taskRepository.findAllByStatusAndUser(taskStatus, loggedInUser);
        }
        List<TaskDataDTO> taskDataDTOList = new ArrayList<>();
        taskList.forEach(task -> {
            TaskDataDTO taskDataDTO = new TaskDataDTO();
            taskDataDTO.setDescription(task.getDescription());
            taskDataDTO.setDue_date(task.getTaskDueDate());
            taskDataDTO.setStatus(task.getStatus().getValue());
            taskDataDTOList.add(taskDataDTO);
        });
        return taskDataDTOList;
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
