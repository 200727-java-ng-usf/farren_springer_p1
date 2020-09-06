package com.revature.ers.controllers.employee;

import com.revature.ers.exceptions.AuthenticationException;

import com.revature.ers.repos.ReimbRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * RemoveReimbController performs the DELETE operation on the project1 schema.
 * This controller should only be accessible to users with role EMPLOYEE
 */
public class RemoveReimbController {

    public static String removeReimb(HttpServletRequest req) throws IOException {

        ReimbRepository reimbRepo = new ReimbRepository();

        System.out.println("In removeReimb method in RemoveReimb Controller");


//        if (!req.getMethod().equals("POST")) {
//            System.out.println("requested get method");
//            return "/html/employee/reimbwasremoved.html";
//        }

        try {

            System.out.println("In try in removeReimb method in RemoveReimbController");

//            ErsReimbursement ersReimbursement = reimbRepo.findById()
//                    .orElseThrow(NullPointerException::new);
            reimbRepo.deleteById(Integer.parseInt(String.valueOf(req.getSession().getAttribute("loggedReimbIdChosenByEmployee"))));
            System.out.println("reimb should be deleted");

            return "/html/employee/reimbwasremoved.html";

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/html/badlogin.html";
        }


    }
}
