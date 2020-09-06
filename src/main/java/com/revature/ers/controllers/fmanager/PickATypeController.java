package com.revature.ers.controllers.fmanager;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// TODO links
public class PickATypeController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String viewByType(HttpServletRequest req) throws IOException {

        System.out.println("In viewByType method in PickAType");

        if(!req.getMethod().equals("POST")) {
            return "/html/fmanager/type.html";
        }

        // TODO authenticate that the user's role field is FINANCE_MANAGER

        // acquire the form data
        System.out.println("about to acquire the form data...");
        String typeChoice = req.getParameter("typeChoice");
        System.out.println("acquired the form data...");
        System.out.println("form data acquired is: " + typeChoice);
        System.out.println("form data from a while ago is: " + req.getSession().getAttribute("loggedUsername"));

        try {
            System.out.println("about to set the attribute...");
            req.getSession().setAttribute("loggedTypeChoice", typeChoice);
            System.out.println("attribute set...in PickATypeController");

            return "/viewallbytype"; // TODO why this does not map to servlet

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/api/badlogin.html";
        }



    }
}
