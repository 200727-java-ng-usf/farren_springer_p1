package com.revature.ers.services;

import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.exceptions.ResourceNotFoundException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Status;
import com.revature.ers.models.Type;
import com.revature.ers.repos.ReimbRepository;

import java.util.Optional;
import java.util.Set;

public class ReimbService {

    private ReimbRepository reimbRepo = new ReimbRepository();

    public Set<ErsReimbursement> getAllReimbs() {

        Set<ErsReimbursement> reimbs = reimbRepo.findAllReimbs();

        if (reimbs.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return reimbs;
    }

    public Set<ErsReimbursement> getAllByStatus(Status status) {

        Set<ErsReimbursement> reimbs = reimbRepo.findAllReimbsByStatus(status);

        if (reimbs.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return reimbs;

    }

    public Set<ErsReimbursement> getAllByType(Type type) {

        Set<ErsReimbursement> reimbs = reimbRepo.findAllReimbsByType(type);

        if (reimbs.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return reimbs;

    }

    public Set<ErsReimbursement> getAllByAuthorId(Integer authorId) {

        Set<ErsReimbursement> reimbs = reimbRepo.findAllReimbsByAuthorId(authorId);

        if (reimbs.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return reimbs;

    }

    public void register(ErsReimbursement newReimbursement) {

        if (!isReimbValid(newReimbursement)) {
            throw new InvalidRequestException("Invalid reimbursement field values provided during registration!");
        }
//
//        Optional<ErsReimbursement> existingReimb = reimbRepo.findReimbById(newReimbursement.getId());

        // repeats of reimbs okay? No required unique values
//        if(existingReimb.isPresent()) {
//            // TODO implement a custom ResourcePersistenceException
//            throw new RuntimeException("Provided ")
//        }

        reimbRepo.save(newReimbursement);
        System.out.println(newReimbursement);

    }

    public ErsReimbursement getReimbById(int id) {

        if(id <= 0) {
            throw new InvalidRequestException("The provided id cannot be less than or equal to zero.");
        }

        return reimbRepo.findReimbById(id)
                .orElseThrow(ResourceNotFoundException::new);

    }

    public boolean isReimbValid(ErsReimbursement reimb) {
        if (reimb == null) return false;
        if (reimb.getAmount() == null) return false;
        if (reimb.getAuthorId() == null) return false;
        return true;
    }

}
