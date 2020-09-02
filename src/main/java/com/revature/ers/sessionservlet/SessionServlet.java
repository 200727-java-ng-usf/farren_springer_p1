package com.revature.ers.sessionservlet;

import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

// TODO understand how this works

@WebServlet("/sesserv")
public class SessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ErsUser ersUser = new ErsUser("jdoe", "password", "John", "Doe", "jdoe@gmail.com");

        // SESSION SYNTAX HERE

        // this method will get the current OR create one if there is no session
        // req.getSession(false); will return null if there is no session, it will NOT create one
        HttpSession session = req.getSession();

//        session.setAttribute("currentUser", ersUser);

        // THIS STUFF HAS NOTHING TO DO WITH SESSION
        System.out.println("in session doGet");
        resp.getWriter().write("ErsUser is in session");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // extracting form data
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String firstName = req.getParameter("first name");
        String lastName = req.getParameter("last name");
        String email = req.getParameter("email");
        Role role = Role.valueOf(req.getParameter("role"));

        // regular java stuff
        ErsUser tempUser = new ErsUser(username, password, firstName, lastName, email);

        // session syntax
        HttpSession session = req.getSession();
        session.setAttribute("currentUser", tempUser);

        // print writer
        PrintWriter out = resp.getWriter();
        out.println("Hello, " + firstName);

        System.out.println("in session doPost");
    }
}
