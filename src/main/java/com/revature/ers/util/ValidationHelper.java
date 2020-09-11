package com.revature.ers.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ValidationHelper {

    private final UserService userService = new UserService();

    public boolean process(HttpServletRequest req) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        switch (req.getRequestURI()) {
            case "/farren_springer_p1/email.validate":
                String email = mapper.readValue(req.getInputStream(), String.class);
                return userService.isEmailAvailable(email);

            case "/farren_springer_p1/username.validate":
                String username = mapper.readValue(req.getInputStream(), String.class);
                return userService.isUsernameAvailable(username);

            default:
                return false;
        }

    }

}
