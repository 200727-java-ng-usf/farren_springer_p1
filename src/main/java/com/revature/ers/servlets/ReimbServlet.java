package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.ers.dtos.Credentials;
import com.revature.ers.dtos.ErrorResponse;
import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.exceptions.ResourceNotFoundException;
import com.revature.ers.models.*;
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
import java.util.Set;

@WebServlet("/reimbs/*")
public class ReimbServlet extends HttpServlet {

    private final ReimbService reimbService = new ReimbService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("in ReimbServlet doGet");

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");


        System.out.println("below should be the reimb_id (may be null)");
        System.out.println(req.getParameter("reimb_id"));

        System.out.println("below is the requestURI");
        System.out.println(req.getRequestURI());

        System.out.println("below should be the authUser ID");
        System.out.println(req.getSession().getAttribute("authorIdToFindReimbs"));

        System.out.println("below should be the reimbursement if one was assigned");
        System.out.println(req.getSession().getAttribute("reimbursement"));

        try {

            String idParam = req.getParameter("reimb_id");

            /**
             * Find the ID to see what role the user is
             */
            Object authorIdParam = req.getSession().getAttribute("authorIdToFindReimbs");
            System.out.println(authorIdParam.toString());

            Object reimbursement = req.getSession().getAttribute("reimbursement");


            if (idParam != null) {

                int id = Integer.parseInt(idParam);
                ErsReimbursement reimb = reimbService.getReimbById(id);
                String reimbJSON = mapper.writeValueAsString(reimb);
                respWriter.write(reimbJSON);

                // TODO get Reimbs by Author ID

            }
            else if (reimbursement != null) {

                ErsReimbursement ersReimbursement = (ErsReimbursement) reimbursement;

                String ersReimbursementJSON = mapper.writeValueAsString(ersReimbursement);

                respWriter.write(ersReimbursementJSON);

                resp.setStatus(200); // 200 OK
            }

            else if (authorIdParam != null) { // if the authorIdParam is not null...

                int authorId = Integer.parseInt(String.valueOf(authorIdParam)); // turn the parameter to an int
                Role role = userService.getUserById(authorId).getRole(); // find the role from the ID
                System.out.println(role);

                if (role == Role.EMPLOYEE) { // if the user is an employee...

                    // only get the reimbs by the author ID
                    Set<ErsReimbursement> reimbsByAuthor = reimbService.getAllByAuthorId(authorId);

                    String principalJSON = mapper.writeValueAsString(reimbsByAuthor);
                    respWriter.write(principalJSON);

                    resp.setStatus(200); // 200 OK
                }

                else {
                    System.out.println("No authorID found. Finding all reimbs");

                    Set<ErsReimbursement> reimbs = reimbService.getAllReimbs();

                    String reimbsJSON = mapper.writeValueAsString(reimbs);
                    respWriter.write(reimbsJSON);

                    resp.setStatus(200); // 200 = OK
                    System.out.println(resp.getStatus());
                    System.out.println(req.getRequestURI());
                }


            }

//            else { // should get here if the
//
//                System.out.println("No authorID found. Finding all reimbs");
//
//                Set<ErsReimbursement> reimbs = reimbService.getAllReimbs();
//
//                String reimbsJSON = mapper.writeValueAsString(reimbs);
//                respWriter.write(reimbsJSON);
//
//                resp.setStatus(200); // 200 = OK
//                System.out.println(resp.getStatus());
//                System.out.println(req.getRequestURI());
//
//            }

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

        System.out.println("in ReimbServlet doPost");

        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();

        try {

            // if the attribute that the Manager chose for a reimbursement is not already present...
            if (req.getSession().getAttribute("reimbursement") == null) {
                //then register!
                System.out.println("reimbursement attribute not present. Starting submit!");
                ErsReimbursement newReimbursement = mapper.readValue(req.getInputStream(), ErsReimbursement.class);
                reimbService.register(newReimbursement);
                System.out.println(newReimbursement);
                String newReimbursementJSON = mapper.writeValueAsString(newReimbursement);
                respWriter.write(newReimbursementJSON);
                resp.setStatus(201); // 201 = CREATED
            }
            // otherwise, update.
            else { // if this block is executed, it means a manager has chosen a reimbursement to resolve and it needs to be updated in the DB

                System.out.println("reimbursement to update exists!");

                // find the original reimbursement ID
                Object reimbId = req.getSession().getAttribute("reimbIdToUpdate");
                System.out.println("This is the reimb ID: " + reimbId);

                String string = String.valueOf(reimbId);
                System.out.println(string);

                String cleanString = string.replaceAll("\\D+", "");
                System.out.println(cleanString);

                Integer integer = Integer.parseInt(cleanString);
                System.out.println(integer);

                // find the reimbursement with that ID
                ErsReimbursement reimbToUpdate = reimbService.getReimbById(integer);
                System.out.println("This is the reimbursement to update: " + reimbToUpdate);

                // all fields must be filled out for it to be resolved
                // this might be where you put another if statement if you
                // add feature where employees can update their reimbursement

                // find the updated information to set the original reimb to
                ErsReimbursement reimbursementWithResolvedInfo = mapper.readValue(req.getInputStream(), ErsReimbursement.class);

                System.out.println("This contains the updated information: " + reimbursementWithResolvedInfo);
                System.out.println("Note that ID may be null");

                // assign fields to service layer object
                reimbToUpdate.setResolverId(reimbursementWithResolvedInfo.getResolverId());
                reimbToUpdate.setResolved(reimbursementWithResolvedInfo.getResolved());
                reimbToUpdate.setReimbursementStatus(reimbursementWithResolvedInfo.getReimbursementStatus());

                // update the DB
                reimbService.resolve(reimbToUpdate);

                HttpSession session = req.getSession();
                session.setAttribute("reimbUpdated", reimbToUpdate); // assign that user to the session. TODO unset this attribute once they are updated?

                String reimbUpdatedJSON = mapper.writeValueAsString(reimbToUpdate);
                respWriter.write(reimbUpdatedJSON); // return the user (if found) to the response

                resp.setStatus(201); // 201 = CREATED because new information?
            }



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
