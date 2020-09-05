package com.revature.ers.controllers.fmanager;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.repos.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class DirectFromFilterChoiceController {


    public static String directFromFilterChoice(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UserRepository userRepo = new UserRepository();

        // SESSION SYNTAX
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("loggedUsername");

        // NON SESSION SYNTAX
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        if (username != null) {


            out.println("Welcome, " + username + ", I do not have this page set up yet. I'm sorry.");

            ErsUser ersUser = userRepo.findUserByUsername(username)
                    .orElseThrow(AuthenticationException::new);

            out.println("<h1>Name: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
            out.println("<b>\tEmail: " + ersUser.getEmail() + "</b><br>");
            out.println("<i>\tRole: " + ersUser.getRole() + "</i><br>");

            // switch case here for different types based on form on previous page
            //         /farren_springer_p1/api/pickatype?reimbsbytypeorstatus=status
            //         /farren_springer_p1/api/pickatype?reimbsbytypeorstatus=type

            System.out.println("about to get the form data...");
            String reimbsByTypeOrStatus = req.getParameter("reimbsbytypeorstatus");
            System.out.println("got the form data...");

            System.out.println("about to set the session attribute...");
            req.getSession().setAttribute("loggedFilterSetting", reimbsByTypeOrStatus);
            System.out.println("set the session attribute...");

            switch (reimbsByTypeOrStatus) {
                case "type":
                    System.out.println("In employee case");
                    // TODO HTML doc to display username?
                    return "/html/allreimbsbytype.html";
                case "status":
                    System.out.println("In finance manager case");
                    // TODO make the html document display the user
                    return "/html/allreimbsbystatus.html";
                default:
                    System.out.println("invalid selection...should be type or status selected");
                    // TODO make the html document display the user
                    return "/html/badlogin.html";
            }
        } else{
            out.println("Can't find you");
        }

        out.println("</body></html>");

        resp.getWriter().write("<h1>Helper Session Servlet! doGet</h1>");

        return "/html/badlogin.html";

    }
}