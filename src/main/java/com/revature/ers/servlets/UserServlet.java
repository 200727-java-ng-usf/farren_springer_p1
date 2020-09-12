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

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // TODO validate that a user is the same user that is trying to view itself
        // TODO validate that a user is an admin before viewing all users

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        System.out.println(req.getParameter("ers_user_id"));

        try {

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
     * Used to handle incoming requests to register new users for the application.
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

            ErsUser newUser = mapper.readValue(req.getInputStream(), ErsUser.class);
            userService.register(newUser);
            System.out.println(newUser);
            String newUserJSON = mapper.writeValueAsString(newUser);
            respWriter.write(newUserJSON);
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
}
