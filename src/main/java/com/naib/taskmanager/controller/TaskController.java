package com.naib.taskmanager.controller;

import com.naib.taskmanager.dto.ProjectDataDTO;
import com.naib.taskmanager.dto.TaskDataDTO;
import com.naib.taskmanager.service.TaskService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 422, message = "Username is already in use")})
    public TaskDataDTO createProject(@ApiParam("Create New Task") @RequestBody TaskDataDTO taskDataDTO){
        TaskDataDTO createdTask = modelMapper.map(taskService.createTask(taskDataDTO),TaskDataDTO.class);
        return createdTask;
    }
}
