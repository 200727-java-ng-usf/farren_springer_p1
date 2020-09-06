package com.revature.ers.controllers.fmanager;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This controller:
 * Gets the parameter from the form in typeorstatus.html,
 * sets the attribute of the session, and
 * takes you to a page based on the entry into the form in typeorstatus.html
 */
public class TypeOrStatusController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String directFromFilterChoice(HttpServletRequest req) throws IOException {

        System.out.println("In directFromFilterChoice method in TypeOrStatusController");

        if(!req.getMethod().equals("POST")) {
            return "/html/fmanager/typeorstatus.html";
        }

        // TODO authenticate that the user's role field is FINANCE_MANAGER

        // acquire the form data
        System.out.println("about to acquire the form data...");
        String typeOrStatusChoice = req.getParameter("typeOrStatusChoice");
        System.out.println("acquired the form data...");
        System.out.println("form data acquired is: " + typeOrStatusChoice);
        System.out.println("form data from a while ago is: " + req.getSession().getAttribute("loggedUsername"));

        try {
            System.out.println("about to set the attribute...");
            req.getSession().setAttribute("loggedTypeOrStatusChoice", typeOrStatusChoice);
            System.out.println("attribute set...");

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/api/badlogin.html";
        }

        switch(typeOrStatusChoice) {
            case "type":
                System.out.println("type case");
                return "/html/fmanager/type.html";
            case "status":
                System.out.println("status case");
                return "/html/fmanager/status.html";
            default:
                System.out.println("in default case");
                return "/html/badlogin.html";
        }


    }


}