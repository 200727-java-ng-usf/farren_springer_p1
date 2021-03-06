package com.revature.ers.services;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.exceptions.ResourceNotFoundException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.repos.UserRepository;

import java.util.Optional;
import java.util.Set;


public class UserService {

    private UserRepository userRepo = new UserRepository();

    /**
     * CREATE operation
     * @param newUser
     */
    public void register(ErsUser newUser) {

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user field values provided during registration!");
        }

        Optional<ErsUser> existingUser = userRepo.findUserByUsername(newUser.getUsername());
        if (existingUser.isPresent()) {
            // TODO implement a custom ResourcePersistenceException
            throw new RuntimeException("Provided username is already in use!"); // TODO custom exception?
        }

//        newUser.setRole(Role.EMPLOYEE);
        userRepo.save(newUser);
        System.out.println(newUser);
//        app.setCurrentUser(newUser);

    }

    /**
     * READ operation
     * @return
     */
    public Set<ErsUser> getAllUsers() {

        Set<ErsUser> users = userRepo.findAllUsers();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return users;
    }

    /**
     * READ operation
     * @param username
     * @param password
     * @return
     */
    public ErsUser authenticate(String username, String password) {

        // validate that the provided username and password are not non-values
        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid credential values provided!");
        }

        return userRepo.findUserByCredentials(username, password)
                                    .orElseThrow(AuthenticationException::new);

//        app.setCurrentUser(authUser);

    }

    /**
     * READ operation
     * @param id
     * @return
     */
    public ErsUser getUserById(int id) {

        if(id <= 0) {
            throw new InvalidRequestException("The provided id cannot be less than or equal to zero.");
        }

        return userRepo.findUserById(id)
                        .orElseThrow(ResourceNotFoundException::new);

    }

    /**
     * Validation
     * @param username
     * @return
     */
    public boolean isUsernameAvailable(String username) {
        ErsUser user = userRepo.findUserByUsername(username).orElse(null); // todo custom exception?
        return user == null;
    }

    /**
     * Validation
     * @param email
     * @return
     */
    public boolean isEmailAvailable(String email) {
        ErsUser user = userRepo.findUserByEmail(email).orElse(null); // todo custom exception?
        return user == null;
    }

    /**
     * UPDATE operation
     * @param updatedUser
     */
    public void update(ErsUser updatedUser) {

        if (!isUserValid(updatedUser)) {
            throw new InvalidRequestException("User not found...");
        }

        userRepo.update(updatedUser);

    }

    /**
     * Validates that the given user and its fields are valid (not null or empty strings). Does
     * not perform validation on id or role fields.
     * Convenience method to be used in READ operation methods
     * @param user
     * @return true or false depending on if the user was valid or not
     */
    public boolean isUserValid(ErsUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        if (user.getPassword() == null || user.getPassword().trim().equals("")) return false;
        return true;
    }


}





//        // updated user will either have field values that are new or that are assigned
//        // to the original values, so it is okay to reassign all of them.
//        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
//
//            String sql = "UPDATE project1.ers_users "
//                    + "SET email = '" + updatedUser.getEmail() + "', "
//                    + "username = '" + updatedUser.getUsername() + "', "
//                    + "password = '" + updatedUser.getPassword() + "', "
//                    + "first_name = '" + updatedUser.getFirstName() + "', "
//                    + "last_name = '" + updatedUser.getLastName() + "', "
//                    + "user_role_id = '" + updatedUser.getRole().ordinal() + "' "
////                    + "', "
//                    + "WHERE ers_user_id = " + updatedUser.getId();
//
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.executeUpdate(); //
//            pstmt.close();
//
//        } catch (SQLException sqle) {
//            sqle.printStackTrace();
//        }
//
//        return true;