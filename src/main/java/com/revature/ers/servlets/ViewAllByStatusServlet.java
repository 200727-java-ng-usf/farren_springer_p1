package com.revature.ers.servlets;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.ErsUser;
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

@WebServlet("/viewallbystatus")
public class ViewAllByStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("in doGet of ViewAllByStatysServlet!");


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("Whoops! In doPost");
//        req.getSession().invalidate();


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
         * In the body, print the sign out option
         */
        out.println("<header>\n" +
                "            <div>\n" +
                "                <a href=\"/farren_springer_p1/html/login.html\">Sign Out</a>\n" +
                "            </div>\n" +
                "        </header>");

        if(ersUser != null) {

            /**
             * Use the same format from update reimbursement by employee
             * here. Fmanagers are doing the same thing but updating the status,
             * resolver_id and resolved timestamp of the reimbursement instead.
             *
             * Receipt(?)
             */

            out.println("<div>");
            out.println("<h1 class=\"anyButEvenSmaller\">Name: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
            out.println("<b class=\"anyButPrettySmall\">\tEmail: " + ersUser.getEmail() + "</b><br>");
            out.println("<i class=\"anyButPrettySmall\">\tRole: " + ersUser.getRole().toString() + "</i><br>");
            out.println("</div>");
            /**
             * Put a form here with an option to chose one reimbursement to approve/deny
             */
//            out.println("<form action=\"/farren_springer_p1/html/fmanager/typeorstatus.html\">\n" +
//                    "        <input type=\"submit\" value=\"Choose a Filter\">\n" +
//                    "    </form>");
            System.out.println("Still hereee");
            Integer statusChoiceNum = Integer.parseInt(String.valueOf(req.getSession().getAttribute("loggedStatusChoice")));

            System.out.println("Here");
//            out.println("<b>\tAll Reimbursements by the status you chose: "
//                    + reimbService.getAllReimbsByStatus(statusChoiceNum) + "</b><br>");

            /**
             * if the list of reimbursements where the status the user chose it NOT empty...
             */
            if(!reimbService.getAllReimbsByStatus(statusChoiceNum).isEmpty()) {
                /**
                 * Switch case to display different message based on status choice
                 */
                System.out.println(req.getSession().getAttribute("loggedStatusChoice").toString());
                switch(Integer.parseInt(req.getSession().getAttribute("loggedStatusChoice").toString())) {
                    case 1:
                        out.println("<p> All Pending Reimbursements </p>");
                        break;
                    case 2:
                        out.println("<p> All Approved Reimbursements </p>");
                        break;
                    case 3:
                        out.println("<p> All Denied Reimbursements </p>");
                        break;
                    default:
                        out.println("<p> Something went wrong... </p>");
                }
//                out.println("<p>\t All " + req.getSession().getAttribute("loggedStatusChoice") + " Reimbursements:</p>");
                /**
                 * For each reimbursement in all the reimbursements with that status...
                 */
                for (ErsReimbursement r : reimbService.getAllReimbsByStatus(statusChoiceNum)) {
                    /**
                     * Use the toString
                     */
                    out.println("<p class=\"anyButPrettySmall\">" + r.toString() + "</p>");

                }
            } else {
                out.println("<p>" + "No Reimbursements found" + "</p>");
            }

            System.out.println("Hmm");

            /**
             * Print the go back option
             */
            out.println("<div>\n" +
                    "            <a href=\"/farren_springer_p1/api/home\">Go Back</a>\n" +
                    "        </div>");

        } else {
            out.println("Can't find you");
        }

        /**
         * End the body and html tag and include a link to the style sheet
         */
        out.println("</body>");
        out.println("<link rel=\"stylesheet\" href=\"/farren_springer_p1/css/mystyles.css\">");
        out.println("</html>");
//        out.println("</body></html>");




    }
}
