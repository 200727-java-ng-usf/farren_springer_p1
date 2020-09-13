package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.ers.dtos.Credentials;
import com.revature.ers.dtos.ErrorResponse;
import com.revature.ers.dtos.Principal;
import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.ErsUser;
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

        req.getSession().invalidate();
        resp.setStatus(204);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");
        System.out.println(req.getInputStream());

        try {

//            Object object = mapper.readValue(req.getInputStream(), Object.class); // object will either be credentialsJSON or reimb object with id

            // if a user in the session already exists
            if (req.getSession().getAttribute("authorIdToFindReimbs") != null) { // if this parameter has already been created...

                System.out.println("in if of doPost of AuthServlet");
//                String testString = "{id=30}";
//                System.out.println(testString);
//                String clean = testString.replaceAll("\\D+",""); //remove non-digits
//                System.out.println(clean);
//                Integer testInteger = Integer.parseInt(clean);
//                System.out.println(testInteger);

                Object reimbId = mapper.readValue(req.getInputStream(), Object.class);
                System.out.println(reimbId);

                String string = String.valueOf(reimbId);
                System.out.println(string);
                String cleanString = string.replaceAll("\\D+","");
                System.out.println(cleanString);
                Integer integer = Integer.parseInt(cleanString);
                System.out.println(integer);

                ErsReimbursement ersReimbursement = reimbService.getReimbById(integer);
                System.out.println(ersReimbursement);

                HttpSession session = req.getSession();
                session.setAttribute("reimbursement", ersReimbursement);

                String reimbursementJSON = mapper.writeValueAsString(ersReimbursement);
                respWriter.write(reimbursementJSON);

                resp.setStatus(200); // 200 OK
            }
            else { // else the user is not logged in. Log them in.

                System.out.println("in else of doPost of AuthServlet");

                // User Jackson to read the request body and map the provided JSON to a Java POJO
                Credentials creds = mapper.readValue(req.getInputStream(), Credentials.class);

                ErsUser authUser = userService.authenticate(creds.getUsername(), creds.getPassword());
                Principal principal = new Principal(authUser);

                HttpSession session = req.getSession();
                session.setAttribute("principal", principal);
                session.setAttribute("authorIdToFindReimbs", principal.getId());

                String principalJSON = mapper.writeValueAsString(principal);
                respWriter.write(principalJSON);

                resp.setStatus(200); // 200 OK

            }

//            // User Jackson to read the request body and map the provided JSON to a Java POJO
//            Credentials creds = mapper.readValue(req.getInputStream(), Credentials.class);
//
//            ErsUser authUser = userService.authenticate(creds.getUsername(), creds.getPassword());
//            Principal principal = new Principal(authUser);
//
//            HttpSession session = req.getSession();
//            session.setAttribute("principal", principal);
//            session.setAttribute("authorIdToFindReimbs", principal.getId());
//
//            String principalJSON = mapper.writeValueAsString(principal);
//            respWriter.write(principalJSON);
//
//            resp.setStatus(200); // 200 OK

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
