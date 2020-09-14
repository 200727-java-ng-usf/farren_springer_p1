package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.ers.dtos.Credentials;
import com.revature.ers.dtos.ErrorResponse;
import com.revature.ers.dtos.Principal;
import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;
import com.revature.ers.services.ReimbService;
import com.revature.ers.services.UserService;

import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final ReimbService reimbService = new ReimbService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("in AuthServlet doGet");
        req.getSession().invalidate();
        resp.setStatus(204);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("in AuthServlet doPost");

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");
        System.out.println(req.getInputStream());

        try {

            // if a user in the session already exists
            if (req.getSession().getAttribute("authorIdToFindReimbs") != null) { // if this parameter has already been created... (will be if the user has logged in)

                System.out.println("in first if of AuthServlet doPost");
                System.out.println("Current user's ID: " + req.getSession().getAttribute("authorIdToFindReimbs"));
                /**
                 * If the user is an admin, take the id and assume it is a USER
                 * If the user is not an admin, take the id and assume it is a REIMBURSEMENT
                 * TODO Could add the case that the user is trying to update their own information...
                 */

                //find the user to find their role
                String astring = String.valueOf(req.getSession().getAttribute("authorIdToFindReimbs"));
                System.out.println("This is the current user's ID as a string: " + astring);
                Integer aninteger = Integer.parseInt(astring);
                System.out.println("This is the current user's ID as an integer: " + aninteger);
                ErsUser ersUser = userService.getUserById(aninteger);
                // even though above says "authorIdToFindReimbs...this is just the id of the user logged in. TODO change name of attribute
                System.out.println(ersUser.toString());

                Role role = ersUser.getRole(); // the role of the user logged in
                System.out.println(role); // print the role for logging...

                if (role == Role.ADMIN) { // if the user is an admin, we are authorizing that the user id they chose exists in the DB

                    System.out.println("in the case where the user is an admin and is in doPost of auth");

                    Object userId = mapper.readValue(req.getInputStream(), Object.class);
                    System.out.println("This is the user ID: " + userId);

                    String string = String.valueOf(userId); // turn the object to a string first
                    System.out.println("This is the string version of the request value: " + string);

                    String cleanString = string.replaceAll("\\D+", ""); // take the characters out of the string to leave just numbers
                    System.out.println("This is the string with only numbers: " + cleanString);

                    Integer integer = Integer.parseInt(cleanString); // parse the string for int
                    System.out.println("This is that same string as an integer: " + integer);

                    ErsUser userToUpdate = userService.getUserById(integer); // find the user
                    System.out.println("This is the user that was found based on the request: " + userToUpdate);

                    HttpSession session = req.getSession();
                    Integer userIdToUpdate = userToUpdate.getId();
                    session.setAttribute("userIdToUpdate", userIdToUpdate); // assign that user to the session. TODO unset this attribute once they are updated?

                    String userToUpdateJSON = mapper.writeValueAsString(userToUpdate);
                    respWriter.write(userToUpdateJSON); // return the user (if found) to the response

                    resp.setStatus(200); // 200 OK

                } else { // if this block is executed, the user is not an admin, and is therefore authorizing that a reimbursement exists

                    System.out.println("in case where user is not an Admin of doPost of AuthServlet");

                    Object reimbId = mapper.readValue(req.getInputStream(), Object.class);
                    System.out.println(reimbId);

                    String string = String.valueOf(reimbId);
                    System.out.println(string);
                    String cleanString = string.replaceAll("\\D+","");
                    System.out.println(cleanString);
                    Integer integer = Integer.parseInt(cleanString);
                    System.out.println(integer);

                    req.getSession().setAttribute("reimbIdToUpdate", integer);

                    ErsReimbursement ersReimbursement = reimbService.getReimbById(integer);
                    System.out.println(ersReimbursement);

                    HttpSession session = req.getSession();
                    session.setAttribute("reimbursement", ersReimbursement);

                    String reimbursementJSON = mapper.writeValueAsString(ersReimbursement);
                    respWriter.write(reimbursementJSON);

                    resp.setStatus(200); // 200 OK
                }

            }
            else { // else the user is not logged in. Log them in.

                System.out.println("user is not logged in doPost of AuthServlet. About to authenticate...");

                // User Jackson to read the request body and map the provided JSON to a Java POJO
                Credentials creds = mapper.readValue(req.getInputStream(), Credentials.class);

                ErsUser authUser = userService.authenticate(creds.getUsername(), creds.getPassword());
                System.out.println("Authentication done! User is: " + authUser.toString());
                Principal principal = new Principal(authUser);

                HttpSession session = req.getSession();
                session.setAttribute("principal", principal);
                session.setAttribute("authorIdToFindReimbs", principal.getId());

                String principalJSON = mapper.writeValueAsString(principal);
                respWriter.write(principalJSON);

                resp.setStatus(200); // 200 OK

            }


        } catch(MismatchedInputException | InvalidRequestException e) {

            resp.setStatus(400); // 400 = BAD REQUEST

            ErrorResponse err = new ErrorResponse(400, "Bad Request: Malformed credentials object found in request body");
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);

        } catch (AuthenticationException ae) {

            resp.setStatus(401);
            ErrorResponse err = new ErrorResponse(401, ae.getMessage());
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500); // 500 = INTERNAL SERVER ERROR
            ErrorResponse err = new ErrorResponse(500, "It's not you, it's us. Our bad");
            respWriter.write(mapper.writeValueAsString(err));
        }

    }
}
