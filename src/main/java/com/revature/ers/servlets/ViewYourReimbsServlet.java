package com.revature.ers.servlets;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;
import com.revature.ers.models.Status;
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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static com.revature.ers.services.UserService.app;

/**
 * This servlet will only display PAST reimbursements, so
 * reimbursements whose status is NOT pending
 */

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
//            out.println("This is text!");

            out.println("<h1>Name: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
            out.println("<b>\tEmail: " + ersUser.getEmail() + "</b><br>");
            out.println("<i>\tRole: " + ersUser.getRole().toString() + "</i><br>");

        } else {
            out.println("Can't find you");
        }

        /**
         * This will print all the reimbursements that the employee has
         * to the screen.
         */
        if(!reimbRepo.findAllReimbsByAuthorId(ersUser.getId()).isEmpty()) {
            /**
             * For each reimbursement with the author's ID...
             */
            for (ErsReimbursement r : reimbRepo.findAllReimbsByAuthorId(ersUser.getId())) {
                /**
                 * If the reimbursement is pending...
                 */
                if (r.getReimbursementStatusId() != Status.PENDING) {
                    /**
                     * Print the toString for employees.
                     */
                    out.println("<p>" + r.toString() + "</p>"); // TODO link here to Servlet with details?
                }

            }
        } else {
            out.println("<p>" + "No Reimbursements found" + "</p>"); // add button to submit a reimbursement here?
        }

        /**
         * Print the go back link
         */
        out.println("<div>\n" +
                "            <a href=\"/farren_springer_p1/api/home\">Go Back</a>\n" +
                "        </div>");

        out.println("</body>");
        out.println("<link rel=\"stylesheet\" href=\"/farren_springer_p1/css/mystyles.css\">");
        File file = new File("/farren_springer_p1/css/mystyles.css");
        System.out.println(file.getPath());
        System.out.println(file.exists());
        out.println("</html>");

//        resp.getWriter().write("<h1>Helper Session Servlet! doGet</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }
}
