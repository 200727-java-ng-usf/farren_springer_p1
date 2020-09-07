package com.revature.ers.models;

/**
 * Reimbursement Type
 */
public enum Type {

    LODGING(1), TRAVEL(2), FOOD(3), OTHER(4);

    private Integer typeId;

    // enum constructors are implicitly private
    Type(Integer num) { this.typeId = num; }

    public static Type getById(Integer type) {
        /**
         * For each constant in roles, if it's id equivalent is the same as the
         * parameter, role, of getById, then return it.
         */
        for (Type theType : Type.values()) {
            if (theType.typeId.equals(type)) {
                return theType;
            }


        }

        /**
         * By default, return OTHER
         */
        return OTHER;
    }

    @Override
    public String toString() {
        /**
         * Use a switch case for the different types.
         */
        switch (typeId) {
            case 1:
                return "Lodging";
            case 2:
                return "Travel";
            case 3:
                return "Food";
            case 4:
                return "Other";
        }
        return "no type found";
    }
}
