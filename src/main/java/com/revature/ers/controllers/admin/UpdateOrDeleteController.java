package com.revature.ers.controllers.admin;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static com.revature.ers.services.UserService.app;

/**
 * UpdateOrDeleteController collects information from a form and leads the
 * admin user to either the UPDATE or DELETE user controller.
 * This controller should only be accessible to users with role ADMIN
 */
public class UpdateOrDeleteController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String updateOrDelete(HttpServletRequest req) throws IOException {

        System.out.println("In updateOrDelete method in UpdateOrDeleteController");

        if(!req.getMethod().equals("POST")) {
            return "/html/admin/updateordelete.html";
        }

        // TODO authenticate that the user's role field is ADMIN

        // acquire the form data
        System.out.println("about to acquire the form data...");
        String updateOrDeleteChoice = req.getParameter("updateOrDeleteChoice");
        System.out.println("acquired the form data...");
        System.out.println("form data acquired is: " + updateOrDeleteChoice);
        System.out.println("form data from a while ago is: " + req.getSession().getAttribute("loggedIdOfUserToEdit"));

            try {
                System.out.println("about to set the attribute...");
                req.getSession().setAttribute("loggedChoiceToUpdateOrDelete", updateOrDeleteChoice);
                System.out.println("attribute set...");

            } catch (AuthenticationException e) {
                e.printStackTrace();
                return "/api/badlogin.html";
            }

            switch(updateOrDeleteChoice) {
                case "update":
                    System.out.println("update case");
                    return "/html/admin/updateuser.html";
                case "delete":
                    System.out.println("delete");
                    return "/html/admin/deleteuser.html";
                default:
                    System.out.println("in default case");
                    return "/html/badlogin.html";
            }


    }
}
