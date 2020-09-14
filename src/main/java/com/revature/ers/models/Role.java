package com.revature.ers.models;

public enum Role {

    // values declared within enums are constants and are comma separated
    ADMIN("Admin"),
    FINANCE_MANAGER("FinManager"),
    EMPLOYEE("Employee"),
    INACTIVE("Inactive");

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

        return EMPLOYEE; // default is inactive

        // functional implementation of the above code
//        return Arrays.stream(Role.values())
//                .filter(role -> role.roleName.equals(name))
//                .findFirst()
//                .orElse(LOCKED);

    }

//    public static Role returnRoleBasedOnInt(Integer integer) {
//
//        switch(integer) {
//            case 1:
//                return Role.ADMIN;
//            case 2:
//                return Role.FINANCE_MANAGER;
//            case 3:
//                return Role.EMPLOYEE;
//            case 4:
//                return Role.INACTIVE;
//            default:
//                return Role.INACTIVE;
//        }
//    }

    @Override
    public String toString() {
        return roleName;
    }

}
