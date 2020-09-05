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

public class UpdateUserController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String updateUser(HttpServletRequest req) throws IOException {

        if(!req.getMethod().equals("POST")) {
            return "/html/updateuser.html";
        }

        // TODO authenticate that the user's role field is ADMIN

        // acquire the form data
        String newUsername = req.getParameter("username");
        String newPassword = req.getParameter("password");
        String newFirstName = req.getParameter("firstName");
        String newLastName = req.getParameter("lastName");
        String newEmail = req.getParameter("email");


        try {

            userService.update(userRepo.findById((Integer) req.getAttribute("loggedIdOfUserToEdit")));

//            req.getSession().setAttribute("newUsername", username);
//            req.getSession().setAttribute("newPassword", password);
//            req.getSession().setAttribute("newFirstName", firstName);
//            req.getSession().setAttribute("newLastName", lastName);
//            req.getSession().setAttribute("newEmail", email);




            return "/api/home";

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/api/badlogin.html";
        }

    }
}
