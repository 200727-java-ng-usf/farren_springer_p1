package com.revature.ers.models;

public enum Status {

    // values declared within enums are constants and are comma separated
    PENDING("Pending"),
    APPROVED("Approved"),
    DENIED("Denied");

    private String statusName;

    // enum constructors are implicitly private
    Status(String name) {
        this.statusName = name;
    }

    public static Status getByName(String name) {

        for (Status status : Status.values()) {
            if (status.statusName.equals(name)) {
                return status;
            }
        }

        return PENDING;

        // functional implementation of the above code
//        return Arrays.stream(Status.values())
//                .filter(status -> status.statusName.equals(name))
//                .findFirst()
//                .orElse(LOCKED);

    }

    @Override
    public String toString() {
        return statusName;
    }
}
