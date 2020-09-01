package com.revature.ers.controllers.employee;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO JSON tokens for viewing?
 */
public class ViewMyReimbsController {

    public static String submit(HttpServletRequest req) {
        if(!req.getMethod().equals("POST")) {
            return "/html/viewmyreimbs.html";
        }

        return "/api/home";

    }
}
