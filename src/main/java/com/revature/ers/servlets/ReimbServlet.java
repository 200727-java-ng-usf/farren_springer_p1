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

    /**
     * READ operation
     * If this method is called, the user is either a Finance Manager or an Employee.
     * If they are a Finance Manager, they are either getting all reimbs or the details for one.
     * If they are an employee, they are getting all of their reimbs or the details for one.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("in ReimbServlet doGet");

        /**
         * Set up objects to write back to the browser
         */
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        /**
         * First, get the principle to identify the user as a Finance Manager or an Employee.
         * Also, get the reimbursement if one was assigned. If this is present, return only
         * one reimbursement.
         */

        System.out.println("below should be the reimb_id (may be null)");
        System.out.println(req.getParameter("reimb_id"));

        System.out.println("below should be the authUser ID");
        System.out.println(req.getSession().getAttribute("authorIdToFindReimbs"));

        System.out.println("below should be the user ID if they are a finance manager");
        System.out.println(req.getSession().getAttribute("userWhoIsDefinitelyAFinanceManager"));

        System.out.println("below should be the reimbursement if one was assigned");
        System.out.println(req.getSession().getAttribute("reimbursement"));

        try {

            /**
             * See which ID is present (one for employee or one for finance manager)
             */
            if (req.getSession().getAttribute("authorIdToFindReimbs") != null) { // if the user is an employee...

                Object authorIdParam = req.getSession().getAttribute("authorIdToFindReimbs"); // assign this attribute to an object in the service layer
                System.out.println("User is an employee!" + authorIdParam.toString());

                /**
                 * Now, we need to see if one reimbursement has been selected.
                 */
                if (req.getSession().getAttribute("reimbursement") != null) { // if a reimbursement has been selected...

                    Object reimbursement = req.getSession().getAttribute("reimbursement");
                    System.out.println("A specific reimbursement has been selected!");

                    ErsReimbursement ersReimbursement = (ErsReimbursement) reimbursement;

                    String ersReimbursementJSON = mapper.writeValueAsString(ersReimbursement);

                    respWriter.write(ersReimbursementJSON);


                    resp.setStatus(200);

                } else { // else, return the reimbursements by the employee

                    String authorIdParamString = String.valueOf(authorIdParam);
                    Integer authorIdParamInteger = Integer.parseInt(authorIdParamString);

                    Set<ErsReimbursement> reimbsByAuthor = reimbService.getAllByAuthorId(authorIdParamInteger);

                    String principalJSON = mapper.writeValueAsString(reimbsByAuthor);
                    respWriter.write(principalJSON);

                    resp.setStatus(200); // 200 OK

                }

            } else {

                Object userWhoIsDefinitelyAFinanceManager = req.getSession().getAttribute("userWhoIsDefinitelyAFinanceManager");
                System.out.println("User is a finance manager!" + userWhoIsDefinitelyAFinanceManager.toString());

                /**
                 * Now, we need to see if one reimbursement has been selected
                 */
                if (req.getSession().getAttribute("reimbursement") != null) { // if a reimbursement has been selected...

                    Object reimbursement = req.getSession().getAttribute("reimbursement");
                    System.out.println("A specific reimbursement has been selected!");

                    ErsReimbursement ersReimbursement = (ErsReimbursement) reimbursement;

                    String ersReimbursementJSON = mapper.writeValueAsString(ersReimbursement);

                    respWriter.write(ersReimbursementJSON);

                    reimbursement = null; // assign the reimbursement to null so that all will show the next time a user calls the doGet

                    resp.setStatus(200);

                } else { // else, return all reimbursements for the finance manager

                    Set<ErsReimbursement> reimbs = reimbService.getAllReimbs();

                    String reimbsJSON = mapper.writeValueAsString(reimbs);
                    respWriter.write(reimbsJSON);

                    resp.setStatus(200); // 200 = OK
                    System.out.println(resp.getStatus());
                    System.out.println(req.getRequestURI());

                }

            }


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
     * This method will be called to submit a reimbursement

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

            System.out.println("Starting submit!");
            ErsReimbursement newReimbursement = mapper.readValue(req.getInputStream(), ErsReimbursement.class);
            System.out.println("read the input stream: " + newReimbursement);
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

    /**
     * If this method is called, a user is either:
     * A finance manager resolving a reimbursement, or
     * an employee updating their PENDING reimbursement TODO check to make sure reimb is PENDING in service layer?
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("In doPut of ReimbServlet!");

        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();

        try {
            if (req.getSession().getAttribute("userWhoIsDefinitelyAFinanceManager") != null) { // user is a finance manager

                System.out.println("User is a finance manager! Resolving the reimb...");

                /**
                 * Find the reimbursement from the session
                  */
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

                /**
                 * Map the input stream from the XMLHttpRequest to a reimbursement object that will use the constructor that has resolve values
                 */
                // find the updated information to set the original reimb to
                System.out.println("about to read the information from the request...");
                ErsReimbursement reimbursementWithResolvedInfo = mapper.readValue(req.getInputStream(), ErsReimbursement.class);

                System.out.println("This contains the updated information: " + reimbursementWithResolvedInfo);
                System.out.println("Note that ID may be null");

                /**
                 * set the fields of the original reimbursement to the fields of the resolve reimbursement object (which will have no ID bc it is temporary)
                 */
                // assign fields to service layer object
                reimbToUpdate.setResolverId(reimbursementWithResolvedInfo.getResolverId());
                reimbToUpdate.setResolved(reimbursementWithResolvedInfo.getResolved());
                reimbToUpdate.setReimbursementStatus(reimbursementWithResolvedInfo.getReimbursementStatus());

                // update the DB
                reimbService.resolve(reimbToUpdate);

                HttpSession session = req.getSession();
                session.setAttribute("reimbUpdated", reimbToUpdate); // assign that user to the session. TODO unset this attribute once they are updated?

                req.getSession().removeAttribute("reimbursement"); // resets so that managers can see all users again when this method is requested

                String reimbUpdatedJSON = mapper.writeValueAsString(reimbToUpdate);
                respWriter.write(reimbUpdatedJSON); // return the user (if found) to the response

                resp.setStatus(201); // 201 = CREATED because new information?


            } else if (req.getSession().getAttribute("authorIdToFindReimbs") != null) { // user is an employee

                System.out.println("User is an employee! Updating their reimb...");

                /**
                 * find the reimbursement from the session
                 */
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

                /**
                 * Map the input stream from the XMLHttpRequest to a reimbursement object with the new values
                 */
                // get the information from the browser
                ErsReimbursement ersReimbursement = mapper.readValue(req.getInputStream(), ErsReimbursement.class);
                System.out.println("ID: of this should be null: " + ersReimbursement);

                /**
                 * Set the fields of the og reimbursement to the fields of the reimb with updated values (which will have no ID bc it is temporary)
                 */
                // assign amount, type, and description to the original reimb (even if they left some null or empty,
                // the values from the browser should have been assigned the og values if that's the case, so still
                // reassign all).
                reimbToUpdate.setAmount(ersReimbursement.getAmount());
                reimbToUpdate.setReimbursementType(ersReimbursement.getReimbursementType());
                reimbToUpdate.setDescription(ersReimbursement.getDescription());

                // update the reimbursement
                reimbService.update(reimbToUpdate);

                // return things.
                HttpSession session = req.getSession();
                session.setAttribute("reimbUpdatedByEmployee", reimbToUpdate); // assign that reimb to the session. TODO unset this attribute once they are updated?

                req.getSession().removeAttribute("reimbursement"); // resets so that employees can see all of their reimbs again when this method is requested

                String reimbUpdatedJSON = mapper.writeValueAsString(reimbToUpdate);
                respWriter.write(reimbUpdatedJSON); // return the user (if found) to the response

                resp.setStatus(201); // 201 = CREATED because new information?

            }
        } catch (Exception e) {
            e.printStackTrace(); // TODO custom exceptions
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("In doDelete of ReimbServlet!");

        resp.setContentType("application/json");

        try {

            if (req.getSession().getAttribute("reimbIdToUpdate") != null) { // if the reimbursement has been set in the session...

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
                ErsReimbursement reimbToDelete= reimbService.getReimbById(integer);
                System.out.println("This is the reimbursement to delete: " + reimbToDelete);

                reimbService.delete(reimbToDelete);

                req.getSession().removeAttribute("reimbursement"); // clear the session data for this

                resp.setStatus(200); // 200 = OK

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

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
