package com.revature.ers.models;

public enum Role {
    ADMIN("Admin"), FINANCE_MANAGER("Finance Manager"), EMPLOYEE("Employee");

    private String roleName;

    // enum constructors are implicitly private
    Role(String name) { this.roleName = name; }

    public static Role getByName(String role) {
        /**
         * For each constant in roles, if it is the same as the
         * parameter of getByRole, return it.
         */
        for (Role theRole : Role.values()) {
            if (theRole.roleName.equals(role)) {
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
                "roleName='" + roleName + '\'' +
                '}';
    }
}
