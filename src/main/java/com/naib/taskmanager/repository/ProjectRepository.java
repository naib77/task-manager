package com.naib.taskmanager.repository;

import com.naib.taskmanager.model.dao.Project;
import com.naib.taskmanager.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByUser(User user);
    Project findByIdAndUser(Integer id, User user);
}
