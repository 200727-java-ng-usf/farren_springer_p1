package com.revature.ers.controllers.employee;

import javax.servlet.http.HttpServletRequest;

public class SubmitController {

    public static String submit(HttpServletRequest req) {
        if(!req.getMethod().equals("POST")) {
            return "/html/submitreimbform.html";
        }

        String amount = req.getParameter("amount");
        String reimbType = req.getParameter("reimb_type");

        /**
         * Ensure not null values are submitted
         */
        if(!(amount.equals(null) || reimbType.equals(""))) {
            // this logic will trigger when the amount is null or the type is empty
            return"/api/invalidinput";
        } else {
            req.getSession().setAttribute("loggedAmount", amount);
            req.getSession().setAttribute("loggedType", reimbType);
            return "/api/home";
        }
    }
}
