package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.ers.dtos.ErrorResponse;
import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.exceptions.ResourceNotFoundException;
import com.revature.ers.models.ErsUser;
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

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

    // Use the userService for all CRUD operations in the servlet
    private final UserService userService = new UserService();

    /**
     * CREATE operation
     * Used to handle incoming requests to register new users for the application.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("in UserServlet doPost");

        resp.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();

        try {

                // Read the input stream and register the user in the DB
                System.out.println("registering a new user!");
                ErsUser newUser = mapper.readValue(req.getInputStream(), ErsUser.class);
                userService.register(newUser);
                System.out.println(newUser);

                // Write back the response
                String newUserJSON = mapper.writeValueAsString(newUser);
                respWriter.write(newUserJSON);

                // Set the status
                resp.setStatus(201); // 201 = CREATED
                System.out.println(resp.getStatus());

        } catch(MismatchedInputException mie) {

            resp.setStatus(400); // 400 = BAD REQUEST

            ErrorResponse err = new ErrorResponse(400, "Bad Request: Malformed user object found in request body");
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
     * READ operation
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("in UserServlet doGet");

        // TODO validate that a user is the same user that is trying to view itself
        // TODO validate that a user is an admin before viewing all users

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        System.out.println(req.getParameter("ers_user_id"));

        try {

            // To view PROFILE, use doGet in UserServlet
            // if the user is NOT an admin, find their id and return their corresponding ErsUser object
            if (req.getSession().getAttribute("adminId") == null) {

                System.out.println("User is not an admin. Finding their profile!");

                if (req.getSession().getAttribute("userWhoIsDefinitelyAFinanceManager") != null) { // user is a finance manager

                    Object managerId = req.getSession().getAttribute("userWhoIsDefinitelyAFinanceManager");
                    String managerIdAsString = String.valueOf(managerId);
                    Integer managerIdAsInteger = Integer.parseInt(managerIdAsString);
                    ErsUser userToSeeProfile = userService.getUserById(managerIdAsInteger);
                    String userToSeeProfileJSON = mapper.writeValueAsString(userToSeeProfile);
                    respWriter.write(userToSeeProfileJSON);
                    resp.setStatus(200); // 200 = OK

                } else if (req.getSession().getAttribute("authorIdToFindReimbs") != null) { // user is an employee

                    Object employeeId = req.getSession().getAttribute("authorIdToFindReimbs");
                    String employeeIdAsString = String.valueOf(employeeId);
                    Integer employeeIdAsInteger = Integer.parseInt(employeeIdAsString);
                    ErsUser userToSeeProfile = userService.getUserById(employeeIdAsInteger);
                    String userToSeeProfileJSON = mapper.writeValueAsString(userToSeeProfile);
                    respWriter.write(userToSeeProfileJSON);
                    resp.setStatus(200); // 200 = OK

                }
            }
            String idParam = req.getParameter("ers_user_id");

            if (idParam != null) {

                int id = Integer.parseInt(idParam);
                ErsUser user = userService.getUserById(id);
                String userJSON = mapper.writeValueAsString(user);
                respWriter.write(userJSON);

            } else {

                Set<ErsUser> users = userService.getAllUsers();

                String usersJSON = mapper.writeValueAsString(users);
                respWriter.write(usersJSON);

                resp.setStatus(200); // 200 = OK
                System.out.println(resp.getStatus());

            }




        } catch (ResourceNotFoundException rnfe) {

            resp.setStatus(404);

            ErrorResponse err = new ErrorResponse(404, rnfe.getMessage());
            respWriter.write(mapper.writeValueAsString(err));

        } catch(NumberFormatException | InvalidRequestException e) {
            resp.setStatus(400); // 400 Bad Request
            ErrorResponse err = new ErrorResponse(400, "Malformed user id parameter value provided.");
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
     * UPDATE operation
     * This method is used by admins to update users or render them inactive
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("In UserServlet doPut!");

        resp.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();

        try {

            System.out.println("Updating an existing user!");

            // find the original user to update
            Object userId = req.getSession().getAttribute("userIdToUpdate");
            System.out.println("This is the user ID: " + userId);

            String string = String.valueOf(userId); // turn the object to a string first
            System.out.println("This is the string version of the request value: " + string);

            String cleanString = string.replaceAll("\\D+", ""); // take the characters out of the string to leave just numbers
            System.out.println("This is the string with only numbers: " + cleanString);

            Integer integer = Integer.parseInt(cleanString); // parse the string for int
            System.out.println("This is that same string as an integer: " + integer);

            ErsUser userToUpdate = userService.getUserById(integer); // find the user
            System.out.println("This is the user that was found based on the request: " + userToUpdate);

            // find the updated information to set the original user to
            ErsUser userWithUpdatedInfo = mapper.readValue(req.getInputStream(), ErsUser.class);

            System.out.println("This contains the updated information:" + userWithUpdatedInfo.toString());
            System.out.println("Note that the ID will be null...");

            // reassign the fields to the new info in the service layer
            userToUpdate.setFirstName(userWithUpdatedInfo.getFirstName());
            userToUpdate.setLastName(userWithUpdatedInfo.getLastName());
            userToUpdate.setEmail(userWithUpdatedInfo.getEmail());
            userToUpdate.setUsername(userWithUpdatedInfo.getUsername());
            userToUpdate.setPassword(userWithUpdatedInfo.getPassword());
            userToUpdate.setRole(userWithUpdatedInfo.getRole());
            System.out.println(userToUpdate);

            // update the DB
            userService.update(userToUpdate);

            HttpSession session = req.getSession();
            session.setAttribute("userUpdated", userToUpdate); // assign that user to the session. TODO unset this attribute once they are updated?

            String userUpdatedJSON = mapper.writeValueAsString(userToUpdate);
            respWriter.write(userUpdatedJSON); // return the user (if found) to the response

            resp.setStatus(201); // 201 = CREATED because new information?

        } catch (Exception e) {
            e.printStackTrace(); // TODO custom exceptions
        }
    }
}


// don't need this; put it in doPut
// first, see if the userToUpdate attribute exists
// find the original user in the database
//
//            if (req.getSession().getAttribute("userIdToUpdate") != null) { // if this attribute is NOT null, then it is an update
//
//                    System.out.println("User to update exists!");
//
//                    // find the original user to update
//                    Object userId = req.getSession().getAttribute("userIdToUpdate");
//                    System.out.println("This is the user ID: " + userId);
//
//                    String string = String.valueOf(userId); // turn the object to a string first
//                    System.out.println("This is the string version of the request value: " + string);
//
//                    String cleanString = string.replaceAll("\\D+", ""); // take the characters out of the string to leave just numbers
//                    System.out.println("This is the string with only numbers: " + cleanString);
//
//                    Integer integer = Integer.parseInt(cleanString); // parse the string for int
//                    System.out.println("This is that same string as an integer: " + integer);
//
//                    ErsUser userToUpdate = userService.getUserById(integer); // find the user
//                    System.out.println("This is the user that was found based on the request: " + userToUpdate);
//
//                    // find the updated information to set the original user to
//                    ErsUser userWithUpdatedInfo = mapper.readValue(req.getInputStream(), ErsUser.class);
//
//        System.out.println("This contains the updated information:" + userWithUpdatedInfo.toString());
//        System.out.println("Note that the ID will be null...");
//
//        // reassign the fields to the new info in the service layer
//        userToUpdate.setFirstName(userWithUpdatedInfo.getFirstName());
//        userToUpdate.setLastName(userWithUpdatedInfo.getLastName());
//        userToUpdate.setEmail(userWithUpdatedInfo.getEmail());
//        userToUpdate.setUsername(userWithUpdatedInfo.getUsername());
//        userToUpdate.setPassword(userWithUpdatedInfo.getPassword());
//        userToUpdate.setRole(userWithUpdatedInfo.getRole());
//        System.out.println(userToUpdate);
//
//        // update the DB
//        userService.update(userToUpdate);
//
//        HttpSession session = req.getSession();
//        session.setAttribute("userUpdated", userToUpdate); // assign that user to the session. TODO unset this attribute once they are updated?
//
//        String userUpdatedJSON = mapper.writeValueAsString(userToUpdate);
//        respWriter.write(userUpdatedJSON); // return the user (if found) to the response
//
//        resp.setStatus(201); // 201 = CREATED because new information?
//
//        }
