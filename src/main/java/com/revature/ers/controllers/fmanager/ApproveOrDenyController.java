package com.revature.ers.controllers.fmanager;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static com.revature.ers.services.UserService.app;

public class ApproveOrDenyController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String approveOrDeny(HttpServletRequest req) throws IOException {

        System.out.println("In approveOrDeny method in ApproveOrDenyController");

        if(!req.getMethod().equals("POST")) {
            return "/html/admin/updateordelete.html";
        }

        // TODO authenticate that the user's role field is FINANCE_MANAGER

        // acquire the form data
        System.out.println("about to acquire the form data...");
        String approveOrDenyChoice = req.getParameter("approveOrDenyChoice");
        Integer reimbId = Integer.parseInt(req.getParameter("reimbIdChosenByFManager"));
        System.out.println("acquired the form data...");
        System.out.println("form data acquired is: " + approveOrDenyChoice + "and" + reimbId);
        System.out.println("form data from a while ago is: " + req.getSession().getAttribute("loggedUsername"));

        try {
            System.out.println("about to set the attribute...");
            req.getSession().setAttribute("loggedChoiceToApproveOrDeny", approveOrDenyChoice);
            System.out.println("attribute set...");
            System.out.println("about to set the id to a session attribute called loggedReimbId");
            req.getSession().setAttribute("loggedReimbId", reimbId);
            System.out.println("reimbId attribute set");

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/api/badlogin.html";
        }

        switch(approveOrDenyChoice) {
            case "approve":
                System.out.println("approve case");
                return "/html/fmanager/approvereimb.html";
            case "deny":
                System.out.println("deny case");
                return "/html/fmanager/denyreimb.html";
            default:
                System.out.println("in default case");
                return "/html/badlogin.html";
        }


    }
}
