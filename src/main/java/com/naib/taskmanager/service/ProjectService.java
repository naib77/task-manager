package com.naib.taskmanager.service;

import com.naib.taskmanager.dto.ProjectDataDTO;
import com.naib.taskmanager.model.dao.Project;
import com.naib.taskmanager.model.dao.User;
import com.naib.taskmanager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(ProjectDataDTO projectDataDTO){
        Project project = new Project();
        project.setName(projectDataDTO.getName());
        if (projectDataDTO.getUser_id() != null){
            Set<User> users = new HashSet<>();
            User user = new User();
            user.setId(projectDataDTO.getUser_id());
            users.add(user);
            project.setUser(users);
        }

        return projectRepository.save(project);
    }
}
