package com.revature.ers.controllers.admin;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UpdateController {

    //    private static UserRepository userRepo = new UserRepository();
//    private static UserService userService = new UserService(userRepo);
//
    public static String updateUser(HttpServletRequest req) throws IOException {
        if (!req.getMethod().equals("POST")) {
            return "/html/updateuser.html";
        }
        return "/api/updateuser";
    }
}
//
//        if(!req.getMethod().equals("POST")) {
//            return "/html/finduser.html";
//        }
//
//        // TODO authenticate that the user's role field is ADMIN
//
//        // acquire the form data
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        String firstName = req.getParameter("firstName");
//        String lastName = req.getParameter("lastName");
//        String email = req.getParameter("email");
//        System.out.println("form data acquired");
//
//        try {
//
//            userService.update(userToUpdate, );
//
//            req.getSession().setAttribute("loggedUsername", username);
//            req.getSession().setAttribute("loggedPassword", password);
//            req.getSession().setAttribute("loggedFirstName", firstName);
//            req.getSession().setAttribute("loggedLastName", lastName);
//            req.getSession().setAttribute("loggedEmail", email);
//
//            return "/api/home";
//
//        } catch (AuthenticationException e) {
//            e.printStackTrace();
//            return "/api/badlogin.html";
//        }
//
////        /**
////         * Ensure not null values are submitted
////         */
////        if(!(username.equals(null) || password.equals(""))) {
////            // this logic will trigger when the amount is null or the type is empty
////            return"/api/invalidinput";
////        } else {
////            req.getSession().setAttribute("loggedUsername", username);
////            req.getSession().setAttribute("loggedPassword", password);
////            req.getSession().setAttribute("loggedFirstName", firstName);
////            req.getSession().setAttribute("loggedLastName", lastName);
////            req.getSession().setAttribute("loggedEmail", email);
////            ErsUser employee = new ErsUser(username, password, firstName, lastName, email);
////            userService.register(employee);
////            return "/api/home";
////        }
//    }

