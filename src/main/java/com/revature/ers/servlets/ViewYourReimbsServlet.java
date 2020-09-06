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

    /**
     * Check that the reimbursement is PENDING,
     * Or only show pending reimbursements/have a different
     * servlet to show reimbursements that are already
     * approved/denied. Right now, an employee can update
     * regardless.
     *
     */

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
                .orElseThrow(AuthenticationException::new);

        // NON SESSION SYNTAX
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        if(ersUser != null) {
            out.println("This is text!");

            out.println("<h1>Name: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
            out.println("<b>\tEmail: " + ersUser.getEmail() + "</b><br>");
            out.println("<i>\tRole: " + ersUser.getRole() + "</i><br>");
            out.println("<form method=\"post\" action=\"/farren_springer_p1/api/chooseToEdit\">\n" +
                    "            <p>Enter the ID number of the reimbursement you would like it edit</p>\n" +
                    "            <input placeholder=\"enter the ID of the reimbursement to view/edit\" name=\"reimbIdChosenByEmployee\"/><br>\n" +
                    "            <p>Enter either update or remove to edit your reimbursement</p>\n" +
                    "            <p>(If you wish to go back to your dashboard, type back)</p>\n" +
                    "            <input placeholder=\"Enter text\" name=\"choseToEditEmployee\"/><br>\n" +
                    "            <input type=\"submit\" value=\"Choose\"/><br>\n" +
                    "        </form>");
            out.println("<form method=\"post\" action=\"/farren_springer_p1/api/home\" class=\"any\">\n" +
                    "        <input type = \"submit\" value=\"Go Back\" class=\"any\"><br>\n" +
                    "    </form>");
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
