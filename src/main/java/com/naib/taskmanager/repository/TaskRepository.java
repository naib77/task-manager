package com.naib.taskmanager.repository;

import com.naib.taskmanager.model.TaskStatus;
import com.naib.taskmanager.model.dao.Task;
import com.naib.taskmanager.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task getById(Integer id);

    Task getByIdAndUser(Integer id, User user);

    List<Task> findAllByProject_Id(Integer project_id);

    List<Task> findAllByUser_id(Integer user_id);

    List<Task> findAllByProject_IdAndUser(Integer project_id, User user);

    List<Task> findAllByStatus(TaskStatus status);

    List<Task> findAllByStatusAndUser(TaskStatus status, User user);

    List<Task> findAllByTaskDueDateBefore(Date date);

    List<Task> findAllByTaskDueDateBeforeAndUser(Date date, User user);

    void deleteByIdAndUser(Integer id, User user);
}
