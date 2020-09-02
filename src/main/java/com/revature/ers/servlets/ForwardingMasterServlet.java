package com.revature.ers.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/*")
public class ForwardingMasterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("String").forward(req,resp);
        System.out.println("in doGet in ForwardingMasterServlet");
        req.getRequestDispatcher(RequestHelper.process(req, resp)).forward(req, resp);
//        resp.getWriter().write("in get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(RequestHelper.process(req, resp)).forward(req, resp);
//        resp.getWriter().write("in post");
        System.out.println("in doPost in ForwardingMasterServlet");
    }
}
