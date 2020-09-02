package com.revature.ers.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controllers handle the business logic of an endpoint
 */
public class AdminController {

    public static ErsUser adminFinder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ErsUser admin = new ErsUser("adminUsername","password","firstname", "lastname", "adminEmail", Role.ADMIN);

        System.out.println("in admin finder");
        resp.getWriter().write(new ObjectMapper().writeValueAsString(admin));
        return admin;
    }

}
