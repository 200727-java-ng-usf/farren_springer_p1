package com.revature.ers.services;

import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.exceptions.ResourceNotFoundException;
import com.revature.ers.models.ErsReimbursement;

import com.revature.ers.repos.ReimbRepository;

import java.util.Set;

public class ReimbService {

    private ReimbRepository reimbRepo = new ReimbRepository();

    /**
     * CREATE operation
     * @param newReimbursement
     */
    public void register(ErsReimbursement newReimbursement) {

        if (!isReimbValid(newReimbursement)) {
            throw new InvalidRequestException("Invalid reimbursement field values provided during registration!");
        }

        reimbRepo.save(newReimbursement);
        System.out.println(newReimbursement);

    }

    /**
     * READ operation
     * @return
     */
    public Set<ErsReimbursement> getAllReimbs() {

        Set<ErsReimbursement> reimbs = reimbRepo.findAllReimbs();

        if (reimbs.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return reimbs;
    }

    /**
     * READ operation
     * @param authorId
     * @return
     */
    public Set<ErsReimbursement> getAllByAuthorId(Integer authorId) {

        Set<ErsReimbursement> reimbs = reimbRepo.findAllReimbsByAuthorId(authorId);

        if (reimbs.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return reimbs;

    }

    /**
     * READ operation
     * @param id
     * @return
     */
    public ErsReimbursement getReimbById(int id) {

        if(id <= 0) {
            throw new InvalidRequestException("The provided id cannot be less than or equal to zero.");
        }

        return reimbRepo.findReimbById(id)
                .orElseThrow(ResourceNotFoundException::new);

    }

    /**
     * UPDATE operation
     * @param updatedReimb
     * @return
     */
    public boolean resolve(ErsReimbursement updatedReimb) {

        if (updatedReimb == null) {
            throw new InvalidRequestException("reimb to resolve not found...");
        }

        reimbRepo.resolve(updatedReimb);
        return true;
    }

    /**
     * UPDATE operation
     * @param updatedReimb
     * @return
     */
    public boolean update(ErsReimbursement updatedReimb) {

        if(updatedReimb == null) {
            throw new InvalidRequestException("reimb to update not found...");
        }

        reimbRepo.update(updatedReimb);
        return true;

    }

    /**
     * DELETE operation
     * @param reimb
     */
    public void delete(ErsReimbursement reimb) {

        if (reimb == null) {
            throw new InvalidRequestException("reimb to delete not found...");
        } // null checking in SERVICE layer

        reimbRepo.delete(reimb);

    }

    /**
     * Convenience method (to be used in READ operation methods)
     * @param reimb
     * @return
     */
    public boolean isReimbValid(ErsReimbursement reimb) {
        if (reimb == null) return false;
        if (reimb.getAmount() == null) return false;
        if (reimb.getAuthorId() == null) return false;
        return true;
    }

}




// not using these
//    public Set<ErsReimbursement> getAllByStatus(Status status) {
//
//        Set<ErsReimbursement> reimbs = reimbRepo.findAllReimbsByStatus(status);
//
//        if (reimbs.isEmpty()) {
//            throw new ResourceNotFoundException();
//        }
//
//        return reimbs;
//
//    }
//
//    public Set<ErsReimbursement> getAllByType(Type type) {
//
//        Set<ErsReimbursement> reimbs = reimbRepo.findAllReimbsByType(type);
//
//        if (reimbs.isEmpty()) {
//            throw new ResourceNotFoundException();
//        }
//
//        return reimbs;
//
//    }
