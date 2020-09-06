package com.revature.ers.models;

/**
 * Reimbursement Type
 */
public enum Type {

    LODGING("Lodging"), TRAVEL("Travel"), FOOD("Food"), OTHER("Other");

    private String typeName;

    Type(String type) { this.typeName = type; }

    public static Type getByType(String type) {

        for (Type theType : Type.values()) {
            if (theType.typeName.equals(type)) {
                return theType;
            }
        }

        return OTHER;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
