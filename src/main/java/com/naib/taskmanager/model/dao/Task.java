package com.naib.taskmanager.model.dao;

import com.naib.taskmanager.model.TaskStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer id;

    @Size(min = 2, max = 255, message = "Minimum name length: 4 characters")
    @Column(nullable = false)
    private String name;

    @Size(min = 2, max = 255, message = "Minimum name length: 4 characters")
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_task",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> user;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name="project_id", nullable=true)
    private Project project;

    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date taskDueDate;
}
