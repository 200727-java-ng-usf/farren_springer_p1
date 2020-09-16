package com.revature.ers.services;

import com.revature.ers.exceptions.InvalidRequestException;
import com.revature.ers.exceptions.ResourceNotFoundException;
import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.Role;
import com.revature.ers.models.Status;
import com.revature.ers.models.Type;
import com.revature.ers.repos.ReimbRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ReimbServiceTests {

    private ReimbService sut;
    private ReimbRepository mockReimbRepo = Mockito.mock(ReimbRepository.class);
    Set<ErsReimbursement> mockReimbs = new HashSet<>();

    @Before
    public void setup() {
        sut = new ReimbService();
        Timestamp timestamp = new Timestamp(1930, 4, 10, 8, 20, 20, 5);
        mockReimbs.add(new ErsReimbursement(90, 100.00, timestamp, timestamp, "Description", 1, 2, Status.PENDING, Type.LODGING));
        mockReimbs.add(new ErsReimbursement(91, 100.00, timestamp, timestamp, "Description", 1, 2, Status.PENDING, Type.TRAVEL));
        mockReimbs.add(new ErsReimbursement(92, 100.00, timestamp, timestamp, "Description", 1, 2, Status.PENDING, Type.FOOD));
        mockReimbs.add(new ErsReimbursement(93, 100.00, timestamp, timestamp, "Description", 1, 2, Status.PENDING, Type.OTHER));
    }

    @After
    public void tearDown() {
        sut = null;
        mockReimbs.removeAll(mockReimbs);
    }

    // TODO test getAllReimbs()
//    @Test
//    public void getAllReimbsReturnsSet() {
//        // arrange
//        Set<ErsReimbursement> expectedReimbSet = mockReimbs;
//        Mockito.when(mockReimbRepo.findAllReimbs())
//                .thenReturn((mockReimbs));
//
//        // act
//        Set<ErsReimbursement> actualReimbSet = sut.getAllReimbs();
//
//        // assert
//        Assert.assertEquals(expectedReimbSet, actualReimbSet);
//    }
    // test that it returns a set
    // mock the repo?
    // test that it throws ResourceNotFoundException
    // TODO test getAllByAuthorId(Integer authorId)
    // test that it returns the right author
    // mock the repo?
    // test that it throws ResourceNotFoundException
    // TODO test register
    // test that it registers
    // test that it doesn't register a null user
    @Test (expected = InvalidRequestException.class)
    public void registerWithNullObject() { sut.register(null); }

    // TODO test getReimbById(int id)
    // TODO test resolve
    // TODO test update
    // update null account
    @Test (expected = InvalidRequestException.class)
    public void updateNullAccount() { sut.update(null); }
    // TODO test delete
    @Test(expected = InvalidRequestException.class)
    public void deleteInvalidAccount() {
        sut.delete(null);
    }
    // TODO test isReimbValid()
    @Test
    public void isReimbValidReturnsFalse() {
        ErsReimbursement mockReimb = null;

        Assert.assertEquals(false, sut.isReimbValid(mockReimb));
    }



}
