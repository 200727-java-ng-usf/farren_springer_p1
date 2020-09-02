package com.revature.ers.services;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.exceptions.ResourceNotFoundException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.repos.ReimbRepository;

import java.io.IOException;
import java.util.Set;

import static com.revature.ers.services.UserService.app;

public class ReimbService {

    private static ReimbRepository reimbRepo;

    /**
     * Constructor
     * @param repo
     */
    public ReimbService(ReimbRepository repo) {
//        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
        reimbRepo = repo;
    }

    /**
     * Authenticate that the reimbursement exists
     * @param reimbId
     */
    public void authenticateByReimbId(Integer reimbId) {

        if (reimbId == 0 || reimbId.equals("") ) {
            throw new InvalidRequestException("Invalid credential values provided!");
        }

        ErsReimbursement authReimb = reimbRepo.findById(reimbId)
                .orElseThrow(AuthenticationException::new);

        app.setCurrentReimbursement(authReimb);

    }

    /**
     * CREATE operation (Service Layer)
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
     * READ operation (Service Layer)
     * @param authorId
     */
    public void findReimbursement(Integer authorId) {

        // validate that the provided id is not a non-value
        if (authorId == null ) {
            throw new InvalidRequestException("Invalid credential values provided!");
        }
        System.out.println("Here are your reimbursement(s):"); // print to html?
        ErsReimbursement authReimb = reimbRepo.findReimbByAuthorId(authorId)
                .orElseThrow(AuthenticationException::new);

        app.setCurrentReimbursement(authReimb); // redundant?

    }

    /**
     * UPDATE operation (Service Layer)
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
     * DELETE operation (Service Layer)
     * @param reimbursement
     */
    public void deleteReimbursement(ErsReimbursement reimbursement) {
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
