package com.revature.ers.controllers.employee;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Type;
import com.revature.ers.repos.ReimbRepository;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import static com.revature.ers.services.UserService.app;

/**
 * UpdateReimbController performs the UPDATE operation on the project1 schema.
 * This controller should only be accessible to users with role EMPLOYEE
 */
public class UpdateReimbController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String updateReimb(HttpServletRequest req) throws IOException {

        ReimbRepository reimbRepo = new ReimbRepository();

        System.out.println("in updateReimb method in UpdateReimbController...");

        if(!req.getMethod().equals("POST")) {
            return "/html/employee/updatereimb.html";
        }

        // TODO authenticate that the user's role field is ADMIN

        // acquire the session data
        String newAmount = req.getParameter("newAmount");
        System.out.println("Parameter is: " + newAmount);
        String newDescription = req.getParameter("newDescription");
        System.out.println("Parameter is: " + newDescription);
        String newType = req.getParameter("newType");
        System.out.println("Parameter is: " + newType);


        try {

            System.out.println("in try in UpdateReimbController...");
            /**
             * Find the reimb using the logged ID attribute from the form on the viewmyreimbs page
             */
            System.out.println("reimb to edit is: " + req.getSession().getAttribute("loggedReimbIdChosenByEmployee"));
            ErsReimbursement ersReimbursement = reimbRepo.findById(Integer.parseInt(String.valueOf(req.getSession().getAttribute("loggedReimbIdChosenByEmployee"))))
                    .orElseThrow(NullPointerException::new);
            ersReimbursement.setAmount(Double.parseDouble(newAmount));
            ersReimbursement.setDescription(newDescription);

            switch (newType) {
                case "1":
                    ersReimbursement.setReimbursementTypeId(Type.LODGING);
                    break;
                case "2":
                    ersReimbursement.setReimbursementTypeId(Type.TRAVEL);
                    break;
                case "3":
                    ersReimbursement.setReimbursementTypeId(Type.FOOD);
                    break;
                case "4":
                    ersReimbursement.setReimbursementTypeId(Type.OTHER);
                    break;
                default:
                    System.out.println("in default case. Invalid type chosen!");
            }

            reimbRepo.update(ersReimbursement);

//            req.getSession().setAttribute("newUsername", username);
//            req.getSession().setAttribute("newPassword", password);
//            req.getSession().setAttribute("newFirstName", firstName);
//            req.getSession().setAttribute("newLastName", lastName);
//            req.getSession().setAttribute("newEmail", email);


            return "/html/employee/employeedash.html";

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/html/badlogin.html";
        }

    }
}
