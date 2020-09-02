package com.revature.ers.controllers.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.repos.ReimbRepository;
import com.revature.ers.services.ReimbService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.revature.ers.services.UserService.app;

/**
 * TODO JSON tokens for viewing?
 */
public class ViewMyReimbsController {

//    public static void marsFinder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//
//        ReimbRepository reimbRepo = new ReimbRepository();
//        ReimbService reimbService = new ReimbService(reimbRepo);
//
//        System.out.println("In viewMyReimbursements method in ViewMyReimbsController");
//        System.out.println(app.getCurrentUser());
//
//        Set<ErsReimbursement> tempReimbs = new HashSet<>();
//        tempReimbs = reimbService.findReimbursements(app.getCurrentUser().getId());
//
//        ErsReimbursement tempReimb = tempReimbs[0];
//
//        resp.getWriter().write(new ObjectMapper().writeValueAsString(tempReimbs);
//    }
}
