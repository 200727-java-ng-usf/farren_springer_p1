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

@WebServlet("/viewAllUsers")
public class ViewAllUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("In doGet of ViewAllUsers Servlet!");

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
        /**
         * Start the HTML page and print the current user's information
         */
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        if(ersUser != null) {
//            out.println("This is text!");

            /**
             * This <div> just displays the user-in-session's information.
             */
            out.println("<div>");
            out.println("<h1 class=\"anyButEvenSmaller\">Name: " + ersUser.getFirstName() + " " + ersUser.getLastName() + "</h1><br>");
            out.println("<b class=\"anyButPrettySmall\">\tEmail: " + ersUser.getEmail() + "</b><br>");
            out.println("<i class=\"anyButPrettySmall\">\tRole: " + ersUser.getRole().toString() + "</i><br>");
            out.println("</div>");

        /**
         * Form to choose user by ID number
         */
            out.println("<div>");
            out.println("<form method=\"post\" action=\"/farren_springer_p1/api/findUser\" >\n" +
                "                <!-- <p class=\"findUser\">Please Enter User Information</p> -->\n" +
                "                <input placeholder=\"Enter ID of User\" name=\"userIdToEdit\" /><br>\n" +
                "                <input type=\"submit\" value=\"Find\" /><br>\n" +
                "            </form>");
            out.println("</div>");


        /**
         * TODO filter for role?
         */
//            out.println("<div>");
//            out.println("<form action=\"/farren_springer_p1/html/fmanager/employeeorfmanager.html\">\n" +
//                    "        <input type=\"submit\" value=\"Choose a Filter\">\n" +
//                    "    </form>");
//            out.println("</div>");


        /**
         * Print all Users to the page
         */
            /**
             * This div displays all of the reimbursements that have already been approved or denied.
             */
            out.println("<div>");
            out.println("<p> All Users: </p>");
            if(!reimbService.getAllReimbs().isEmpty()) {
                System.out.println("In the if statement");
                for (ErsUser u : userRepo.findAll()) {
                    System.out.println("in the for each loop");
                    System.out.println(u.getId());
                    out.println("<p class=\"anyButPrettySmall\">" + u.toString() + "</p>");
                }
            } else {
                out.println("<p>" + "No Users found" + "</p>");
            }
            out.println("</div>"); // end div that displays all users

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
         * End the body and the document. Link the style sheet.
         */
        out.println("</body>");
        out.println("<link rel=\"stylesheet\" href=\"/farren_springer_p1/css/mystyles.css\">");
        out.println("</html>");

        /**
         * Print the go back link
         */


    }

    /**
     * Invalidate the session if it is a post request
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }
}
