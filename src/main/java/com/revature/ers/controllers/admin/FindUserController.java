package com.revature.ers.controllers.admin;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static com.revature.ers.services.UserService.app;

public class FindUserController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String findUser(HttpServletRequest req) throws IOException {

        if(!req.getMethod().equals("POST")) {
            return "/html/finduser.html";
        }

        // TODO authenticate that the user's role field is ADMIN

        // acquire the form data
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // authenticate sets the current user. We don't want to do that...just update the information
        // if the user exists in the db
//        userService.authenticate(username, password);
        if(userRepo.findUserByCredentials(username, password).isPresent()) {

            System.out.println("found user successful");

            try {

                System.out.println("in try in if in findUser method");

                req.getSession().setAttribute("loggedUsername", username);
                req.getSession().setAttribute("loggedPassword", password);

                System.out.println("getSession.setAttribute successful");

                return "/api/updateuser";

            } catch (AuthenticationException e) {
                e.printStackTrace();
                return "/api/badlogin.html";
            }
        } else {
            return "/api/finduser.html";
        }

//        /**
//         * Ensure not null values are submitted
//         */
//        if(!(username.equals(null) || password.equals(""))) {
//            // this logic will trigger when the amount is null or the type is empty
//            return"/api/invalidinput";
//        } else {
//            req.getSession().setAttribute("loggedUsername", username);
//            req.getSession().setAttribute("loggedPassword", password);
//            req.getSession().setAttribute("loggedFirstName", firstName);
//            req.getSession().setAttribute("loggedLastName", lastName);
//            req.getSession().setAttribute("loggedEmail", email);
//            ErsUser employee = new ErsUser(username, password, firstName, lastName, email);
//            userService.register(employee);
//            return "/api/home";
//        }
    }
}