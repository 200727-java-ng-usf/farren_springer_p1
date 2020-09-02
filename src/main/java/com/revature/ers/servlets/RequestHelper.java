package com.revature.ers.servlets;

import com.revature.ers.controllers.HomeController;
import com.revature.ers.controllers.LoginController;
import com.revature.ers.controllers.admin.RegisterController;
import com.revature.ers.controllers.EmployeeController;
import com.revature.ers.controllers.employee.SubmitController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Parse the URI to see what wildcard we are using
 */

public class RequestHelper {

    public static String process(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("THIS is the current URI active: " + req.getRequestURI());

        /**
         * Switch cases for possible wildcard "*" after api/ in url path
         */
        switch(req.getRequestURI()) { // change cases to just necessary? Login, Home, Employee, Admin, Manager, Default (badlogin.html)
            case "/farren_springer_p1/api/login":
                System.out.println("in login case");
                // NOT modularized
                // return "/html/login.html"; // better than a "break" or your money back
                // modularized
                return LoginController.login(req, resp);
            case "/farren_springer_p1/api/home":
                System.out.println("in home case");
                // NOT modularized
                // return "/html/home.html";
                // modularized
                return HomeController.home();
            case "/farren_springer_p1/api/submitform":
                System.out.println("in submitform case");
                return SubmitController.submit(req);
            case "/farren_springer_p1/api/register":
                System.out.println("in register case");
                return RegisterController.registerNewUser(req);
//            case "/farren_springer_p1/api/finduser":
//                System.out.println("in finduser case");
//                return FindUserController.findUser(req);
//            case "/farren_springer_p1/api/updateuser":
//                System.out.println("in updateuser case");
//                return UpdateController.updateUser(req);
            case "/farren_springer_p1/api/employee":
                System.out.println("in employee case");
                return EmployeeController.displayDash(req, resp);
            default:
                System.out.println("in default");
                return "/html/badlogin.html";
        }

//        return "/html/home.html";
    }
}
