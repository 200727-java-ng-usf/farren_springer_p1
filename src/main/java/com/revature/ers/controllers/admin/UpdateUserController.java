package com.revature.ers.controllers.admin;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import static com.revature.ers.services.UserService.app;

/**
 * UpdateUserController performs the UPDATE operation on the project1 schema.
 * This controller should only be accessible to users with role ADMIN
 */
public class UpdateUserController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String updateUser(HttpServletRequest req) throws IOException {

        System.out.println("in updateUser method in UpdateUserController...");

        if(!req.getMethod().equals("POST")) {
            return "/html/admin/updateuser.html";
        }

        // TODO authenticate that the user's role field is ADMIN

        // acquire the form data
        String newUsername = req.getParameter("username");
        String newPassword = req.getParameter("password");
        String newFirstName = req.getParameter("firstName");
        String newLastName = req.getParameter("lastName");
        String newEmail = req.getParameter("email");


        try {

            /**
             * Find the user using the logged ID attribute from the form on the finduser page
             */
            System.out.println("userIdToEdit is: " + req.getSession().getAttribute("loggedIdOfUserToEdit"));
            ErsUser ersUser = userRepo.findById((Integer) req.getSession().getAttribute("loggedIdOfUserToEdit"));
            ersUser.setUsername(newUsername);
            ersUser.setPassword(newPassword);
            ersUser.setFirstName(newFirstName);
            ersUser.setLastName(newLastName);
            ersUser.setEmail(newEmail);


            userService.update(ersUser);

            return "/api/home";

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/api/badlogin.html";
        }

    }
}
