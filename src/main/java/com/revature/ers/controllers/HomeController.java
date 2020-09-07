package com.revature.ers.controllers;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;
import com.revature.ers.repos.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Might not need this. Might remove home.html.
 */
public class HomeController {

    public static String home(HttpServletRequest req, HttpServletResponse resp) { // don't need HttpRequest object as param unless you are doing advanced logic

        System.out.println("redirecting to a user's home page (their dashboard)");

        UserRepository userRepo = new UserRepository();

        // a TON of business logic could go here
        String currentSessionUsername = String.valueOf(req.getSession().getAttribute("loggedUsername"));
        ErsUser currentUser = userRepo.findUserByUsername(currentSessionUsername)
                                .orElseThrow(AuthenticationException::new);
        Role roleOfUser = currentUser.getRole();

        System.out.println("still here");
        String roleOfCurrentUser = String.valueOf(req.getSession().getAttribute("loggedCurrentUserRole"));
        System.out.println("got roleOfCurrentUser string");
        System.out.println(roleOfCurrentUser);

        switch(roleOfUser) {
            case EMPLOYEE:
                System.out.println("in employee case");
                return "/html/employee/employeedash.html";
            case FINANCE_MANAGER:
                System.out.println("in fmanager case");
                return "/html/fmanager/fmanagerdash.html";
            case ADMIN:
                System.out.println("in admin case");
                return "/html/admin/admindash.html";
            default:
                return "/html/badlogin.html";
        }

    }
}
