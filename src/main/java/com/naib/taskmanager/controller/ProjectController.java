package com.naib.taskmanager.controller;

import com.naib.taskmanager.dto.ProjectDataDTO;
import com.naib.taskmanager.dto.UserDataDTO;
import com.naib.taskmanager.service.ProjectService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/projects")
@Api(tags = "project")
public class ProjectController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 422, message = "Username is already in use")})
    public ProjectDataDTO createProject(@ApiParam("Create New Project") @RequestBody ProjectDataDTO project){
        ProjectDataDTO createdProject = modelMapper.map(projectService.createProject(project),ProjectDataDTO.class);
        return createdProject;
    }
}
