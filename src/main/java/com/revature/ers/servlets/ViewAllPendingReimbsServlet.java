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

        if(ersUser != null) {
            out.println("This is text!");

            /**
             * Use the same format from update reimbursement by employee
             * here. Fmanagers are doing the same thing but updating the status,
             * resolver_id and resolved timestamp of the reimbursement instead.
             *
             * Receipt(?)
             */

            out.println("<form method=\"post\" action=\"/farren_springer_p1/api/approveOrDeny\">\n" +
                    "            <p>Enter the ID number of the reimbursement you would like to approve or deny</p>\n" +
                    "            <input placeholder=\"enter the ID of the reimbursement to approve/deny\" name=\"reimbIdChosenByFManager\"/><br>\n" +
                    "            <p>Enter either approve or deny to edit the reimbursement</p>\n" +
                    "            <p>(If you wish to go back to your dashboard, type back)</p>\n" +
                    "            <input placeholder=\"Enter text\" name=\"approveOrDenyChoice\"/><br>\n" +
                    "            <input type=\"submit\" value=\"Choose\"/><br>\n" +
                    "        </form>");

            out.println("<div class =\"any\">\n" +
                    "    <h1 class=\"any\">Here are all reimbursements</h1>\n" +
                    "\n" +
                    "    \n" +
                    "    <form>\n" +
                    "    <label for=\"reimbsbytypeorstatus\">Choose a filter type (uses some JS but not relevant rn):</label>\n" +
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

            out.println("<h1 class=\"any\">Name: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
            out.println("<b>\tEmail: " + ersUser.getEmail() + "</b><br>");
            out.println("<i>\tRole: " + ersUser.getRole() + "</i><br>");
            out.println("<form action=\"/farren_springer_p1/html/fmanager/typeorstatus.html\">\n" +
                    "        <input type=\"submit\" value=\"Choose a Filter\">\n" +
                    "    </form>");
            out.println("<form method=\"post\" action=\"/farren_springer_p1/api/home\" class=\"any\">\n" +
                    "        <input type = \"submit\" value=\"Go Back\" class=\"any\"><br>\n" +
                    "    </form>");
            out.println("<b>\tAll Reimbursements: " + reimbService.getAllReimbsByStatus(1) + "</b><br>");

        } else {
            out.println("Can't find you");
        }

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
        out.println("<link rel=\"stylesheet\" href=\"../css/mystyles.css\">");
        out.println("</html>");
//        out.println("</body></html>");

        resp.getWriter().write("<h1>Helper Session Servlet! doGet</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }
}
