package com.example.manageasset.infrastructure.maintenance.controllers;

import com.example.manageasset.domain.maintenance.dtos.MaintenanceAssetLeasedDto;
import com.example.manageasset.domain.maintenance.services.ListMaintenanceAssetLeasedService;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenance-asset-leased")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ListMaintenanceAssetLeasedOfCurrentUserController {
    private final ListMaintenanceAssetLeasedService listMaintenanceAssetLeasedService;

    @GetMapping("/list-by-user")
    public ResponseEntity<?> listByUser(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
                                  @RequestParam(value = "from", required = false) Long from,
                                  @RequestParam(value = "to", required = false) Long to,
                                  @RequestParam(value = "key", required = false) String searchText,
                                  @RequestParam(value = "status", required = false) Integer status) {
        if(to != null){
            to += 86399000;
        }
        PagingPayload<List<MaintenanceAssetLeasedDto>> result = listMaintenanceAssetLeasedService.getAllByUser(limit, page, sort, from, to, searchText, status);
        return new ResponseEntity<>(new ResponseBody(result, ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
