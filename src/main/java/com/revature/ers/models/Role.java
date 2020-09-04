package com.revature.ers.models;

public enum Role {
    ADMIN(1), FINANCE_MANAGER(2), EMPLOYEE(3);

    private Integer roleId;

    // enum constructors are implicitly private
    Role(Integer num) { this.roleId = num; }

    public static Role getById(Integer role) {
        /**
         * For each constant in roles, if it's id equivalent is the same as the
         * parameter, role, of getById, then return it.
         */
        for (Role theRole : Role.values()) {
            if (theRole.roleId.equals(role)) {
                return theRole;
            }


        }

        /**
         * Return the most restricted role by default.
         */
        return EMPLOYEE;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId='" + roleId + '\'' +
                '}';
    }
}
