package com.revature.ers.services;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.util.AppState;
import com.revature.ers.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserService {

    private UserRepository userRepo;
    public static AppState app = new AppState();

    public UserService(UserRepository repo) {
        userRepo = repo;
//        userRepo = new UserRepository(); // tight coupling! ~hard~ impossible to unit test
    }


    /**
     * Make sure user exists.
     * @param username
     * @param password
     */
    public void authenticate(String username, String password) {

        // validate that the provided username and password are not non-values
        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid credential values provided!");
        }

        /**
         * Uses a READ operation from UserRepository class
         */
        ErsUser authUser = userRepo.findUserByCredentials(username, password)
                .orElseThrow(AuthenticationException::new);

        app.setCurrentUser(authUser);

    }

    /**
     * CREATE operation (Service Layer)
     * @param newUser
     */
    public void register(ErsUser newUser) {

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user field values provided during registration!");
        }

        Optional<ErsUser> existingUser = userRepo.findUserByUsername(newUser.getUsername());
        if (existingUser.isPresent()) {
            // TODO implement a custom ResourcePersistenceException
            throw new RuntimeException("Provided username is already in use!");
        }

        newUser.setRole(Role.EMPLOYEE);
        userRepo.save(newUser);
        System.out.println(newUser);
        /**
         * Will not be setting the current user to the registered
         * user here, because admin's are the only users who will
         * be registering new users, and we still want them to be
         * the current user after they register someone
         */
//        app.setCurrentUser(newUser);

    }

    /**
     * READ operation (Service Layer)
     * @param id
     * @return
     */
    public Optional<ErsUser> findById(Integer id) {

        return userRepo.findById(id);
    }

    /**
     * READ operation
     * filter
     * not in use yet...may not use at all
     * @param role
     * @return
     */
    public Set<Optional<ErsUser>> findAllByRole(Role role) {
        return new HashSet<>();
    }

    /**
     * UPDATE operation (Service Layer)
     * @param ersUser
     */
    public void update(ErsUser ersUser) {

        /**
         * Before updating, check that the user is valid.
         */
        if (!isUserValid(ersUser)) {
            throw new InvalidRequestException("User not found...");
        }

        ersUser.setUsername(ersUser.getUsername());
        ersUser.setPassword(ersUser.getPassword());
        ersUser.setFirstName(ersUser.getFirstName());
        ersUser.setLastName(ersUser.getLastName());
        ersUser.setEmail(ersUser.getEmail());
        ersUser.setRole(ersUser.getRole());
        userRepo.update(ersUser);
    }

    /**
     * DELETE operation (Service Layer)
     * @param ersUser
     */
    public void deleteUser(ErsUser ersUser) {
        userRepo.deleteById(ersUser.getId());
    }

    public boolean isUserValid(ErsUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        if (user.getPassword() == null || user.getPassword().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        return true;
    }
}
