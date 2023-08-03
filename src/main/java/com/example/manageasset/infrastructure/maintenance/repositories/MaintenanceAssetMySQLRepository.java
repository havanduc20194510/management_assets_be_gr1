package com.example.manageasset.infrastructure.maintenance.repositories;

import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MaintenanceAssetMySQLRepository implements MaintenanceAssetRepository {
    private final MaintenanceAssetJpa maintenanceAssetJpa;

    @Override
    public void deleteAllByMaintenanceId(String maintenanceId) {
        maintenanceAssetJpa.deleteAllByMaintenanceAssetLeased_Id(maintenanceId);
    }

    @Override
    public boolean existedMaintenanceAssetLeased(String assetCode) {
        return maintenanceAssetJpa.existedMaintenanceAssetLeased(assetCode);
    }

}
