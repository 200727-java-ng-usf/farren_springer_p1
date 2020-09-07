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

/**
 * This page only shows PAST reimbursements. // TODO can view for each employee?
 */
// TODO View all employee's servlet for admins and use the findUser html on that servlet
@WebServlet("/viewReimbs")
public class ViewAllReimbsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("In doGet of ViewAllReimbs Servlet!");

        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo);
        ReimbRepository reimbRepo = new ReimbRepository();
        ReimbService reimbService = new ReimbService(reimbRepo);

        // SESSION SYNTAX
        HttpSession session = req.getSession();
        String username = String.valueOf(session.getAttribute("loggedUsername"));
        ErsUser ersUser = userRepo.findUserByUsername(username)
                .orElseThrow(AuthenticationException::new);

        // NON SESSION SYNTAX
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        if(ersUser != null) {

            /**
             * This <div> just displays the user-in-session's information.
             */
            out.println("<div>");
            out.println("<h1 class=\"anyButEvenSmaller\">Name: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
            out.println("<b class=\"anyButPrettySmall\">\tEmail: " + ersUser.getEmail() + "</b><br>");
            out.println("<i class=\"anyButPrettySmall\">\tRole: " + ersUser.getRole().toString() + "</i><br>");
            out.println("</div>");


            // TODO make filter that doesn't include pending, because this page is only for reimbursements that are not pending
            out.println("<div>");
            out.println("<p class=\"anyButSmaller\"> Past Reimbursements: </p>");
            out.println("<form action=\"/farren_springer_p1/api/typeOrStatus\">\n" +
                    "        <input class = \"anyButSmaller\" style=\"height: 100px;\" type=\"submit\" value=\"Choose a Filter\">\n" +
                    "    </form>");
            out.println("</div>");


            /**
             * This div displays all of the reimbursements that have already been approved or denied.
             */
            out.println("<div>");
            if(!reimbService.getAllReimbs().isEmpty()) {
                System.out.println("In the if statement");
                for (ErsReimbursement r : reimbService.getAllReimbs()) {
                    System.out.println("in the for each loop");
                    System.out.println(r.getId());
                    System.out.println(r.getReimbursementStatusId().toString());
                    System.out.println(r.getReimbursementStatusId());
                    /**
                     * If the reimbursement is NOT pending...
                     */
                    if (r.getReimbursementStatusId() != Status.PENDING) {
                        System.out.println(r.getReimbursementStatusId().toString());
                        System.out.println("found one that's not pending!");
                        /**
                         * Use the toString for employees that starts with "Pending"
                         */
                        out.println("<p class=\"anyButPrettySmall\">" + r.toStringWithFormattingForResolved() + "</p>");
                    }
                    // call function in HTML and PASS IN dao variable.

                }
            } else {
                out.println("<p>" + "No Reimbursements found" + "</p>");
            }
            out.println("</div>");

//            /**
//             * This div prints all the reimbursements in a table with links
//             */
//            out.println("<div>");
//            out.println("<table>");
//            out.println("<tr>");
//            out.println("<td>");
//            out.println("<a href=\"/farren_springer_p1/api/viewReimbDetails\">");
//            out.println(reimbRepo.findAllReimbsByAuthorId(17));
//            out.println("</a>");
//            out.println("</td>");
//            out.println("</tr>");
//            out.println("</table>");
//            out.println("</div>");




        } else {
            out.println("<div>Can't find you</div>");
        }

        /**
         * Print the go back link
         */
        out.println("<div>\n" +
                "            <a href=\"/farren_springer_p1/api/home\">Go Back</a>\n" +
                "        </div>");

        /**
         * Don't know if I am required to use JS but here is a function that prints. Maybe adjust the message to display all reimbs by type
         */
        out.println("</body>");
        out.println("<script>\n" +
                "    function returnAMessage() {\n" +
                "    var selection = document.querySelector(\"#reimbsbytypeorstatus\").value;\n" +
                "    var message;\n" +
                "    if (selection == \"type\") {\n" +
                "        message = \"type\";\n" +
                "    } else if (selection == \"status\") {\n" +
                "        message = \"status\";\n" +
                "    }\n" +
                "\n" +
                "    document.querySelector(\"#financemanager-container\").innerHTML = message;\n" +
                "}\n" +
                "</script>");
        out.println("<link rel=\"stylesheet\" href=\"/farren_springer_p1/css/mystyles.css\">");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }
}










/**
 * Don't need the ID chose and approve/deny chose on this page. It is just to view history.
 */

// TODO enter id of employee whose past reimbursements you want to view

//            /**
//             * This div is only a demonstration of using JS. // TODO use JS to filter instead of directing to a different page.
//             */
//            out.println("<div>");
//            out.println("<h1 class=\"any\">Here are all reimbursements</h1>\n" +
//                    "\n" +
//                    "    \n" +
//                    "    <form>\n" +
//                    "    <label for=\"reimbsbytypeorstatus\">Choose a filter type (uses some JS but not relevant rn):</label>\n" +
//                    "\n" +
//                    "    <select id=\"reimbsbytypeorstatus\">\n" +
//                    "        <option value=\"type\">View by Type</option>\n" +
//                    "        <option value=\"status\">View by Status</option>\n" +
//                    "    </select>\n" +
//                    "    <br>\n" +
//                    "        <button type=\"button\" onclick=\"returnAMessage()\">Choose</button>\n" +
//                    "    </form>\n");
//            out.println("</div>");
//
//
