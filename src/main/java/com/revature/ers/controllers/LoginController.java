package com.revature.ers.controllers;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.Role;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;

import static com.revature.ers.models.Role.*;
import static com.revature.ers.services.UserService.app;

public class LoginController {

    public static String login(HttpServletRequest req) {

        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo);
        Role userRole;

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
        if(!req.getMethod().equals("POST")) {
            return "/html/login.html";
        }

        // acquire the form data
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        /**
         * For YOUR project, you won't hardcode "cheese" and "louise"...you'll go to
         * the DB and find the ACTUAL password that should be used, based on the username
         * they typed in.
         */
        try {

            userService.authenticate(username, password);

            userRole = app.getCurrentUser().getRole();

            req.getSession().setAttribute("loggedUsername", username);
            req.getSession().setAttribute("loggedPassword", password);
//            return "/api/home";

            switch(userRole) {
                case EMPLOYEE:
                    System.out.println("In employee case");
                    // TODO make the html document display the user
                    return "/html/employeedash.html"; // TODO Forward here instead?
                case FINANCE_MANAGER:
                    System.out.println("In finance manager case");
                    // TODO make the html document display the user
                    return "/html/fmanagerdash.html";
                case ADMIN:
                    System.out.println("In admin case");
                    // TODO make the html document display the user
                    return "/html/admindash.html";
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
