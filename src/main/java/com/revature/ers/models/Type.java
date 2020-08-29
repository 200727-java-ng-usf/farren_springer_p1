package com.revature.ers.models;

public enum Type {

    LODGING("Lodging"), TRAVEL("Travel"), FOOD("Food"), OTHER("Other");

    private String reimbursementTypeName;

    Type(String type) { this.reimbursementTypeName = type; }

    public static Type getByType(String type) {

        for (Type reimbursementType : Type.values()) {
            if (reimbursementType.reimbursementTypeName.equalsIgnoreCase(type)) {
                return reimbursementType;
            }
        }

        return OTHER;
    }


    @Override
    public String toString() {
        return reimbursementTypeName;
    }
}
