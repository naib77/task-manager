package com.naib.taskmanager.controller;

import com.naib.taskmanager.dto.ProjectDataDTO;
import com.naib.taskmanager.dto.TaskDataDTO;
import com.naib.taskmanager.service.TaskService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/tasks")
@Api(tags = "task")
public class TaskController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskService taskService;


    @PostMapping("/create")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public TaskDataDTO createTask(@ApiParam("Create New Task") @RequestBody TaskDataDTO taskDataDTO){
        TaskDataDTO createdTask = modelMapper.map(taskService.createTask(taskDataDTO),TaskDataDTO.class);
        return createdTask;
    }

    @PutMapping("/edit")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public TaskDataDTO updateTask(@ApiParam("Update New Task") @RequestBody TaskDataDTO taskDataDTO){
        TaskDataDTO createdTask = modelMapper.map(taskService.updateTask(taskDataDTO),TaskDataDTO.class);
        return createdTask;
    }
    @GetMapping("/{task_id}")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public TaskDataDTO getTaskById(@ApiParam("task_id") @PathVariable  Integer task_id){
        TaskDataDTO createdTask = modelMapper.map(taskService.getTaskById(task_id),TaskDataDTO.class);
        return createdTask;
    }

    @GetMapping("/search/by-project/{project_id}")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public List<TaskDataDTO> getALlTaskByProjectId(@ApiParam("project_id") @PathVariable  Integer project_id, HttpServletRequest request){
        List<TaskDataDTO> tasks = taskService.getAllTaskByProjectId(project_id, request);
        return tasks;
    }
    @GetMapping("/search/by-status/{status}")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public List<TaskDataDTO> getALlTaskByProjectId(@ApiParam("status") @PathVariable  String status, HttpServletRequest request){
        List<TaskDataDTO> tasks = taskService.getAllTaskByStatus(status, request);
        return tasks;
    }
    @GetMapping("/search/by-user/{user_id}")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public List<TaskDataDTO> getALlTaskByUserId(@ApiParam("user_id") @PathVariable  Integer user_id){
        List<TaskDataDTO> tasks = taskService.getAllTaskByUserId(user_id);
        return tasks;
    }
    @GetMapping("/search/expired-task")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public List<TaskDataDTO> getALlTaskByProjectId( HttpServletRequest request){
        List<TaskDataDTO> tasks = taskService.getAllExpiredTask(request);
        return tasks;
    }
}
