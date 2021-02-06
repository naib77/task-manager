package com.naib.taskmanager.repository;

import com.naib.taskmanager.model.dao.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
