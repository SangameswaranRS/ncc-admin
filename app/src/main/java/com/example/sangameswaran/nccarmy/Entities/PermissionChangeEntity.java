package com.example.sangameswaran.nccarmy.Entities;

import java.lang.ref.SoftReference;

/**
 * Created by Sangameswaran on 10-05-2017.
 */

public class PermissionChangeEntity {
    String user_id,revoked_by,revoked_during,reasonsForPermissionChange;

    public PermissionChangeEntity()
    {}

    public PermissionChangeEntity(String user_id, String revoked_by, String revoked_during, String reasonsForPermissionChange) {
        this.user_id = user_id;
        this.revoked_by = revoked_by;
        this.revoked_during = revoked_during;
        this.reasonsForPermissionChange = reasonsForPermissionChange;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRevoked_by() {
        return revoked_by;
    }

    public void setRevoked_by(String revoked_by) {
        this.revoked_by = revoked_by;
    }

    public String getRevoked_during() {
        return revoked_during;
    }

    public void setRevoked_during(String revoked_during) {
        this.revoked_during = revoked_during;
    }

    public String getReasonsForPermissionChange() {
        return reasonsForPermissionChange;
    }

    public void setReasonsForPermissionChange(String reasonsForPermissionChange) {
        this.reasonsForPermissionChange = reasonsForPermissionChange;
    }
}
