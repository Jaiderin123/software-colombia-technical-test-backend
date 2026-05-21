package com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("projects")
public class ProjectEntity {

    @Id
    private Long id;

    @Column("workspace_id")
    private Long workspaceId;

    private String name;

    private String description;

    @Column("created_at")
    private LocalDateTime createdAt;
}