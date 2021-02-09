package com.naib.taskmanager.migration;

import com.naib.taskmanager.dto.ProjectDataDTO;
import com.naib.taskmanager.dto.TaskDataDTO;
import com.naib.taskmanager.dto.UserResponseDTO;
import com.naib.taskmanager.model.Role;
import com.naib.taskmanager.model.dao.Project;
import com.naib.taskmanager.model.dao.User;
import com.naib.taskmanager.service.ProjectService;
import com.naib.taskmanager.service.TaskService;
import com.naib.taskmanager.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Component
@Service
public class DefaultDBDataInsertion {
    @Autowired
    UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ModelMapper modelMapper;

    public void insertData() {
        User adminUser = null;
        User user = null;
        try {
            adminUser = userService.search("admin");
            user = userService.search("user");
        } catch (Exception e) {
//			e.printStackTrace();
        }
        if (adminUser == null) {
            User admin = new User();
            admin.setId(1);
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@email.com");
            admin.setRoles(new ArrayList<>(Arrays.asList(Role.ADMIN)));

            adminUser = admin;
            userService.signup(admin);
        }

        if (user == null) {
            User client = new User();
            client.setId(2);
            client.setUsername("user");
            client.setPassword("user");
            client.setEmail("user@email.com");
            client.setRoles(new ArrayList<>(Arrays.asList(Role.USER)));

            user = client;
            userService.signup(client);
        }

        ProjectDataDTO project01 = new ProjectDataDTO();
        project01.setName("demo project");
        project01.setUser_id(adminUser.getId());
        projectService.createProject(project01);

        ProjectDataDTO project02 = new ProjectDataDTO();
        project02.setName("demo project");
        project02.setUser_id(user.getId());
        projectService.createProject(project02);

//        TaskDataDTO task01 = new TaskDataDTO();
//        task01.
//        taskService.createTask()
    }
}
