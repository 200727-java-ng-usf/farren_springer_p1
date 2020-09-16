package com.revature.ers.services;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.exceptions.ResourceNotFoundException;
import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;
import com.revature.ers.repos.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserServiceTests {

    private UserService sut;
    private UserRepository mockUserRepo = Mockito.mock(UserRepository.class);
    Set<ErsUser> mockUsers = new HashSet<>();

    @Before
    public void setup() {
        sut = new UserService();
        mockUsers.add(new ErsUser(1, "Adam", "Inn", "admin", "secret", "admin@app.com", Role.ADMIN));
        mockUsers.add(new ErsUser(2, "Manny", "Gerr", "manager", "manage", "manager@app.com", Role.FINANCE_MANAGER));
        mockUsers.add(new ErsUser(3, "Alice", "Anderson", "aanderson", "password", "admin@app.com", Role.EMPLOYEE));
        mockUsers.add(new ErsUser(4, "Bob", "Bailey", "bbailey", "dev", "dev@app.com", Role.EMPLOYEE));
    }

    @After
    public void tearDown() {
        sut = null;
        mockUsers.removeAll(mockUsers);
    }

    @Test(expected = InvalidRequestException.class)
    public void authenticationWithInvalidCredentials() {

        // Arrange
        // nothing to do here for this test; nothing to mock or expect

        // Act
        sut.authenticate("", "");

        // Assert
        // nothing here, because the method should have raised an exception

    }

    // TODO test getAllUsers does not return empty (bc it is not empty)
    // TODO mock the save method in register?

    // TODO test getUserById
    @Test (expected = InvalidRequestException.class)
    public void getInvalidUserByIdReturnsInvalidRequestException() {
        // Act
        sut.getUserById(-10); // there is no user with this ID
    }

    @Test (expected = ResourceNotFoundException.class)
    public void getUserByIdThatDoesNotExist() {
        sut.getUserById(300); // user with ID 300 does not exist
    }

    @Test (expected = InvalidRequestException.class)
    public void updateUserThatDoesNotExist() {
        // arrange
        ErsUser nullUser = null;

        // act
        sut.update(nullUser);
    }

    @Test (expected = RuntimeException.class)
    public void registerAUserThatAlreadyExists() {
        // arrange
        ErsUser validUser = new ErsUser("Sue", "Bob", "sbob", "password", "herEmail");
        sut.register(validUser);

        // act
        sut.register(validUser);

    }


//    @Test
//    public void authenticationWithValidCredentials() {
//
//        // Arrange. Mock findUserByCredentials, because we want the test to only test authenticate
//        ErsUser expectedUser = new ErsUser("Adam", "Inn", "admin", "secret", "ainn@revature.com", Role.ADMIN);
//        Mockito.when(mockUserRepo.findUserByCredentials("admin", "secret"))
//                .thenReturn(Optional.of(expectedUser));
//
//        // Act
//        sut.authenticate("admin", "secret");
//        ErsUser actualResult = app.getCurrentUser();
//
//        // Assert
//        Assert.assertEquals(expectedUser, actualResult);
//
//    }

    @Test(expected = AuthenticationException.class)
    public void authenticationWithUnknownCredentials() {
        sut.authenticate("garbage", "user");
    }

    @Test (expected = InvalidRequestException.class)
    public void registerWithNullErsUser() {
        // Arrange
        // nothing to do here for this test; nothing to mock or expect

        // Act
        sut.register(null);

        // Assert
        // nothing here, because the method should have raised an exception
    }

    @Test (expected = NullPointerException.class)
    public void getAllUsersWithNoUsers() {
        // arrange
        sut = null;
        mockUsers.removeAll(mockUsers);

        // act
        sut.getAllUsers();
    }

    // TODO test that asserts that getAllUsers returns all users
    // TODO test for isUsernameAvailable where user does not exist
    // TODO test for isEmailAvailable where user does not exist


//    @Test (expected = RuntimeException.class)
//    public void registerWithUserAlreadyExists() {
//        // Arrange. Mock findUserByUsername, because we want the test to only test register
//        ErsUser expectedUser = new ErsUser("Manny", "Gerr", "manager", "manage", "manager@app.com", Role.FINANCE_MANAGER);
//        Mockito.when(mockUserRepo.findUserByUsername("manager"))
//                .thenReturn(Optional.of(expectedUser));
////        // also mock save method
////        Mockito.when(mockUserRepo.save(expectedUser))
////                .thenReturn(Optional.of(expectedUser));
//
//        // Act
//        sut.register(expectedUser);
//        ErsUser actualResult = app.getCurrentUser();
//
//        // Assert
//        Assert.assertEquals(expectedUser, actualResult);
//    }

    //    @Test
//    public void registerWithValidErsUser() {
//        // Arrange. Mock findUserByUsername, because we want the test to only test register
//        ErsUser expectedUser = new ErsUser("Manny", "Gerr", "manager", "manage", "manager@app.com", Role.MANAGER);
//        Mockito.when(mockUserRepo.findUserByUsername("manager"))
//                .thenReturn(Optional.of(expectedUser));
//        // also mock save method
//        Mockito.when(mockUserRepo.save(expectedUser))
//                .thenReturn(Optional.of(expectedUser));
//
//        // Act
//        sut.register(expectedUser);
//        ErsUser actualResult = app.getCurrentUser();
//
//        // Assert
//        Assert.assertEquals(expectedUser, actualResult);
//    }
//
//    @Test (expected = InvalidRequestException.class)
//    public void updateEmailWithInvalidUser() {
//        sut.updateEmail("", null);
//    }
//
//    @Test (expected = InvalidRequestException.class)
//    public void updateFirstNameWithInvalidUser() {
//        sut.updateFirstName("", null);
//    }
//
//    @Test (expected = InvalidRequestException.class)
//    public void updateLastNameWithInvalidUser() {
//        sut.updateLastName("", null);
//    }

//    @Test
//    public void updateEmailWithValidUser() {
//        // Arrange
//        String expectedEmail = "newEmail@email.com";
//        ErsUser actualResult = new ErsUser("Alice", "Anderson", "aanderson", "password", "admin@app.com", Role.BASIC_MEMBER);
//        Mockito.when(mockUserRepo.updateEmail("newEmail@email.com", 3))
//                .thenReturn(Optional.of(actualResult));
//
//        // Act
//        sut.updateEmail("newEmail@email.com", actualResult);
//
//        // Assert
//        Assert.assertEquals(expectedEmail, actualResult.getEmail());
//    }
}
