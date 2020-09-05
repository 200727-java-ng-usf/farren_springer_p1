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

public class DeleteUserController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String deleteUser(HttpServletRequest req) throws IOException {

        System.out.println("In deleteUser method in DeleteUserController...");

        if(!req.getMethod().equals("POST")) {
            return "/html/admin/deleteuser.html";
        }

        // TODO authenticate that the user's role field is ADMIN

        // Don't need to acquire any form data


        try {

            /**
             * Find the user using the logged ID attribute from the form on the finduser page
             */
            System.out.println("attribute: " + req.getSession().getAttribute("loggedIdOfUserToEdit"));
            ErsUser tempUser = userRepo.findById((Integer) req.getSession().getAttribute("loggedIdOfUserToEdit"));
            Integer tempUserId = tempUser.getId();
            System.out.println("this is the attribute used to delete: " + tempUserId);
            ErsUser ersUser = userRepo.findById((Integer) req.getSession().getAttribute("loggedIdOfUserToEdit"));
            System.out.println("Got attribute successful");

            userService.deleteUser(ersUser);
            System.out.println("user deleted");

//            req.getSession().setAttribute("newUsername", username);
//            req.getSession().setAttribute("newPassword", password);
//            req.getSession().setAttribute("newFirstName", firstName);
//            req.getSession().setAttribute("newLastName", lastName);
//            req.getSession().setAttribute("newEmail", email);


            return "/html/admin/admindash.html";

        } catch (NullPointerException npe) {
            npe.printStackTrace();
            System.out.println("NullPointer Exception!");
            return "/api/badlogin.html";
        }

    }
}
