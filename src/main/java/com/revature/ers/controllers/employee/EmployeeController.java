package com.revature.ers.controllers.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmployeeController {

    public static void employeeFinder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ErsUser employee = new ErsUser("employeeUsername","password","firstname", "lastname", "employeeEmail", Role.EMPLOYEE);

        /**
         * response is returning the employee as a string
         */
        resp.getWriter().write(new ObjectMapper().writeValueAsString(employee));
    }
}
