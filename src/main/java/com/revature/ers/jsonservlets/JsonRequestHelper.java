package com.revature.ers.jsonservlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.controllers.admin.AdminController;
import com.revature.ers.controllers.employee.EmployeeController;
import com.revature.ers.controllers.financemanager.FinanceManagerController;
import com.revature.ers.models.ErsUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonRequestHelper {

    public static void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println(req.getRequestURI());

        // prune ending of URI string and put into a select statement?

        switch(req.getRequestURI()) {
            case "/farren_springer_p1/json/admin":
                AdminController.adminFinder(req, resp);
                break;
            case "/farren_springer_p1/json/employee":
                EmployeeController.employeeFinder(req, resp);
            case "/farren_springer_p1/json/financemanager":
                FinanceManagerController.financeManagerFinder(req, resp);
            default:
                ErsUser nullUser = new ErsUser("username", "password", "firstname", "lastname", "email");
                resp.getWriter().write(new ObjectMapper().writeValueAsString(nullUser));
        }
    }
}
