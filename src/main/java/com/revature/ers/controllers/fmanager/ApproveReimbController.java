package com.revature.ers.controllers.fmanager;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Status;
import com.revature.ers.repos.ReimbRepository;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static com.revature.ers.services.UserService.app;

/**
 * ApproveReimbController performs the UPDATE operation on the project1 schema.
 * This controller should only be accessible to users with role FINANCE_MANAGER
 */
public class ApproveReimbController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);
    private static ReimbRepository reimbRepo = new ReimbRepository();

    public static String approveReimb(HttpServletRequest req) throws IOException {

        System.out.println("In approveReimb method in approveReimbController...");

        if(!req.getMethod().equals("POST")) {
            return "/html/fmanager/approvereimb.html";
        }

        // TODO authenticate that the user's role field is ADMIN

        // Don't need to acquire any form data


        try {

            /**
             * Find the ID of the Reimbursement
             * Cast it to an integer to use to find the reimbursement in the DB
             */
            Integer reimbId = Integer.parseInt(String.valueOf(req.getSession().getAttribute("loggedReimbId")));
            System.out.println("This should say approve: " + req.getSession().getAttribute("loggedChoiceToApproveOrDeny"));

            System.out.println("Got attribute successful");
            /**
             * Assign a temporary reimbursement object to the matching reimbursement in the DB
             */
            ErsReimbursement ersReimbursement = reimbRepo.findById(reimbId)
                                    .orElseThrow(AuthenticationException::new);
            System.out.println("this is the attribute used to approve: " + reimbId);

            /**
             * Update the reimbursement in the database
             * First, set the status to approved
             */
            System.out.println("about to set the status to approved");
            ersReimbursement.setReimbursementStatusId(Status.APPROVED); // TODO says Id in field name but is an enum constant
            System.out.println("set the status to approved");
            System.out.println("This should say approved: " + ersReimbursement.getReimbursementStatusId());
            /**
             * Then, set the resolved field in the service layer to the current time
             */
            Date date = new Date();
            Timestamp resolved = new Timestamp(date.getTime()); // convert integer entered to type enum value
            ersReimbursement.setResolved(resolved);
            /**
             * Finally, set the resolver ID to the current session's userID
             */
            String fManagerUsername = String.valueOf(req.getSession().getAttribute("loggedUsername"));
            ErsUser resolver = userRepo.findUserByUsername(fManagerUsername)
                                .orElseThrow(AuthenticationException::new); // .orElseThrow bc findUserByUsername returns an optional
            ersReimbursement.setResolverId(resolver.getId());

            /**
             * Update the changed in the DB
             */
            reimbRepo.updateByFManager(ersReimbursement); // should use ReimbService object instead?
            System.out.println("reimbursement approved");


            return "/html/fmanager/fmanagerdash.html";

        } catch (NullPointerException npe) {
            npe.printStackTrace();
            System.out.println("NullPointer Exception!");
            return "/api/badlogin.html";
        }

    }
}
