package com.revature.ers.controllers;

import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;

import static com.revature.ers.services.UserService.app;

public class LoginController {

    public static String login(HttpServletRequest req) {

        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo);

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

            req.getSession().setAttribute("loggedUsername", username);
            req.getSession().setAttribute("loggedPassword", password);
            return "/api/home";

        } catch (Exception e) { // TODO custom exception
            e.printStackTrace();
            return "/api/badlogin.html";
        }

//        if(!(username.equals("cheese") && password.equals("louise"))) {
//            // this logic will trigger when the username and password are incorrect
//            return"/api/wrongcreds";
//        } else {
//            /**
//             * In YOUR project, you'll probably be storing the entire user object in the
//             * session (which will ALSO contain other user information like: role, likes, comments, etc)
//             */
//            req.getSession().setAttribute("loggedUsername", username);
//            req.getSession().setAttribute("loggedPassword", password);
//            return "/api/home";
//        }

//            return null;
    }

}
