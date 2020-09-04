package com.revature.ers.controllers;

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

/**
 * Delete this class if you don't need anything from it. Nothing points to it right now.
 */
public class EmployeeController {

    public static String displayDash(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("In displayDash in EmployeeController...");

        // ensure that the method is a POST http method, else send them back to the login page
        if(!req.getMethod().equals("POST")) {
            return "/html/employeedash.html";
        }

//        req.getSession().setAttribute("loggedAuthorId", req.getSession().getAttribute("loggedUsername"));

        try {

            return "/html/employeedash.html";

        } catch (Exception e) {
            e.printStackTrace();
            return "/html/badlogin.html";
        }

    }
}
