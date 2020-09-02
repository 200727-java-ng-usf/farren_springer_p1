package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.models.ErsUser;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService(new UserRepository());

    /*
        doGet can be used to respond to requests for:
            - get all users
            - get a user by id
            - get a user by username
            - etc.
        determining which of these you want to do will require that you parse
        the requestURI to see which operation should be performed:
            - GET request to /users = get all users
            - GET request to /users/id/1 = get the user with id 1
            - GET request to /users?username=aanderson = get the user with the username aanderson
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter respWriter = resp.getWriter(); // to write to screen
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json");

        System.out.println("Retrieving all users...");
        Set<ErsUser> users = userService.getAllUsers();
        String usersJSON = mapper.writeValueAsString(users); // to get users as strings
        respWriter.write(usersJSON); // write the users to the browser

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter respWriter = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json");

        try {

            InputStream reqBody = req.getInputStream();
            ErsUser newUser = mapper.readValue(reqBody, ErsUser.class); // tells Jackson how to map the req body
            userService.register(newUser); // after this line, the user will have a role and id
            String registeredUserJSON = mapper.writeValueAsString(newUser);
            respWriter.write(registeredUserJSON);
            resp.setStatus(201); // created

        } catch (Exception e) {

        }
    }
}