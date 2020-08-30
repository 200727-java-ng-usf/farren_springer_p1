package com.revature.ers.services;

import com.revature.ers.repos.ReimbRepository;

public class ReimbService {

    private static ReimbRepository reimbRepo;

    public ReimbService(ReimbRepository repo) {
//        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
        reimbRepo = repo;
    }
}
