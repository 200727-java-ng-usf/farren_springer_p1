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
import java.io.IOException;
import java.io.PrintWriter;

import static com.revature.ers.services.UserService.app;

@WebServlet("/viewYourPendingReimbs")
public class ViewYourPendingReimbsServlet extends HttpServlet {

    /**
     * Only Shows Pending Reimbursements by user logged by session.
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

            out.println("<div>");
            out.println("<h1 class=\"anyButEvenSmaller\">Name: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
            out.println("<b class=\"anyButPrettySmall\">\tEmail: " + ersUser.getEmail() + "</b><br>");
            out.println("<i class=\"anyButPrettySmall\">\tRole: " + ersUser.getRole().toString() + "</i><br>");
            out.println("</div>");

            out.println("<div>");
            out.println("<form method=\"post\" action=\"/farren_springer_p1/api/chooseToEdit\">\n" +
                    "            <p style=\"FONT-SIZE: 20PX;\">Enter the ID number of the reimbursement you would like it edit</p>\n" +
                    "            <input placeholder=\"enter the ID of the reimbursement to view/edit\" name=\"reimbIdChosenByEmployee\"/><br>\n" +
                    "            <p style=\"FONT-SIZE: 20PX;\">Enter either update or remove to edit your reimbursement</p>\n" +
//                    "            <p>(If you wish to go back to your dashboard, type back)</p>\n" + // button to go back. Don't need this
                    "            <input placeholder=\"Enter text\" name=\"choseToEditEmployee\"/><br>\n" +
                    "            <input type=\"submit\" value=\"Choose\"/><br>\n" +
                    "        </form>");
            out.println("</div>");
//            out.println("<form method=\"post\" action=\"/farren_springer_p1/api/home\" class=\"any\">\n" +
//                    "        <input type = \"submit\" value=\"Go Back\" class=\"any\"><br>\n" +
//                    "    </form>");
//            out.println("<b>\tYour Reimbursements: " + reimbRepo.findAllPendingReimbsByAuthorId(ersUser.getId()) + "</b><br>");

        } else {
            out.println("Can't find you");
        }

        /**
         * This will print all the reimbursements that the employee has
         * to the screen.
         */
        if(!reimbRepo.findAllReimbsByAuthorId(ersUser.getId()).isEmpty()) {
            out.println("<p style=\"FONT-SIZE: 40PX;\">" + "Pending: \n" + "</p>");
            /**
             * For each reimbursement in the list, print it to the browser
             */
            for (ErsReimbursement r : reimbRepo.findAllReimbsByAuthorId(ersUser.getId())) {
                /**
                 * If the reimbursement is pending...
                 */
                if (r.getReimbursementStatusId() == Status.PENDING) {
                    /**
                     * Use the toString for employees that starts with "Pending."
                     */
                    out.println("<p class=\"anyButPrettySmall\" style=\"FONT-SIZE: 25PX;\">" + r.toStringOnlyUseThisForPending() + "</p>");
                }

            }
        } else {
            out.println("<p>" + "No Reimbursements found" + "</p>");
        }

        /**
         * Print the go back link
         */
        out.println("<div>\n" +
                "            <a href=\"/farren_springer_p1/api/home\">Go Back</a>\n" +
                "        </div>");

        out.println("</body>");
        out.println("<link rel=\"stylesheet\" href=\"/farren_springer_p1/css/mystyles.css\">");
        out.println("</html>");

//        resp.getWriter().write("<h1>Helper Session Servlet! doGet</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }
}
