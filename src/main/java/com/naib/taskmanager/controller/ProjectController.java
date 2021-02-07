package com.naib.taskmanager.controller;

import com.naib.taskmanager.dto.ProjectDataDTO;
import com.naib.taskmanager.dto.ProjectResponseDataDTO;
import com.naib.taskmanager.service.ProjectService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
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
        ProjectResponseDataDTO createdProject = modelMapper.map(projectService.createProject(project), ProjectResponseDataDTO.class);
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
    public String delete(@ApiParam("project_id") @PathVariable Integer project_id) {
        ProjectResponseDataDTO projectResponseDataDTO= new ProjectResponseDataDTO();
        try {
            projectService.deleteByProjectId(project_id);
            projectResponseDataDTO.setMessage("Project Successfully Deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Successfully deleted";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
