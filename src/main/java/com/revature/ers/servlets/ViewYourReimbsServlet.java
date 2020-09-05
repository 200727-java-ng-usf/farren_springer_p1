package com.revature.ers.servlets;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;
import com.revature.ers.repos.ReimbRepository;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.ReimbService;
import com.revature.ers.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static com.revature.ers.services.UserService.app;

@WebServlet("/viewYourReimbs")
public class ViewYourReimbsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo);
        ReimbRepository reimbRepo = new ReimbRepository();
        ReimbService reimbService = new ReimbService(reimbRepo);

        // SESSION SYNTAX
        HttpSession session = req.getSession();
//        ErsUser ersUser = (ErsUser) session.getAttribute("currentUser");
        String username = String.valueOf(session.getAttribute("loggedUsername"));
        ErsUser ersUser = userRepo.findUserByUsername(username)
                .orElseThrow(AuthenticationException::new);;

        // NON SESSION SYNTAX
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        if(ersUser != null) {
            out.println("This is text!");

            out.println("<h1>Name: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
            out.println("<b>\tEmail: " + ersUser.getEmail() + "</b><br>");
            out.println("<i>\tRole: " + ersUser.getRole() + "</i><br>");
            out.println("<b>\tYour Reimbursements: " + reimbRepo.findAllReimbsByAuthorId(ersUser.getId()) + "</b><br>");

        } else {
            out.println("Can't find you");
        }

        out.println("</body>");
        out.println("</html>");

        resp.getWriter().write("<h1>Helper Session Servlet! doGet</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }
}
