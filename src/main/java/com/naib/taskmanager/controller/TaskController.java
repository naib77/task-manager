package com.naib.taskmanager.controller;

import com.naib.taskmanager.dto.TaskDataDTO;
import com.naib.taskmanager.dto.TaskResponseDataDTO;
import com.naib.taskmanager.dto.TaskUpdateDataDTO;
import com.naib.taskmanager.service.TaskService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/tasks")
@Api(tags = "task")
public class TaskController {
    public static Logger LOG = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskService taskService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Create Task")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public TaskDataDTO createTask(@ApiParam("Create New Task") @RequestBody TaskDataDTO taskDataDTO) {
        TaskDataDTO createdTask = modelMapper.map(taskService.createTask(taskDataDTO), TaskDataDTO.class);
        return createdTask;
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Edit Task")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public TaskResponseDataDTO updateTask(@ApiParam("Update New Task") @RequestBody TaskUpdateDataDTO taskDataDTO, HttpServletRequest request) {
        TaskResponseDataDTO responseDataDTO = taskService.updateTask(taskDataDTO, request);
        return responseDataDTO;
    }

    //TODO need to update user role wise
    @GetMapping("/{task_id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Get Task")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public TaskResponseDataDTO getTaskById(@ApiParam("task_id") @PathVariable Integer task_id, HttpServletRequest request) {
        TaskResponseDataDTO createdTask = taskService.getTaskById(task_id, request);
        return createdTask;
    }

    @GetMapping("/search/by-project/{project_id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Get task by project_id")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public List<TaskResponseDataDTO> getALlTaskByProjectId(@ApiParam("project_id") @PathVariable Integer project_id, HttpServletRequest request) {
        List<TaskResponseDataDTO> tasks = taskService.getAllTaskByProjectId(project_id, request);
        return tasks;
    }

    @GetMapping("/search/by-status/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Get task by status [open/in progress/closed]")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public List<TaskResponseDataDTO> getALlTaskByProjectId(@ApiParam("status") @PathVariable String status, HttpServletRequest request) {
        List<TaskResponseDataDTO> tasks = taskService.getAllTaskByStatus(status, request);
        return tasks;
    }

    @GetMapping("/search/by-user/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Only ADMIN can search task by user_id")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public List<TaskResponseDataDTO> getALlTaskByUserId(@ApiParam("user_id") @PathVariable Integer user_id) {
        List<TaskResponseDataDTO> tasks = taskService.getAllTaskByUserId(user_id);
        return tasks;
    }

    @GetMapping("/search/expired-task")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Get expired task")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public List<TaskResponseDataDTO> getALlTaskByProjectId(HttpServletRequest request) {
        List<TaskResponseDataDTO> tasks = taskService.getAllExpiredTask(request);
        if (tasks.size() == 0) {
            TaskResponseDataDTO taskResponseDataDTO = new TaskResponseDataDTO();
            taskResponseDataDTO.setMessage("No Expired Task Available");
            tasks.add(taskResponseDataDTO);
        }
        return tasks;
    }
}
