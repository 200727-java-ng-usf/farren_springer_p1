package com.revature.ers.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FinanceManagerController {

    public static void financeManagerFinder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ErsUser financeManager = new ErsUser("financeManagerUsername","password","firstname", "lastname", "financeManagerEmail", Role.FINANCE_MANAGER);

        resp.getWriter().write(new ObjectMapper().writeValueAsString(financeManager));
    }
}
