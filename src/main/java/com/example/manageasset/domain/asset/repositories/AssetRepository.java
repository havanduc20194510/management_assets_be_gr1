package com.example.manageasset.domain.asset.repositories;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.shared.models.QueryFilter;

import java.util.List;

public interface AssetRepository {
    List<Asset> getAll(QueryFilter queryFilter, String searchText, Long categoryId, Integer state);
    List<Asset> findAll();

    Long countTotal(String searchText, Long categoryId, Integer state);
    Asset getById(Long id);
    void save(Asset asset);
    List<Asset> findByIdIn(List<Long> assetIds);
    Boolean existedAssetInCategory(Long categoryId);
}
