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




        try {

            // acquire the form data
            String newUsername = req.getParameter("username");
            String newPassword = req.getParameter("password");
            String newFirstName = req.getParameter("firstName");
            String newLastName = req.getParameter("lastName");
            String newEmail = req.getParameter("email");

            /**
             * Find the user using the logged ID attribute from the form on the finduser page
             */
            System.out.println("userIdToEdit is: " + req.getSession().getAttribute("loggedIdOfUserToEdit"));
            ErsUser ersUser = userRepo.findById((Integer) req.getSession().getAttribute("loggedIdOfUserToEdit"));

            /**
             * If statements makes sure that fields can be left empty.
             * This would be if the admin only wanted to change the
             * user's email, for example.
             */

            System.out.println("values collected are: " + newUsername + newPassword + newFirstName + newLastName + newEmail);
            System.out.println(newUsername);
            System.out.println(ersUser.getEmail());

            if(ersUser.getUsername() != newUsername && newUsername != null) {
                System.out.println("In the change username update statement!");
                ersUser.setUsername(newUsername);
            }
            if(ersUser.getPassword() != newPassword && newPassword != null) {
                System.out.println("In the change password update statement!");
                ersUser.setPassword(newPassword);
            }
            if(ersUser.getFirstName() != newFirstName && newFirstName != null) {
                System.out.println("In the change firstName update statement!");
                ersUser.setFirstName(newFirstName);
            }
            if(ersUser.getLastName() != newLastName && newLastName != null) {
                System.out.println("In the change last name update statement!");
                ersUser.setLastName(newLastName);
            }
            if(ersUser.getEmail() != newEmail && newEmail != null) {
                System.out.println("In the change email update statement!");
                ersUser.setEmail(newEmail);
            }

//            ersUser.setUsername(newUsername);
//            ersUser.setPassword(newPassword);
//            ersUser.setFirstName(newFirstName);
//            ersUser.setLastName(newLastName);
//            ersUser.setEmail(newEmail);

            System.out.println("about to update");
            userService.update(ersUser);

            return "/api/home";

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/api/badlogin.html";
        }

    }
}
