package com.revature.ers.controllers.employee;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.Status;
import com.revature.ers.models.Type;
import com.revature.ers.repos.ReimbRepository;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.ReimbService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

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
