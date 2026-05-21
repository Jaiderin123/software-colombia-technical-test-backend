package com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.workspace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("workspaces")
public class WorkspaceEntity {

    @Id
    private Long id;

    private String name;
}