package com.revature.ers.servlets;

import com.revature.ers.controllers.HomeController;
import com.revature.ers.controllers.LoginController;
import com.revature.ers.controllers.admin.*;
import com.revature.ers.controllers.fmanager.ApproveReimbController;
import com.revature.ers.controllers.employee.*;
import com.revature.ers.controllers.fmanager.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Parse the URI to see what wildcard we are using
 */

public class RequestHelper {

    public static String process(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("THIS is the current URI active: " + req.getRequestURI());
        System.out.println("In process in RequestHelper");

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
                return HomeController.home(req, resp);
            /**
             * Admin cases
             */
            case "/farren_springer_p1/api/register":
                System.out.println("in register case");
                return RegisterController.registerNewUser(req);
            case "/farren_springer_p1/api/findUser":
                return FindUserController.findUser(req);
            case "/farren_springer_p1/api/updateOrDelete":
                return UpdateOrDeleteController.updateOrDelete(req);
            case "/farren_springer_p1/api/update":
                return UpdateUserController.updateUser(req);
            case "/farren_springer_p1/api/delete":
                return DeleteUserController.deleteUser(req);
            /**
             * Finance Manager cases
             */
            case "/farren_springer_p1/api/typeOrStatus":
                return TypeOrStatusController.directFromFilterChoice(req);
            case "/farren_springer_p1/api/type":
                // this case occurs because user clicked submit in type.html
                return PickATypeController.viewByType(req);
            case "/farren_springer_p1/api/status":
                // this case occurs because user clicked submit in status.html
                return PickAStatusController.viewByStatus(req);
            case "/farren_springer_p1/api/viewbytype":
                return "/viewallbytype"; // maps to servlet
            case "/farren_springer_p1/api/viewbystatus":
                return "/viewallbystatus"; // maps to servlet
            case "/farren_springer_p1/api/approveOrDeny":
                return ApproveOrDenyController.approveOrDeny(req);
            case "/farren_springer_p1/api/approve":
                return ApproveReimbController.approveReimb(req);
            case "/farren_springer_p1/api/deny":
                return DenyReimbController.denyReimb(req);
            /**
             * Employee cases
             */
            case "/farren_springer_p1/api/submitform":
                System.out.println("in submitform case");
                return SubmitController.submit(req);
            case "/farren_springer_p1/api/chooseToEdit":
                return ChooseToEditController.chooseToEdit(req);
            case "/farren_springer_p1/api/updateReimb":
                return UpdateReimbController.updateReimb(req);
            case "/farren_springer_p1/api/removeReimb":
                System.out.println("in remove reimb case");
                return RemoveReimbController.removeReimb(req);

            default:
                System.out.println("in default");
                return "/html/badlogin.html";
        }

//        return "/html/home.html";
    }
}
