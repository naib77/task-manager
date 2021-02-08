package com.naib.taskmanager.controller;

import com.naib.taskmanager.dto.ProjectDataDTO;
import com.naib.taskmanager.dto.ProjectResponseDataDTO;
import com.naib.taskmanager.service.ProjectService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/projects")
@Api(tags = "project")
public class ProjectController {
    public static Logger LOG = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Only user with role ADMIN or USER can create project")
    @Validated
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "already in use")})
    public ProjectResponseDataDTO createProject(@Valid  @ApiParam("Create New Project") @RequestBody ProjectDataDTO project) throws Exception {
        LOG.info("API call for creating project with {}",project.toString());
        ProjectResponseDataDTO createdProject = modelMapper.map(projectService.createProject(project), ProjectResponseDataDTO.class);
        createdProject.setMessage("Successfully Project Created");
        return createdProject;
    }

    @GetMapping("/search")
    @ApiOperation(value = "Get All Projects (USER role will get only own projects)")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public List<ProjectResponseDataDTO> getALlProjects(HttpServletRequest request) {
        List<ProjectResponseDataDTO> projects = projectService.getAllProject(request);
        return projects;
    }

    @GetMapping("/search/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Only user with ADMIN role can search")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Username is already in use")})
    public List<ProjectDataDTO> getALlProjectsByUserId(@ApiParam("user_id") @PathVariable Integer user_id) {
        List<ProjectDataDTO> projects = projectService.getAllProjectByUserId(user_id);
        return projects;
    }

    @DeleteMapping(value = "/{project_id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Delete Project", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public ProjectResponseDataDTO delete(@ApiParam("project_id") @PathVariable Integer project_id, HttpServletRequest request) {
        ProjectResponseDataDTO projectResponseDataDTO= new ProjectResponseDataDTO();
        try {
            projectResponseDataDTO = projectService.deleteByProjectId(project_id, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectResponseDataDTO;
    }
}
