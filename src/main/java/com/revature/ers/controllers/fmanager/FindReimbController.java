package com.revature.ers.controllers.admin;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.repos.ReimbRepository;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.ReimbService;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static com.revature.ers.services.UserService.app;

public class FindReimbController {

    private static ReimbRepository reimbRepo = new ReimbRepository();
    private static ReimbService reimbService = new ReimbService(reimbRepo);

    public static String findUser(HttpServletRequest req) throws IOException {

        if(!req.getMethod().equals("POST")) {
            return "/html/admin/findreimb.html";
        }

        // TODO authenticate that the user's role field is ADMIN

        // acquire the form data
        String reimbIdToEditAsString = req.getParameter("reimbIdToEdit");
        Integer reimbIdToEdit = Integer.parseInt(reimbIdToEditAsString);

        if(reimbRepo.findById(reimbIdToEdit) != null) {

            System.out.println("found reimb successful");

            try {

                System.out.println("in try in if in findUser method");

                req.getSession().setAttribute("loggedIdOfReimbToEdit", reimbIdToEdit);

                System.out.println("getSession.setAttribute successful. The attribute is: " + reimbIdToEdit + ". and as a string: " + reimbIdToEditAsString);

                return "/html/fmanager/approveordeny.html";

            } catch (NullPointerException npe) {
                npe.printStackTrace();
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
