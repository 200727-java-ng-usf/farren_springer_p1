package com.revature.ers.controllers.admin;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * RegisterController performs a CREATE operation on the project1 schema.
 * This controller should only be accessible to users with role ADMIN
 */
public class RegisterController {

    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String registerNewUser(HttpServletRequest req) throws IOException {

        if(!req.getMethod().equals("POST")) {
            return "/html/admin/register.html";
        }

        // TODO authenticate that the user's role field is ADMIN

        // acquire the form data
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        ErsUser employee = new ErsUser(username, password, firstName, lastName, email); // ?
        System.out.println(employee);

        try {

            userService.register(employee);

            req.getSession().setAttribute("loggedUsername", username);
            req.getSession().setAttribute("loggedPassword", password);
            req.getSession().setAttribute("loggedFirstName", firstName);
            req.getSession().setAttribute("loggedLastName", lastName);
            req.getSession().setAttribute("loggedEmail", email);


            ErsUser newUser = new ErsUser(username, password, firstName, lastName, email);
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonGenerator g = mapper.getFactory().createGenerator(new FileOutputStream("C:/Users/Farren/Desktop/WorkFolder/farren_springer_p1/src/main/resources/project1data.json"));

                mapper.writeValue(g, newUser);
                mapper.writeValue(g, newUser);

                g.close();
            } catch (IOException e) {
                e.printStackTrace();
            }




            return "/api/home";

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/api/badlogin.html";
        }

    }
}
