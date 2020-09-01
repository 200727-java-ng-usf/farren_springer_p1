package com.revature.ers.util;

import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.ErsUser;
import com.revature.ers.repos.ReimbRepository;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.services.ReimbService;
import com.revature.ers.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    private BufferedReader console;
    private ErsUser currentUser;
    private boolean appRunning;
    private ErsReimbursement currentReimbursement;

    private ReimbRepository reimbRepo = new ReimbRepository();

    public AppState() {
//        System.out.println("[LOG] - Initializing application...");

        appRunning = true;
        console = new BufferedReader(new InputStreamReader(System.in));

        final UserRepository userRepo = new UserRepository();
        final ReimbRepository reimbRepo = new ReimbRepository();
        final UserService userService = new UserService(userRepo);
        final ReimbService reimbService = new ReimbService(reimbRepo);

//        System.out.println("[LOG] - Application initialization complete.");
    }

    public BufferedReader getConsole() {
        return console;
    }

    public ErsUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(ErsUser currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isAppRunning() {
        return appRunning;
    }

    public void setAppRunning(boolean appRunning) {
        this.appRunning = appRunning;
    }

    public ErsReimbursement getCurrentReimbursement() { return currentReimbursement; }

    public void setCurrentReimbursement(ErsReimbursement currentReimbursement) { this.currentReimbursement = currentReimbursement; }

    public void invalidateCurrentSession() {
        currentUser = null;
    }

    public boolean isSessionValid() {
        return (this.currentUser != null);
    }

}
