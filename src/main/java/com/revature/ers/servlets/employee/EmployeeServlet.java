package com.revature.ers.servlets.employee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class EmployeeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String username = session.getAttribute("loggedUsername").toString();
        System.out.println("current user's username is: " + username);

        String currentReimb = session.getAttribute("loggedReimbursement").toString();
        System.out.println("current user's current reimbursement is: " + currentReimb);

        resp.getWriter().write("Welcome, " + username);
        resp.getWriter().write("Your current reimbursement is: " + currentReimb);

    }
}
