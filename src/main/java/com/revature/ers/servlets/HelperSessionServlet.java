package com.revature.ers.servlets;

import com.revature.ers.models.ErsUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class HelperSessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // SESSION SYNTAX
        HttpSession session = req.getSession();
        ErsUser ersUser = (ErsUser) session.getAttribute("currentUser");

        // NON SESSION SYNTAX
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        if(ersUser != null) {
            out.println("This is text!");

            out.println("<h1>Name: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
            out.println("<b>\tEmail: " + ersUser.getEmail() + "</b><br>");
            out.println("<i>\tRole: " + ersUser.getRole() + "</i><br>");
        } else {
            out.println("Can't find you");
        }

        out.println("</body></html>");

        resp.getWriter().write("<h1>Helper Session Servlet! doGet</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }
}
