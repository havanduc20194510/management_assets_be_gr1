package com.example.manageasset.domain.lease.models;

import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.shared.models.Status;
import com.example.manageasset.domain.user.models.User;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaseContract {
    private String id;
    private User client;
    private User user;
    private String reason;
    private Millisecond revokedAt;
    private Millisecond leasedAt;
    private String note;
    private List<AssetLeased> assetLeaseds;
    private Millisecond createdAt;
    private Millisecond updatedAt;
    private Boolean isDeleted;
    private Status status;

    public LeaseContract(String id, User client, User user, String reason, Millisecond revokedAt, Millisecond leasedAt, String note, Millisecond createdAt, Millisecond updatedAt, Boolean isDeleted, Status status) {
        this.id = id;
        this.client = client;
        this.user = user;
        this.reason = reason;
        this.revokedAt = revokedAt;
        this.leasedAt = leasedAt;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.status = status;
    }

    public LeaseContract(String id, User client, String reason, Millisecond revokedAt, Millisecond leasedAt, String note, Millisecond createdAt, Millisecond updatedAt, Boolean isDeleted, Status status) {
        this.id = id;
        this.client = client;
        this.reason = reason;
        this.revokedAt = revokedAt;
        this.leasedAt = leasedAt;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.status = status;
    }

    public static LeaseContract create(String id, User client, String reason, Millisecond revokedAt, Millisecond leasedAt, String note){
        validate(id, reason);
        return new LeaseContract(id, client, reason, revokedAt, leasedAt, note, Millisecond.now(), Millisecond.now(), false, Status.INPROGRESS);
    }

    private static void validate(String id, String reason){
        if(Strings.isNullOrEmpty(id)) throw new InvalidDataException("Required field LeaseContract[id]");
        if(Strings.isNullOrEmpty(reason)) throw new InvalidDataException("Required field LeaseContract[reason]");
    }

    private void validate(String reason){
        if(Strings.isNullOrEmpty(reason)) throw new InvalidDataException("Required field LeaseContract[reason]");
    }

    public void update(String reason, Millisecond revokedAt, Millisecond leasedAt, String note){
        validate(reason);

        this.reason = reason;
        this.revokedAt = revokedAt;
        this.leasedAt = leasedAt;
        this.note = note;
        this.updatedAt = Millisecond.now();
    }

    public void update(Status status, User user){
        this.status = status;
        this.user = user;
        this.updatedAt = Millisecond.now();
    }
}
