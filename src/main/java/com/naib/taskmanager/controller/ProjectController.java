package com.naib.taskmanager.controller;

import com.naib.taskmanager.dto.ProjectDataDTO;
import com.naib.taskmanager.dto.UserDataDTO;
import com.naib.taskmanager.model.dao.Project;
import com.naib.taskmanager.service.ProjectService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    @GetMapping("/search")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 422, message = "Username is already in use")})
    public List<ProjectDataDTO> getALlProjects(HttpServletRequest request){
        List<ProjectDataDTO> projects = projectService.getAllProject(request);
        return projects;
    }
    @GetMapping("/search/{user_id}")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 422, message = "Username is already in use")})
    public List<ProjectDataDTO> getALlProjectsByUserId(@ApiParam("user_id") @PathVariable  Integer user_id){
        List<ProjectDataDTO> projects = projectService.getAllProjectByUserId(user_id);
        return projects;
    }

    @DeleteMapping(value = "/{project_id}")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "${UserController.delete}", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 404, message = "The user doesn't exist"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public String delete(@ApiParam("project_id") @PathVariable Integer project_id) {
        projectService.deleteByProjectId(project_id);
        return "Successfully deleted";
    }
}
