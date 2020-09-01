package com.revature.ers.services;

import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.exceptions.ResourceNotFoundException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.repos.ReimbRepository;

import java.io.IOException;

import static com.revature.ers.services.UserService.app;

public class ReimbService {

    private static ReimbRepository reimbRepo;

    public ReimbService(ReimbRepository repo) {
//        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
        reimbRepo = repo;
    }

    /**
     * CREATE operation
     * @param newReimbursement
     */
    public void register(ErsReimbursement newReimbursement) {

        if (!isReimbursementValid(newReimbursement)) {
            throw new InvalidRequestException("Invalid reimbursement field values provided during registration!");
        }

        /**
         * Right now the method does not check if the reimb already exists...
         * Maybe make sure users do not create 1399429 reimbs (set a limit).
         */
//        Optional<Account> existingAccount = accountRepo.findAccountByAccountId(newAccount.getId());
//        if (existingAccount.isPresent()) {
        // TODO implement a custom ResourcePersistenceException

//            throw new RuntimeException("Provided username is already in use!");
//        }
        /**
         * To save the reimbursement, accountRepo calls the save method.
         */
        System.out.println("In register method");
        reimbRepo.save(newReimbursement);
        System.out.println(newReimbursement);
//        app.setCurrentReimbursement(newReimbursement); // redundant?

    }

    /**
     * UPDATE operation
     * @param reimbursement
     * @param amount
     * @throws IOException
     */
    public void update(ErsReimbursement reimbursement, Double amount) throws IOException {

        reimbursement.setAmount(amount);
        System.out.println("Updating in Service layer " + amount + " into reimb #" + reimbursement.getId());
        reimbRepo.update(reimbursement);
    }

    /**
     * DELETE operation
     * @param reimbursement
     */
    public void deleteAccount(ErsReimbursement reimbursement) {
        if (reimbursement == null ) {
            throw new ResourceNotFoundException("The Reimbursement does not exist!");
        }
        reimbRepo.deleteById(reimbursement.getId());
    }

    /**
     * Validates that the given reimbursement and its fields are valid (not null or empty strings).
     * @param reimbursement
     * @return
     */
    public boolean isReimbursementValid(ErsReimbursement reimbursement) {
        if (reimbursement == null) return false;
        if (reimbursement.getAmount() == null ) return false;
        return true;
    }
}
