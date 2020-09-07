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

@WebServlet("/viewPendingReimbs")
public class ViewAllPendingReimbsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("In doGet of ViewAllPendingReimbs Servlet!");

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

        /**
         * Sign out option at the top of every page
         */
        out.println("<header>\n" +
                "            <div>\n" +
                "                <a href=\"/farren_springer_p1/html/login.html\">Sign Out</a>\n" +
                "            </div>\n" +
                "        </header>");

        /**
         * If the user in the session is not null...
         */
        if(ersUser != null) {
//            out.println("This is text!");
            /**
             * This <div> just displays the user-in-session's information
             */
            out.println("<div>");
            out.println("<h1>\tName: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
            out.println("<b>\tEmail: " + ersUser.getEmail() + "</b><br>");
            out.println("<i>\tRole: " + ersUser.getRole().toString() + "</i><br>");
//            out.println("<form action=\"/farren_springer_p1/html/fmanager/typeorstatus.html\">\n" +
//                    "        <input type=\"submit\" value=\"Choose a Filter\">\n" +
//                    "    </form>");
            out.println("</div>");

            /**
             * This <div> only contains a form that will let the finance manager approve or deny a reimbursement.
             */
            out.println("<div>");
            out.println("<form method=\"post\" action=\"/farren_springer_p1/api/approveOrDeny\">\n" +
                    "            <p style=\"FONT-SIZE: 20PX;\">Enter the ID number of the reimbursement you would like to approve or deny</p>\n" +
                    "            <input placeholder=\"enter the ID of the reimbursement to approve/deny\" name=\"reimbIdChosenByFManager\"/><br>\n" +
                    "            <p style=\"FONT-SIZE: 20PX;\">Enter either approve or deny to edit the reimbursement</p>\n" +
                    "            <input placeholder=\"Enter text\" name=\"approveOrDenyChoice\"/><br>\n" +
                    "            <input type=\"submit\" value=\"Choose\"/><br>\n" +
                    "        </form>");
            out.println("</div>");

            /**
             * This <div> lets the finance manager chose a filter
             */
            out.println("<div>");
            out.println("<form action=\"/farren_springer_p1/html/fmanager/typeorstatus.html\">\n" +
                    "        <input type=\"submit\" value=\"Choose a Filter\">\n" +
                    "    </form>");
            out.println("</div>");



//            out.println("<b>\tAll Reimbursements: " + reimbService.getAllReimbs() + "</b><br>");

            out.println("<div>");

            out.println("<h1 style=\"FONT-SIZE: 20PX;\">Pending Reimbursements: </h1>\n");
            if(!reimbService.getAllReimbs().isEmpty()) {
                for (ErsReimbursement r : reimbService.getAllReimbs()) {
                    /**
                     * If the reimbursement is pending...
                     */
                    if (r.getReimbursementStatusId() == Status.PENDING) {
                        /**
                         * Use the toString for employees that starts with "Pending"
                         */
                        out.println("<p>" + r.toStringOnlyUseThisForPending() + "</p>");
                    }

                }
            } else {
                out.println("<p>" + "No Reimbursements found" + "</p>");
            }
            out.println("</div>");

        } else {
            out.println("Can't find you");
        }

        /**
         * Print the go back link
         */
        out.println("<div>\n" +
                "            <a href=\"/farren_springer_p1/api/home\">Go Back</a>\n" +
                "        </div>");

        out.println("<div>\n" +
                "\n" +
                "    \n" +
                "    <form>\n" +
                "    <label style=\"FONT-SIZE: 20PX;\" for=\"reimbsbytypeorstatus\">Choose a filter type (uses some JS but not relevant rn):</label>\n" +
                "\n" +
                "    <select id=\"reimbsbytypeorstatus\">\n" +
                "        <option value=\"type\">View by Type</option>\n" +
                "        <option value=\"status\">View by Status</option>\n" +
                "    </select>\n" +
                "    <br>\n" +
                "        <button type=\"button\" onclick=\"returnAMessage()\">Choose</button>\n" +
                "    </form>\n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"financemanager-container\" class=\"financemanager-container\"></div>");

        /**
         * Copy-pasted from webapp/html/viewallreimbs.html
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
//        out.println("</body></html>");

//        resp.getWriter().write("<h1>Helper Session Servlet! doGet</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }
}
