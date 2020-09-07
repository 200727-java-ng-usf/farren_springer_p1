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

@WebServlet("/viewallbytype")
public class ViewAllByTypeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("in doGet of ViewAllByTypeServlet!");

//        UserRepository userRepo = new UserRepository();
//        UserService userService = new UserService(userRepo);
//        ReimbRepository reimbRepo = new ReimbRepository();
//        ReimbService reimbService = new ReimbService(reimbRepo);
//
//        // SESSION SYNTAX
//        HttpSession session = req.getSession();
////        ErsUser ersUser = (ErsUser) session.getAttribute("currentUser");
//        String username = String.valueOf(session.getAttribute("loggedUsername"));
//        ErsUser ersUser = userRepo.findUserByUsername(username)
//                .orElseThrow(AuthenticationException::new);
//
//
//        // NON SESSION SYNTAX
//        PrintWriter out = resp.getWriter();
//        out.println("<html><body>");
//
//        if(ersUser != null) {
//            out.println("This is text!");
//
//            /**
//             * Use the same format from update reimbursement by employee
//             * here. Fmanagers are doing the same thing but updating the status,
//             * resolver_id and resolved timestamp of the reimbursement instead.
//             *
//             * Receipt(?)
//             */
//
//            out.println("<h1>Name: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
//            out.println("<b>\tEmail: " + ersUser.getEmail() + "</b><br>");
//            out.println("<i>\tRole: " + ersUser.getRole() + "</i><br>");
//            /**
//             * Put a form here with an option to chose one reimbursement to approve/deny
//             */
////            out.println("<form action=\"/farren_springer_p1/html/fmanager/typeorstatus.html\">\n" +
////                    "        <input type=\"submit\" value=\"Choose a Filter\">\n" +
////                    "    </form>");
//            Integer typeChoiceNum = Integer.parseInt(String.valueOf(req.getSession().getAttribute("typeChoice")));
//
//            out.println("<b>\tAll Reimbursements by the type you chose: "
//                    + reimbService.getAllReimbsByType(typeChoiceNum) + "</b><br>");
//
//        } else {
//            out.println("Can't find you");
//        }
//
//        /**
//         * Copy-pasted from webapp/html/viewallreimbs.html
//         */
//        out.println("</body>");
//        out.println("</html>");
////        out.println("</body></html>");
//
//        resp.getWriter().write("<h1>Helper Session Servlet! doGet</h1>");

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

        if(ersUser != null) {
//            out.println("This is text!");

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



//            out.println("<form action=\"/farren_springer_p1/html/fmanager/typeorstatus.html\">\n" +
//                    "        <input type=\"submit\" value=\"Choose a Filter\">\n" +
//                    "    </form>");
            System.out.println("Still hereee");
            Integer typeChoiceNum = Integer.parseInt(String.valueOf(req.getSession().getAttribute("loggedTypeChoice")));

            System.out.println("Here");
//            out.println("<b>\tAll Reimbursements by the type you chose: "
//                    + reimbService.getAllReimbsByType(typeChoiceNum) + "</b><br>");

            /**
             * if the list of reimbursements where the type the user chose it NOT empty...
             */
            if(!reimbService.getAllReimbsByType(typeChoiceNum).isEmpty()) {
                /**
                 * Switch case to display different message based on type choice
                 */
                System.out.println(req.getSession().getAttribute("loggedTypeChoice").toString());
                switch(Integer.parseInt(req.getSession().getAttribute("loggedTypeChoice").toString())) {
                    case 1:
                        out.println("<p> All Lodging Reimbursements </p>");
                        break;
                    case 2:
                        out.println("<p> All Travel Reimbursements </p>");
                        break;
                    case 3:
                        out.println("<p> All Food Reimbursements </p>");
                        break;
                    case 4:
                        out.println("<p> All \"Other\" Reimbursements </p>");
                        break;
                    default:
                        out.println("<p> Something went wrong... </p>");
                }
//                out.println("<p>\t All " + req.getSession().getAttribute("loggedTypeChoice") + " Reimbursements:</p>");
                /**
                 * For each reimbursement in all the reimbursements with that type...
                 */
                for (ErsReimbursement r : reimbService.getAllReimbsByType(typeChoiceNum)) {
                    /**
                     * Use the toString
                     */
                    out.println("<p class=\"anyButPrettySmall\">" + r.toString() + "</p>");
                }

            } else {
                out.println("<p>" + "No Reimbursements found" + "</p>");
            }

            System.out.println("Hmm");

        } else {
            out.println("Can't find you");
        }

        /**
         * Print the go back link
         */
        out.println("<div>\n" +
                "            <a href=\"/farren_springer_p1/api/home\">Go Back</a>\n" +
                "        </div>");

        /**
         * Copy-pasted from webapp/html/viewallreimbs.html
         */
        out.println("</body>");
        out.println("<link rel=\"stylesheet\" href=\"/farren_springer_p1/css/mystyles.css\">");
        out.println("</html>");
//        out.println("</body></html>");




    }
}
