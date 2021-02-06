package com.naib.taskmanager;

import com.naib.taskmanager.dto.UserResponseDTO;
import com.naib.taskmanager.model.Role;
import com.naib.taskmanager.model.dao.User;
import com.naib.taskmanager.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class TaskmanagerApplication  implements CommandLineRunner {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {
		UserResponseDTO adminUser = null;
		UserResponseDTO user = null;
		try {
			adminUser = this.modelMapper().map(userService.search("admin"), UserResponseDTO.class);
			user = this.modelMapper().map(userService.search("user"), UserResponseDTO.class);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		if (adminUser == null){
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword("admin");
			admin.setEmail("admin@email.com");
			admin.setRoles(new ArrayList<>(Arrays.asList(Role.ADMIN)));

			userService.signup(admin);
		}

		if (user == null){
			User client = new User();
			client.setUsername("user");
			client.setPassword("user");
			client.setEmail("user@email.com");
			client.setRoles(new ArrayList<>(Arrays.asList(Role.USER)));

			userService.signup(client);
		}

	}
}
