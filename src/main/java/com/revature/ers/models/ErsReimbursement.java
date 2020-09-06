package com.revature.ers.models;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Reimbursement POJOs
 */
public class ErsReimbursement {

    private Integer id;
    private Double amount;
    private Timestamp submitted;
    private Timestamp resolved;
    private String description;
    private String receipt;
    private Integer authorId;
    private Integer resolverId;
    private Status reimbursementStatusId;
    private Type reimbursementTypeId;

    public ErsReimbursement() {
        super();
    }

    public ErsReimbursement(Double amount, Type reimbursementTypeId) {
        this.amount = amount;
        this.reimbursementTypeId = reimbursementTypeId;
    }

    /**
     * Constructor
     * @param amount
     * @param reimbursementTypeId
     * @param description
     */
    public ErsReimbursement(Double amount, Type reimbursementTypeId, String description) {
        this(amount, reimbursementTypeId);
        this.description = description;
        System.out.println("In constructor");
    }

    public ErsReimbursement(Double amount, Type reimbursementTypeId, String description, Timestamp submitted) {
        this(amount, reimbursementTypeId, description);
        this.submitted = submitted;
        System.out.println("In constructor");
    }

    public ErsReimbursement(Double amount, Type reimbursementTypeId, String description, Timestamp submitted, Status reimbursementStatusId) {
        this(amount, reimbursementTypeId, description, submitted);
        this.reimbursementStatusId = reimbursementStatusId;
        System.out.println("In constructor");
    }

    /**
     * Constructor
     * @param amount
     * @param description
     * @param authorId
     */
    public ErsReimbursement(Double amount, Type reimbursementTypeId, String description, Integer authorId) {
        this(amount, reimbursementTypeId, description);
        this.authorId = authorId;
    }

    /**
     * Constructor
     * @param amount
     * @param submitted
     * @param description
     * @param authorId
     * @param reimbursementStatusId
     * @param reimbursementTypeId
     */
    public ErsReimbursement(Double amount, Timestamp submitted, String description, Integer authorId, Status reimbursementStatusId, Type reimbursementTypeId) {
        this(amount, reimbursementTypeId, description, authorId);
        this.submitted = submitted;
        this.reimbursementStatusId = reimbursementStatusId;
        this.authorId = authorId;
    }

    /**
     * Constructor with all params except id
     * @param amount
     * @param submitted
     * @param resolved
     * @param description
     * @param receipt
     * @param authorId
     * @param resolverId
     * @param reimbursementStatusId
     * @param reimbursementTypeId
     */
    public ErsReimbursement(Double amount, Timestamp submitted, Timestamp resolved, String description, String receipt, Integer authorId, Integer resolverId, Status reimbursementStatusId, Type reimbursementTypeId) {
        this(amount, submitted, description, authorId, reimbursementStatusId, reimbursementTypeId);
        this.resolved = resolved;
        this.receipt = receipt;
        this.resolverId = resolverId;
    }

    /**
     * Full constructor
     * @param id
     * @param amount
     * @param submitted
     * @param resolved
     * @param description
     * @param receipt
     * @param authorId
     * @param resolverId
     * @param reimbursementStatusId
     * @param reimbursementTypeId
     */
    public ErsReimbursement(Integer id, Double amount, Timestamp submitted, Timestamp resolved, String description, String receipt, Integer authorId, Integer resolverId, Status reimbursementStatusId, Type reimbursementTypeId) {
        this(amount, submitted, resolved, description, receipt, authorId, resolverId, reimbursementStatusId, reimbursementTypeId);
        this.id = id;

    }

    /**
     * Copy constructor (used for conveniently copying the values of one ErsReimbursement to create a new instance with those values)
     * @param copy
     */
    public ErsReimbursement(ErsReimbursement copy) {
        this(copy.id, copy.amount, copy.submitted, copy.resolved, copy.description, copy.receipt, copy.authorId, copy.resolverId, copy.reimbursementStatusId, copy.reimbursementTypeId);
    }


    /**
     * Getters and Setters
     */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getResolverId() {
        return resolverId;
    }

    public void setResolverId(Integer resolverId) {
        this.resolverId = resolverId;
    }

    public Status getReimbursementStatusId() {
        return reimbursementStatusId;
    }

    public void setReimbursementStatusId(Status reimbursementStatusId) {
        this.reimbursementStatusId = reimbursementStatusId;
    }

    public Type getReimbursementTypeId() {
        return reimbursementTypeId;
    }

    public void setReimbursementTypeId(Type reimbursementTypeId) {
        this.reimbursementTypeId = reimbursementTypeId;
    }

    /**
     * Overridden object methods
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErsReimbursement that = (ErsReimbursement) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(submitted, that.submitted) &&
                Objects.equals(resolved, that.resolved) &&
                Objects.equals(description, that.description) &&
                Objects.equals(receipt, that.receipt) &&
                Objects.equals(authorId, that.authorId) &&
                Objects.equals(resolverId, that.resolverId) &&
                Objects.equals(reimbursementStatusId, that.reimbursementStatusId) &&
                Objects.equals(reimbursementTypeId, that.reimbursementTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, submitted, resolved, description, receipt, authorId, resolverId, reimbursementStatusId, reimbursementTypeId);
    }

    @Override
    public String toString() {
        return "\nErsReimbursement: " +
                "id=" + id +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", authorId=" + authorId +
                ", resolverId=" + resolverId +
                ", reimbursementStatusId=" + reimbursementStatusId +
                ", reimbursementTypeId=" + reimbursementTypeId +
                ' ' + "\n";
    }
}
