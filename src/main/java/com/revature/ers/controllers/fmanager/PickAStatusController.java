package com.revature.ers.controllers.fmanager;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PickAStatusController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String viewByStatus(HttpServletRequest req) throws IOException {

        System.out.println("In viewByStatus method in ViewAllReimbsByStatusController");

        if(!req.getMethod().equals("POST")) {
            return "/html/fmanager/status.html";
        }

        // TODO authenticate that the user's role field is FINANCE_MANAGER

        // acquire the form data
        System.out.println("about to acquire the form data...");
        String statusChoice = req.getParameter("statusChoice");
        System.out.println("acquired the form data...");
        System.out.println("form data acquired is: " + statusChoice);
        System.out.println("form data from a while ago is: " + req.getSession().getAttribute("loggedUsername"));

        try {
            System.out.println("about to set the attribute...");
            req.getSession().setAttribute("loggedStatusChoice", statusChoice);
            System.out.println("attribute set...");

            return "/viewallbystatus"; // TODO why this does not map to servlet

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/api/badlogin.html";
        }




    }
}
