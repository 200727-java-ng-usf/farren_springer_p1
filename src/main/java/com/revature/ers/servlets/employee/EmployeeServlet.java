package com.revature.ers.servlets.employee;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/employeeWelcome")
public class EmployeeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // TODO switch case? For either view reimbursements or submit one

        HttpSession session = req.getSession();

        String username = session.getAttribute("loggedUsername").toString();
        System.out.println("current user's username is: " + username);

        resp.getWriter().write("Welcome, " + username + "\n");

        System.out.println("In the Employee Servlet's doPost method...");

    }
}
