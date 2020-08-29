package com.revature.ers.servlets;

import com.revature.ers.controllers.HomeController;
import com.revature.ers.controllers.LoginController;

import javax.servlet.http.HttpServletRequest;

/**
 * Parse the URI to see what wildcard we are using
 */

public class RequestHelper {

    public static String process(HttpServletRequest req) {

        System.out.println("THIS is the current URI active: " + req.getRequestURI());

        /**
         * Switch cases for possible wildcard "*" after api/ in url path
         */
        switch(req.getRequestURI()) {
            case "/farren_springer_p1/api/login":
                System.out.println("in login case");
                // NOT modularized
                // return "/html/login.html"; // better than a "break" or your money back

                // modularized
                return LoginController.login(req);

            case "/farren_springer_p1/api/home":
                System.out.println("in home case");
                // NOT modularized
                // return "/html/home.html";
                // modularized
                return HomeController.home();
            default:
                System.out.println("in default");
                return "/html/badlogin.html";
        }

//        return "/html/home.html";
    }
}
