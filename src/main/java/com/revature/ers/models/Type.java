package com.revature.ers.models;

public enum Type {

    // values declared within enums are constants and are comma separated
    LODGING("Lodging"),
    TRAVEL("Travel"),
    FOOD("Food"),
    OTHER("Other");

    private String typeName;

    // enum constructors are implicitly private
    Type(String name) {
        this.typeName = name;
    }

    public static Type getByName(String name) {

        for (Type type : Type.values()) {
            if (type.typeName.equals(name)) {
                return type;
            }
        }

        return OTHER;

        // functional implementation of the above code
//        return Arrays.stream(Type.values())
//                .filter(type -> type.typeName.equals(name))
//                .findFirst()
//                .orElse(LOCKED);

    }

    @Override
    public String toString() {
        return typeName;
    }

}
