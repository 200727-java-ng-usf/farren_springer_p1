package com.revature.ers.controllers.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;
import com.revature.ers.repos.ReimbRepository;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.ReimbService;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.revature.ers.services.UserService.app;

public class EmployeeController {

    public static String displayDash(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo);
        ReimbRepository reimbRepo = new ReimbRepository();
        ReimbService reimbService = new ReimbService(reimbRepo);
        String userSelection;
        Set<Optional<ErsReimbursement>> currentReimbs = new HashSet<>(); // employee's reimbs
        // TODO method that returns all reimbursements to pass back to this hashSet

        // ensure that the method is a POST http method, else send them back to the login page
        if(!req.getMethod().equals("POST")) {
            return "/html/login.html";
        }

        req.getSession().setAttribute("loggedAuthorId", req.getSession().getAttribute("username"));

        try {

            reimbService.findReimbursement(app.getCurrentUser().getId());

            System.out.println("set the current reimbursement based on the user");

            req.getSession().setAttribute("loggedReimbursement", app.getCurrentReimbursement());

            System.out.println("logged the current reimbursement from the service layer");

            // Maybe use Fetch API instead in HTML
            return "/employeeWelcome";

        } catch (Exception e) {
            e.printStackTrace();
            return "/html/badlogin.html";
        }



//        // acquire the form data
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//
//        String username = req.getParameter("loggedUsername"); // not needed?
//
//        resp.sendRedirect("EmployeeServlet");
//        return "/farren_springer_p1/employee";

    }
}
