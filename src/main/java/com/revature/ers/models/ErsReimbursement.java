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
    private Blob receipt;
    private Integer authorId;
    private Integer resolverId;
    private Integer reimbursementStatusId;
    private Integer reimbursementTypeId;

    /**
     * Constructor with minimum parameter requirements
     * @param amount
     * @param submitted
     * @param description
     * @param authorId
     * @param reimbursementStatusId
     * @param reimbursementTypeId
     */
    public ErsReimbursement(Double amount, Timestamp submitted, String description, Integer authorId, Integer reimbursementStatusId, Integer reimbursementTypeId) {
        this.amount = amount;
        this.submitted = submitted;
        this.description = description;
        this.authorId = authorId;
        this.reimbursementStatusId = reimbursementStatusId;
        this.reimbursementTypeId = reimbursementTypeId;
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
    public ErsReimbursement(Double amount, Timestamp submitted, Timestamp resolved, String description, Blob receipt, Integer authorId, Integer resolverId, Integer reimbursementStatusId, Integer reimbursementTypeId) {
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.authorId = authorId;
        this.resolverId = resolverId;
        this.reimbursementStatusId = reimbursementStatusId;
        this.reimbursementTypeId = reimbursementTypeId;
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
    public ErsReimbursement(Integer id, Double amount, Timestamp submitted, Timestamp resolved, String description, Blob receipt, Integer authorId, Integer resolverId, Integer reimbursementStatusId, Integer reimbursementTypeId) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.authorId = authorId;
        this.resolverId = resolverId;
        this.reimbursementStatusId = reimbursementStatusId;
        this.reimbursementTypeId = reimbursementTypeId;
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

    public Blob getReceipt() {
        return receipt;
    }

    public void setReceipt(Blob receipt) {
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

    public Integer getReimbursementStatusId() {
        return reimbursementStatusId;
    }

    public void setReimbursementStatusId(Integer reimbursementStatusId) {
        this.reimbursementStatusId = reimbursementStatusId;
    }

    public Integer getReimbursementTypeId() {
        return reimbursementTypeId;
    }

    public void setReimbursementTypeId(Integer reimbursementTypeId) {
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
        return "ErsReimbursement{" +
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
                '}';
    }
}
