package com.naib.taskmanager;

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
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword("admin");
		admin.setEmail("admin@email.com");
		admin.setRoles(new ArrayList<>(Arrays.asList(Role.ADMIN)));

		userService.signup(admin);

		User client = new User();
		client.setUsername("user");
		client.setPassword("user");
		client.setEmail("user@email.com");
		client.setRoles(new ArrayList<>(Arrays.asList(Role.USER)));

		userService.signup(client);
	}
}
