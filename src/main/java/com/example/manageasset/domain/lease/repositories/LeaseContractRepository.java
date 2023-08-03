package com.example.manageasset.domain.lease.repositories;

import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.shared.models.QueryFilter;
import com.example.manageasset.domain.shared.models.Status;
import com.example.manageasset.domain.user.models.User;

import java.util.List;

public interface LeaseContractRepository {
    void save(LeaseContract leaseContract);
    LeaseContract findById(String id);
    void deleteById(String id);
    List<LeaseContract> list(QueryFilter queryFilter, String searchText, Long leasedAtFrom, Long leasedAtTo, Status status, String username);
    long totalList(String searchText, Long leasedAtFrom, Long leasedAtTo, Status status, String username);
}
