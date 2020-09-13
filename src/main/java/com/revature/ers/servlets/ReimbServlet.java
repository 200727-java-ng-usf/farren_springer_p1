package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.ers.dtos.ErrorResponse;
import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.exceptions.ResourceNotFoundException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Status;
import com.revature.ers.models.Type;
import com.revature.ers.services.ReimbService;
import com.revature.ers.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet("/reimbs/*")
public class ReimbServlet extends HttpServlet {

    private final ReimbService reimbService = new ReimbService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");



        System.out.println(req.getParameter("reimb_id"));
        System.out.println(req.getRequestURI());

        try {

            String idParam = req.getParameter("reimb_id");
//            String userIdParam = req.getParameter("ers_user_id");
//
//            Integer userIdParamAsInt = Integer.parseInt(userIdParam);
//            System.out.println(userIdParamAsInt);
//
//            ErsUser currentUser = userService.getUserById(userIdParamAsInt);

            if (idParam != null) {

                int id = Integer.parseInt(idParam);
                ErsReimbursement reimb = reimbService.getReimbById(id);
                String reimbJSON = mapper.writeValueAsString(reimb);
                respWriter.write(reimbJSON);

                // TODO get Reimbs by Author ID

            }
//            else if (userIdParamAsInt != null) {
//
//                System.out.println("Finding reimbs by authorId");
//
//                Set<ErsReimbursement> reimbs = reimbService.getAllByAuthorId(currentUser.getId()); // userIdParamAsInt should be the same as authorId
//
//                String reimbsJSON = mapper.writeValueAsString(reimbs);
//                respWriter.write(reimbsJSON);
//
//                resp.setStatus(200); // 200 = OK
//                System.out.println(resp.getStatus());
//                System.out.println(req.getRequestURI());
//
//            }


            else {

                System.out.println("No authorID found. Finding all reimbs");

                Set<ErsReimbursement> reimbs = reimbService.getAllReimbs();

                String reimbsJSON = mapper.writeValueAsString(reimbs);
                respWriter.write(reimbsJSON);

                resp.setStatus(200); // 200 = OK
                System.out.println(resp.getStatus());
                System.out.println(req.getRequestURI());

            }

//
//            else {
//                String JSON; // string to be written as response
//                switch (req.getRequestURI()) {
//                    case "/reimbs/pending":
//                        Set<ErsReimbursement> pendingReimbs = reimbService.getAllByStatus(Status.PENDING);
//                        JSON = mapper.writeValueAsString(pendingReimbs);
//                        break;
//                    case "/reimbs/approved":
//                        Set<ErsReimbursement> approvedReimbs = reimbService.getAllByStatus(Status.APPROVED);
//                        JSON = mapper.writeValueAsString(approvedReimbs);
//                        break;
//                    case "reimbs/denied":
//                        Set<ErsReimbursement> deniedReimbs = reimbService.getAllByStatus(Status.DENIED);
//                        JSON = mapper.writeValueAsString(deniedReimbs);
//                        break;
//                    case "reimbs/lodging":
//                        Set<ErsReimbursement> lodgingReimbs = reimbService.getAllByType(Type.LODGING);
//                        JSON = mapper.writeValueAsString(lodgingReimbs);
//                        break;
//                    case "reimbs/travel":
//                        Set<ErsReimbursement> travelReimbs = reimbService.getAllByType(Type.TRAVEL);
//                        JSON = mapper.writeValueAsString(travelReimbs);
//                        break;
//                    case "reimbs/food":
//                        Set<ErsReimbursement> foodReimbs = reimbService.getAllByType(Type.FOOD);
//                        JSON = mapper.writeValueAsString(foodReimbs);
//                        break;
//                    case "reimbs/other":
//                        Set<ErsReimbursement> otherReimbs = reimbService.getAllByType(Type.OTHER);
//                        JSON = mapper.writeValueAsString(otherReimbs);
//                        break;
//                    case "reimbs/":
//                        Set<ErsReimbursement> reimbs = reimbService.getAllReimbs();
//                        JSON = mapper.writeValueAsString(reimbs);
//                    default:
//                        throw new IllegalStateException("Unexpected value: " + req.getRequestURI());
//                }
//                respWriter.write(JSON); // response string based on switch case
//                resp.setStatus(200); // 200 = OK
//                System.out.println(resp.getStatus());
//            }


        } catch (ResourceNotFoundException rnfe) {

            resp.setStatus(404);

            ErrorResponse err = new ErrorResponse(404, rnfe.getMessage());
            respWriter.write(mapper.writeValueAsString(err));

        } catch(NumberFormatException | InvalidRequestException e) {
            resp.setStatus(400); // 400 Bad Request
            ErrorResponse err = new ErrorResponse(400, "Malformed reimb id parameter value provided.");
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500); // 500 = INTERNAL SERVER ERROR
            ErrorResponse err = new ErrorResponse(500, "It's not you, it's us. Our bad");
            respWriter.write(mapper.writeValueAsString(err));
        } // don't set message for err response unless you know EXACTLY what it says



    }

    /**
     * Used to handle incoming requests to register new reimbs for the application.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();

        try {

            ErsReimbursement newReimbursement = mapper.readValue(req.getInputStream(), ErsReimbursement.class);
            reimbService.register(newReimbursement);
            System.out.println(newReimbursement);
            String newReimbursementJSON = mapper.writeValueAsString(newReimbursement);
            respWriter.write(newReimbursementJSON);
            resp.setStatus(201); // 201 = CREATED

        } catch(MismatchedInputException mie) {

            resp.setStatus(400); // 400 = BAD REQUEST

            ErrorResponse err = new ErrorResponse(400, "Bad Request: Malformed reimb object found in request body");
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);

        }
        catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500); // 500 = INTERNAL SERVER ERROR
            ErrorResponse err = new ErrorResponse(500, "It's not you, it's us. Our bad");
            respWriter.write(mapper.writeValueAsString(err));
        }
    }
}
