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

public class SubmitController {

    private static ReimbRepository reimbRepo = new ReimbRepository();
    private static ReimbService reimbService = new ReimbService(reimbRepo);


    public static String submit(HttpServletRequest req) {

        System.out.println("In submit method");

        if(!req.getMethod().equals("POST")) {
            return "/html/submitform.html";
        }

        System.out.println("Still here");

        // acquire the form data
        String amount = req.getParameter("amount");
        String reimbType = req.getParameter("reimb_type_id");
        String description = req.getParameter("description");

        Integer reimbTypeInt = Integer.parseInt(reimbType);
        Type reimbTypeAsType = Type.values()[reimbTypeInt];

        Date date = new Date();
        Timestamp submitted = new Timestamp(date.getTime());

        Status reimbStatusId = Status.PENDING; // By default, status is pending at first

        System.out.println("Data entered...");

        // TODO set the author ID to the current User's user_id. Pass that into the construction
        // TODO of the reimbursement.


//        /**
//         * Ensure not null values are submitted
//         */
//        if(!(amount.equals(null) || reimbType.equals(""))) {
//            System.out.println("whoops");
//            // this logic will trigger when the amount is null or the type is empty
//            return"/api/invalidinput";
//        }
        System.out.println(amount + reimbType + description + submitted + reimbStatusId);

        ErsReimbursement reimbursement = new ErsReimbursement(Double.parseDouble(amount), reimbTypeAsType, description, submitted, reimbStatusId);
        System.out.println(reimbursement);

        try {

            System.out.println("In try block...");

            reimbService.register(reimbursement);

            System.out.println("Out of register method...");

            req.getSession().setAttribute("loggedAmount", amount);
            req.getSession().setAttribute("loggedType", reimbType);
            req.getSession().setAttribute("loggedDescription", description);
            req.getSession().setAttribute("loggedSubmitted", submitted);
            req.getSession().setAttribute("loggedStatus", reimbStatusId);

            System.out.println("Attributes set...");


            return "/api/home";

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/api/badlogin.html";
        }
    }
}
