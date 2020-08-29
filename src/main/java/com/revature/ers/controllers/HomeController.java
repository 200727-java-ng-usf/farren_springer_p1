package com.revature.ers.controllers;

public class HomeController {

    public static String home() { // don't need HttpRequest object as param unless you are doing advanced logic
        // a TON of business logic could go here
        return "/html/home.html";
    }
}
