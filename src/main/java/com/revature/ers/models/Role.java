package com.revature.ers.models;

public enum Role {

    // values declared within enums are constants and are comma separated
    ADMIN("Admin"),
    FINANCE_MANAGER("Finance Manager"),
    EMPLOYEE("Employee");

    private String roleName;

    // enum constructors are implicitly private
    Role(String name) {
        this.roleName = name;
    }

    public static Role getByName(String name) {

        for (Role role : Role.values()) {
            if (role.roleName.equals(name)) {
                return role;
            }
        }

        return EMPLOYEE;

        // functional implementation of the above code
//        return Arrays.stream(Role.values())
//                .filter(role -> role.roleName.equals(name))
//                .findFirst()
//                .orElse(LOCKED);

    }

    @Override
    public String toString() {
        return roleName;
    }

}
