package com.revature.ers.controllers.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Status;
import com.revature.ers.models.Type;
import com.revature.ers.repos.ReimbRepository;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.ReimbService;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.sql.Timestamp;

public class ViewAllReimbsController {

    private static ReimbRepository reimbRepo = new ReimbRepository();
    private static ReimbService reimbService = new ReimbService(reimbRepo);
    private static UserRepository userRepo = new UserRepository();
    private static UserService userService = new UserService(userRepo);

    public static String viewAllReimbs(HttpServletRequest req) throws JsonProcessingException {

        System.out.println("In viewAllReimbs method");

        return "/html/fmanager/viewallreimbs.html";

    }
}
