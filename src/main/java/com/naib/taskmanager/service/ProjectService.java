package com.naib.taskmanager.service;

import com.naib.taskmanager.dto.ProjectDataDTO;
import com.naib.taskmanager.dto.ProjectResponseDataDTO;
import com.naib.taskmanager.model.Role;
import com.naib.taskmanager.model.dao.Project;
import com.naib.taskmanager.model.dao.Task;
import com.naib.taskmanager.model.dao.User;
import com.naib.taskmanager.repository.ProjectRepository;
import com.naib.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    @Autowired
    UserService userService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;

    public Project createProject(ProjectDataDTO projectDataDTO) {
        Project project = new Project();
        project.setName(projectDataDTO.getName());
        if (projectDataDTO.getUser_id() != null) {
            Set<User> users = new HashSet<>();
            User user = new User();
            user.setId(projectDataDTO.getUser_id());
            users.add(user);
            project.setUser(users);
        }

        return projectRepository.save(project);
    }

    public List<ProjectResponseDataDTO> getAllProject(HttpServletRequest request) {
        List<Project> projects = null;
        if (request.isUserInRole(Role.ADMIN.name())) {
            projects = projectRepository.findAll();
        } else if (request.isUserInRole(Role.USER.name())) {
            System.out.println("User");
            User loggedInUser = userService.loggedInUser(request);
            projects = projectRepository.findByUser(loggedInUser);
        }

        List<ProjectResponseDataDTO> projectList = new ArrayList<>();
        projects.forEach(project -> {
            ProjectResponseDataDTO projectDataDTO = new ProjectResponseDataDTO();
            projectDataDTO.setName(project.getName());
            Set<User> users = project.getUser();
            Set<Integer> userIdList = new HashSet<>();
            users.forEach(user -> {
                userIdList.add(user.getId());
            });
            projectDataDTO.setUsers(userIdList);
            projectList.add(projectDataDTO);
        });
        return projectList;
    }

    public List<ProjectDataDTO> getAllProjectByUserId(Integer userId) {
        List<Project> projects = null;
        User user = new User();
        user.setId(userId);
        projects = projectRepository.findByUser(user);

        List<ProjectDataDTO> projectList = new ArrayList<>();
        projects.forEach(project -> {
            ProjectDataDTO projectDataDTO = new ProjectDataDTO();
            projectDataDTO.setName(project.getName());
            projectDataDTO.setUser_id(userId);
            projectList.add(projectDataDTO);
        });
        return projectList;
    }

    @Transactional
    public void deleteByProjectId(Integer projectId) {
        List<Task> tasks = taskRepository.findAllByProject_Id(projectId);
        tasks.forEach(task -> {
            task.setProject(null);
        });
        taskRepository.saveAll(tasks);
        projectRepository.deleteById(projectId);
    }
}
