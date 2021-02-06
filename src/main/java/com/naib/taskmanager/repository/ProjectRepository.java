package com.naib.taskmanager.repository;

import com.naib.taskmanager.model.dao.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
