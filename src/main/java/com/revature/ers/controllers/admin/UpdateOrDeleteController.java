package com.revature.ers.controllers.admin;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static com.revature.ers.services.UserService.app;

public class UpdateOrDeleteController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String updateOrDelete(HttpServletRequest req) throws IOException {

        if(!req.getMethod().equals("POST")) {
            return "/html/updateordelete.html";
        }

        // TODO authenticate that the user's role field is ADMIN

        // acquire the form data
        String updateOrDeleteChoice = req.getParameter("updateOrDeleteChoice");

        if(userRepo.findById(Integer.parseInt(updateOrDeleteChoice)) != null) {

            try {
                req.getSession().setAttribute("loggedChoiceToUpdateOrDelete", updateOrDeleteChoice);
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return "/api/badlogin.html";
            }

            switch(updateOrDeleteChoice) {
                case "update":
                    return "/api/update";
                case "delete":
                    return "/api/delete";
                default:
                    System.out.println("in default case");
                    return "/html/badlogin.html";
            }

        } else {
            return "/api/finduser.html";
        }

    }
}
