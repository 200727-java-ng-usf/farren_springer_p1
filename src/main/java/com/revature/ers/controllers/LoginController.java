package com.revature.ers.controllers;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.Role;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.revature.ers.services.UserService.app;

/**
 * The LoginController takes users to a different HTML page based on their Role.
 * LoginController is used for all User cases, so it is not in a more specific package.
 */
public class LoginController {

    public static String login(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo);
        Role userRole;
        System.out.println("in login method in LoginController");


        /**
         * You may want to implement route guarding for your endpoints
         *
         * for example,
         * filter(doctor in some way, then pass it on) OR (does this have this token? then move on or
         * return to sender, saying "you don't have the credentials for this) OR you can filter something
         * on the way back, by refactoring a response. Modularizes logic for request and response
         *
         * for example, you may want to make sure only ADMINS can access admin endpoints
         */
        // ensure that the method is a POST http method, else send them back to the login page
        // need to do this because the user will be entering form data on this page
        if(!req.getMethod().equals("POST")) {
            return "/html/login.html";
        }

        // acquire the form data
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // try to authenticate the user before moving forward
        try {

            System.out.println("about to try to authenticate...");
            userService.authenticate(username, password);
            System.out.println("authentication successful...");
            userRole = app.getCurrentUser().getRole();
            System.out.println("set the current user");
            req.getSession().setAttribute("loggedUsername", username);
            String currentUserRole = String.valueOf(userRepo.findUserByUsername(String.valueOf(req.
                                        getSession().getAttribute("loggedUsername"))));
            req.getSession().setAttribute("loggedCurrentUserRole", currentUserRole);
            req.getSession().setAttribute("loggedPassword", password);
//            return "/api/home";


            switch(userRole) {
                case EMPLOYEE:
                    System.out.println("In employee case");

                    return "/html/employee/employeedash.html";
                case FINANCE_MANAGER:
                    System.out.println("In finance manager case");

                    return "/html/fmanager/fmanagerdash.html";
                case ADMIN:
                    System.out.println("In admin case");

                    return "/html/admin/admindash.html";
                default:
                    System.out.println("user does not seem to have a role...");
                    // TODO make the html document display the user
                    return "/html/badlogin.html";
            }

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/api/badlogin.html";
        }

    }

}
