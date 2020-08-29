package com.revature.ers.models;

/**
 * Reimbursement Status
 */
public enum Status {
    PENDING("Pending"), APPROVED("Approved"), DENIED("Denied");

    private String statusName;

    Status(String name) { this.statusName = name; }

    public static Status getByStatus(String status) {

        for (Status theStatusName : Status.values()) {
            if (theStatusName.statusName.equals(status)) {
                return theStatusName;
            }
        }

        return PENDING;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
