package com.revature.ers.controllers.employee;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Status;
import com.revature.ers.models.Type;
import com.revature.ers.repos.ReimbRepository;
import com.revature.ers.services.ReimbService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.sql.Timestamp;

/**
 * SubmitReimbController performs the CREATE operation on the project1 schema.
 * This controller should only be accessible to users with role EMPLOYEE
 */
public class SubmitController {

    private static ReimbRepository reimbRepo = new ReimbRepository();
    private static ReimbService reimbService = new ReimbService(reimbRepo);


    public static String submit(HttpServletRequest req) {

        System.out.println("In submit method");

        if(!req.getMethod().equals("POST")) {
            return "/html/employee/submitform.html";
        }

        System.out.println("Still here");

        // acquire the form data
        String amount = req.getParameter("amount");
        String reimbType = req.getParameter("reimb_type_id");
        String description = req.getParameter("description");

        Integer reimbTypeInt = Integer.parseInt(reimbType);
        Type reimbTypeAsType = Type.values()[reimbTypeInt];

        Date date = new Date();
        Timestamp submitted = new Timestamp(date.getTime()); // convert integer entered to type enum value
        // TODO drop down menu for types?

        Status reimbStatusAsStatus = Status.PENDING; // By default, status is pending at first

        System.out.println("Data entered...");

        // TODO set the author ID to the current User's user_id. Pass that into the construction
        // TODO of the reimbursement.

        // TODO ensure null values not entered

        System.out.println(amount + reimbType + description + submitted + reimbStatusAsStatus);

        ErsReimbursement reimbursement = new ErsReimbursement(Double.parseDouble(amount), reimbTypeAsType, description, submitted, reimbStatusAsStatus);
        System.out.println(reimbursement);

        try {

            System.out.println("In try block...");

            reimbService.register(reimbursement);

            System.out.println("Out of register method...");

            req.getSession().setAttribute("loggedAmount", amount);
            req.getSession().setAttribute("loggedType", reimbType);
            req.getSession().setAttribute("loggedDescription", description);
            req.getSession().setAttribute("loggedSubmitted", submitted);
            req.getSession().setAttribute("loggedStatus", reimbStatusAsStatus);

            System.out.println("Attributes set...");


            return "/html/employee/employeedash.html";

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/api/badlogin.html";
        }
    }
}
