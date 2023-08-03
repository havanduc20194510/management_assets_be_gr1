package com.example.manageasset.infrastructure.asset.repositories;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.models.State;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.infrastructure.user.repositories.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "description")
    private String description;
    @Column(name = "value", nullable = false)
    private Double value;
    @Column(name = "management_unit", nullable = false)
    private String managementUnit;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity manager;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "asset_id", nullable = false)
    private List<AttachmentEntity> attachments = new ArrayList<>();

    @Column(name = "state", nullable = false)
    private Integer state;

    public static AssetEntity fromModel(Asset asset) {
        return new AssetEntity(
                asset.getId(),
                asset.getName(),
                asset.getStatus(),
                asset.getDescription(),
                asset.getValue(),
                asset.getManagementUnit(),
                new Timestamp(asset.getCreatedAt().asLong()),
                new Timestamp(asset.getUpdatedAt().asLong()),
                asset.getIsDeleted(),
                UserEntity.fromModel(asset.getManager()),
                CategoryEntity.fromModel(asset.getCategory()),
                asset.getAttachments().stream().map(AttachmentEntity::fromDomain).collect(Collectors.toList()),
                asset.getState().asInt()
        );
    }

    public Asset toModel() {
        return new Asset(id, name, status, value, managementUnit, description, new Millisecond(createdAt.getTime()),
                new Millisecond(updatedAt.getTime()), isDeleted, manager.toModel(), category.toModel(),
                attachments.stream().map(AttachmentEntity::toDomain).collect(Collectors.toList()), new State(state));
    }
}
