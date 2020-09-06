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
 * DeleteUserController performs the DELETE operation on the project1 schema.
 * This controller should only be accessible to users with role ADMIN
 */
public class DeleteUserController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String deleteUser(HttpServletRequest req) throws IOException {

        System.out.println("In deleteUser method in DeleteUserController...");



        if(!req.getMethod().equals("POST")) {
            return "/html/admin/deleteuser.html";
        }

        // TODO check for ADMIN role by req.getSession().getAttribute("loggedUsername)

        // Don't need to acquire any form data

        try {

            /**
             * To delete the user, first access the user.
             * Get the ID that the Admin entered on the form
             */
            System.out.println("attribute: " + req.getSession().getAttribute("loggedIdOfUserToEdit"));
            /**
             * Assign an ErsUser object to the fields that match that user's fields in the DB
             */
            ErsUser tempUser = userRepo.findById((Integer) req.getSession().getAttribute("loggedIdOfUserToEdit"));
            /**
             * Get that user's ID.
             */
            // TODO redundant?
            Integer tempUserId = tempUser.getId();
            System.out.println("this is the attribute used to delete: " + tempUserId);
            ErsUser ersUser = userRepo.findById((Integer) req.getSession().getAttribute("loggedIdOfUserToEdit"));
            System.out.println("Got attribute successful");

            /**
             * Delete the user
             */
            userService.deleteUser(ersUser);
            System.out.println("user deleted");

            /**
             * Return to the dashboard
             */
            return "/html/admin/admindash.html";

        } catch (NullPointerException npe) {
            npe.printStackTrace();
            System.out.println("NullPointer Exception!");
            return "/api/badlogin.html";
        }

    }
}
