package com.revature.ers.servlets;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
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

@WebServlet("/viewallbystatus")
public class ViewAllByStatusServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("In view all by status servlet!");

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

            out.println("<h1>Name: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
            out.println("<b>\tEmail: " + ersUser.getEmail() + "</b><br>");
            out.println("<i>\tRole: " + ersUser.getRole() + "</i><br>");
            /**
             * Put a form here with an option to chose one reimbursement to approve/deny
             */
//            out.println("<form action=\"/farren_springer_p1/html/fmanager/typeorstatus.html\">\n" +
//                    "        <input type=\"submit\" value=\"Choose a Filter\">\n" +
//                    "    </form>");
            Integer statusChoiceNum = Integer.parseInt(String.valueOf(req.getSession().getAttribute("statusChoice")));

            out.println("<b>\tAll Reimbursements by the status you chose: "
                    + reimbService.getAllReimbsByStatus(statusChoiceNum));

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
        out.println("</html>");
//        out.println("</body></html>");

        resp.getWriter().write("<h1>Helper Session Servlet! doGet</h1>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }

}