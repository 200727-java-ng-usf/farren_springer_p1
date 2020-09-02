package com.revature.ers.servlets.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // TODO switch case? For either view reimbursements or submit one

        HttpSession session = req.getSession();

        String username = session.getAttribute("loggedUsername").toString();
        System.out.println("current user's username is: " + username);

        resp.getWriter().write("Welcome, " + username + "\n");

        System.out.println("In the Admin Servlet's doPost method...");

    }
}
