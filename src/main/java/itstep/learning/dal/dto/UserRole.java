package itstep.learning.dal.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRole {
    private UUID roleId;
    private String roleName;
    private boolean canCreate;
    private boolean canRead;
    private boolean canUpdate;
    private boolean canDelete;

    public UserRole() {
    }
    public UserRole(ResultSet rs) throws SQLException {
        this.setRoleId(UUID.fromString(rs.getString("role_id")));
        this.setRoleName(rs.getString("role_name"));
        this.setCanCreate(rs.getBoolean("can_create"));
        this.setCanRead(rs.getBoolean("can_read"));
        this.setCanUpdate(rs.getBoolean("cac_update"));
        this.setCanDelete(rs.getBoolean("can_delete"));
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isCnCreate() {
        return canCreate;
    }

    public void setCanCreate(boolean cnCreate) {
        this.canCreate = cnCreate;
    }

    public boolean isCanRead() {
        return canRead;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean cnDelete) {
        this.canDelete = cnDelete;
    }
}
