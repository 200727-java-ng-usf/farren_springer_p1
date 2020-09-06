package com.revature.ers.controllers.employee;

import com.revature.ers.repos.ReimbRepository;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.ReimbService;

import javax.servlet.http.HttpServletRequest;


/**
 * RemoveReimbController takes form data to direct employees to either
 * the UpdateReimbController or the RemoveReimbController.
 * This controller should only be accessible to users with role EMPLOYEE
 */
public class ChooseToEditController {

    private static ReimbRepository reimbRepo = new ReimbRepository();
    private static ReimbService reimbService = new ReimbService(reimbRepo);


    public static String chooseToEdit(HttpServletRequest req) {


        UserRepository userRepo = new UserRepository();

        System.out.println("In chooseToEdit method in ChooseToEditController in employee package");

//        if(!req.getMethod().equals("POST")) {
//            return "/html/employee/.html";
//        }

        System.out.println("Still here");

        // acquire the form data
        String reimbIdChosenByEmployee = req.getParameter("reimbIdChosenByEmployee");
        System.out.println("Parameter was: " + reimbIdChosenByEmployee);
        String choseToEditEmployee = req.getParameter("choseToEditEmployee");
        System.out.println("Parameter was: " + choseToEditEmployee);

        // Re-cast anything necessary
        // Integer reimbIdChosenByEmployeeAsInt = Integer.parseInt(reimbIdChosenByEmployee);

        try {

            req.getSession().setAttribute("loggedReimbIdChosenByEmployee", reimbIdChosenByEmployee);
            System.out.println("Attribute set!");
            req.getSession().setAttribute("loggedChoseToEditEmployee", choseToEditEmployee);
            System.out.println("Attribute set!");

            // switch case here. Switch based on choice to update, remove, or go back
            switch(choseToEditEmployee) {
                case "update":
                    System.out.println("in update case");
                    return "/html/employee/updatereimb.html";
                case "remove":
                    System.out.println("in remove case");
                    return "/api/removeReimb";
                case "back":
                    System.out.println("in back case");
                    return "/html/employee/employeedash.html";
                default:
                    System.out.println("in default");
                    return "/html/badlogin.html";
            }

        } catch (NullPointerException npe) {
            npe.printStackTrace();
            return "/api/badlogin.html";
        }



    }
}
