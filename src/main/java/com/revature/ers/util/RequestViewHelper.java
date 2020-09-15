package com.revature.ers.util;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {

    public String process(HttpServletRequest req) {

        System.out.println("In RequestViewHelper process()");
        System.out.println(req.getRequestURI());

        switch (req.getRequestURI()) {

            case "/login.view": // for AWS deployment
            case "/farren_springer_p1/login.view": // for local deployment
                return "partials/login.html";

            case "/register.view":
            case "/farren_springer_p1/register.view":
                return "partials/register.html";

            case "/home.view":
            case "/farren_springer_p1/home.view":

                String principal = String.valueOf(req.getSession().getAttribute("principal"));
                if (principal == null || principal.equals("")) {
                    return "partials/login.html";
                }

                return "partials/home.html";
            case "/users.view":
            case "/farren_springer_p1/users.view":
                return "partials/users.html";

            case "/reimbs.view":
            case "/farren_springer_p1/reimbs.view":
                return "partials/reimbs.html";

            case "/submit.view":
            case "/farren_springer_p1/submit.view":
                return "partials/submit.html";

            case "/updateuser.view":
            case "/farren_springer_p1/updateuser.view":
                return "partials/updateuser.html";

            case "/updatereimb.view":
            case "/farren_springer_p1/updatereimb.view":
                return "partials/updatereimb.html";

            default:
                return null;

        }

    }
}
