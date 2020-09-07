package com.revature.ers.models;

/**
 * Reimbursement Status
 */
public enum Status {
    PENDING(1), APPROVED(2), DENIED(3);

    private Integer statusId;

    // enum constructors are implicitly private
    Status(Integer num) { this.statusId = num; }

    public static Status getById(Integer status) {
        /**
         * For each constant in roles, if it's id equivalent is the same as the
         * parameter, role, of getById, then return it.
         */
        for (Status theStatus : Status.values()) {
            if (theStatus.statusId.equals(status)) {
                return theStatus;
            }


        }

        /**
         * By default, return PENDING
         */
        return PENDING;
    }

    @Override
    public String toString() {
        /**
         * Use a switch case for the different statuses.
         */
        switch (statusId) {
            case 1:
                return "Pending";
            case 2:
                return "Approved";
            case 3:
                return "Denied";
        }
        return "no status found";
    }
}
