package com.revature.ers.repos;

import com.revature.ers.exceptions.AuthenticationException;
import com.revature.ers.models.*;
import com.revature.ers.util.ConnectionFactory;

import javax.swing.text.html.Option;
import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.revature.ers.services.UserService.app;

/**
 * ReimbRepository connects the service layer to the persistence layer
 */
public class ReimbRepository implements CrudRepository<ErsReimbursement> {

    /**
     * Extract common query clauses into a easily referenced member for reusability.
     */
    private String baseQuery = "SELECT * FROM project1.ers_reimbursements er " +
            "JOIN project1.ers_reimbursement_types rt " +
            "ON er.reimb_type_id = rt.reimb_type_id " +
            "JOIN project1.ers_reimbursement_statuses rs " +
            "ON er.reimb_status_id = rs.reimb_status_id ";

    /**
     * Constructor
     */
    public ReimbRepository() {

//        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
    }

    /**
     * CREATE operation (Persistence Layer)
     * @param reimbursement
     * @return
     */
    @Override
    public Optional<ErsReimbursement> save(ErsReimbursement reimbursement) {

        System.out.println("In save method");

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "INSERT INTO project1.ers_reimbursements (amount, submitted, description, author_id, reimb_status_id, reimb_type_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            // second parameter here is used to indicate column names that will have generated values
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"reimb_id"}); // Uses the Connection object
            pstmt.setDouble(1, reimbursement.getAmount()); // the first ? mark and what value it will be in the query
            pstmt.setTimestamp(2, reimbursement.getSubmitted());
            pstmt.setString(3, reimbursement.getDescription());
            System.out.println("got description...");
            pstmt.setInt(4, app.getCurrentUser().getId()); // TODO author id
            System.out.println("got authorId");
            pstmt.setInt(5, reimbursement.getReimbursementStatusId().ordinal() + 1);
            pstmt.setInt(6, reimbursement.getReimbursementTypeId().ordinal());
//            pstmt.setInt(3, app.getCurrentUser().getId()); // for reference

            int rowsInserted = pstmt.executeUpdate(); // execute the prepared statement using Connection object that uses the db

            if (rowsInserted != 0) {

                ResultSet rs = pstmt.getGeneratedKeys(); // get the primary keys created by a sequence

                rs.next(); // iterate through the result set
                reimbursement.setId(rs.getInt(1)); // get the first column from the db and
                // assign it to the id of the account object

            }
//            // every time we save a reimbursement, we need to add it to the author's set
//            Set<ErsReimbursement> reimbSet = null;
//            reimbSet = app.getCurrentUser().getReimbursementSet(); // get the author's reimbursement set and assign it to a temporary set
//            reimbSet.add(reimbursement); // add the new reimbursement to that temporary set
//            app.getCurrentUser().setReimbursementSet(reimbSet); // set the current user's reimbset to the updated set

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }

    /**
     * READ operation (Persistence Layer)
     * @return
     */
    @Override
    public Set<ErsReimbursement> findAll() {

        Set<ErsReimbursement> reimbs = new HashSet<>();

        /**
         * Try with resources; the resource is the JDB
         */
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            reimbs = mapResultSet(rs);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return reimbs;
    }

    public Set<ErsReimbursement> findAllByType(Integer typeChoice) {

        Set<ErsReimbursement> reimbs = new HashSet<>();

        /**
         * Try with resources; the resource is the JDB
         */
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "SELECT * FROM project1.ers_reimbursements er " + "WHERE reimb_type_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, typeChoice);

            ResultSet rs = pstmt.executeQuery();
            reimbs = mapResultSet(rs);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return reimbs;
    }

    public Set<ErsReimbursement> findAllByStatus(Integer statusChoice) {

        Set<ErsReimbursement> reimbs = new HashSet<>();

        /**
         * Try with resources; the resource is the JDB
         */
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "SELECT * FROM project1.ers_reimbursements er " + "WHERE reimb_status_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, statusChoice);

            ResultSet rs = pstmt.executeQuery();
            reimbs = mapResultSet(rs);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return reimbs;
    }

    /**
     * READ operation (Persistence Layer)
     * @param authorId
     * @return
     */
    public Set<ErsReimbursement> findAllReimbsByAuthorId(Integer authorId) {

        System.out.println("In findAllReimbsByAuthorId method in ReimbRepository");

        Set<ErsReimbursement> reimbs = new HashSet<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE author_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, authorId);

            ResultSet rs = pstmt.executeQuery();
            reimbs = mapResultSet(rs);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return reimbs;
    }

    /**
     * READ operation (Persistence Layer)
     * @param id
     * @return
     */
//    @Override
    public Optional<ErsReimbursement> findById(Integer id) {

        System.out.println("In findById method in ReimbRepository");

        Optional<ErsReimbursement> _reimb = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE reimb_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            _reimb = mapResultSet(rs).stream().findFirst();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return _reimb;
    }

    /**
     * READ operation (Persistence Layer)
     * @param authorId
     * @return
     */
    public Optional<ErsReimbursement> findReimbByAuthorId(Integer authorId) {

        System.out.println("In findReimbByAuthorId method in ReimbRepository");

        Optional<ErsReimbursement> _reimb = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE author_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, authorId);

            ResultSet rs = pstmt.executeQuery();
            _reimb = mapResultSet(rs).stream().findFirst();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return _reimb;
    }



    /**
     * UPDATE operation (Persistence Layer)
     * @param ersReimbursement
     * @return
     */
    @Override
    public boolean update(ErsReimbursement ersReimbursement) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            System.out.println("This is the type ID: " + ersReimbursement.getReimbursementTypeId().ordinal());
            String sql = "UPDATE project1.ers_reimbursements "
                    + "SET amount = '" + ersReimbursement.getAmount() + "', "
                    + "submitted = '" + ersReimbursement.getSubmitted() + "', "
//                    + "resolved = '" + ersReimbursement.getResolved() + "', "
                    + "description = '" + ersReimbursement.getDescription() + "', "
                    + "resolver_id = '" + ersReimbursement.getResolverId() + "', "
                    + "reimb_status_id = '" + (ersReimbursement.getReimbursementStatusId().ordinal() + 1) + "', "
                    + "reimb_type_id = '" + ersReimbursement.getReimbursementTypeId().ordinal() + "' "
                    + "WHERE reimb_id = '" + ersReimbursement.getId() + "' ";

            // second parameter here is used to indicate column names that will have generated values
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            rs.next();

//            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return true;
    }

    /**
     * Custom UPDATE operation for Finance Managers
     * @param ersReimbursement
     * @return
     */
    public boolean updateByFManager(ErsReimbursement ersReimbursement) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "UPDATE project1.ers_reimbursements "
                    + "SET resolved = '" + ersReimbursement.getResolved() + "', "
                    + "resolver_id = '" + ersReimbursement.getResolverId() + "', "
                    + "reimb_status_id = '" + (ersReimbursement.getReimbursementStatusId().ordinal() + 1) + "' "
                    /**
                    * note that the last changed does not need a comma in the quotes
                    */
                    + "WHERE reimb_id = '" + ersReimbursement.getId() + "' ";

            // second parameter here is used to indicate column names that will have generated values
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            rs.next();

//            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return true;
    }

    /**
     * DELETE operation (Persistence Layer)
     * @param reimbId
     * @return
     */
    @Override
    public boolean deleteById(Integer reimbId) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "DELETE from project1.ers_reimbursements WHERE reimb_id = " + reimbId;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            rs.next();
            return true;

//            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return false;
    }

    /**
     * Convenience method for READ operations
     * reduce redundancy
     * @param rs
     * @return
     * @throws SQLException
     */
    private Set<ErsReimbursement> mapResultSet(ResultSet rs) throws SQLException {

        Set<ErsReimbursement> reimbs = new HashSet<>();

        /**
         * Extract results, set the temporary AppUser fields, and add the temp AppUser to the Set.
         */
        while (rs.next()) {
            ErsReimbursement temp = new ErsReimbursement();
            temp.setId(rs.getInt("reimb_id"));
            temp.setAmount(rs.getDouble("amount"));
            temp.setSubmitted(rs.getTimestamp("submitted"));
            temp.setResolved(rs.getTimestamp("resolved"));
            temp.setDescription(rs.getString("description"));
            temp.setReceipt(rs.getString("receipt"));
            temp.setAuthorId(rs.getInt("author_id"));
            temp.setResolverId(rs.getInt("resolver_id"));
            temp.setReimbursementStatusId(Status.getByStatus(rs.getString("reimb_status_id"))); // TODO may have complications bc ordinal in table
            temp.setReimbursementTypeId(Type.getByType(rs.getString("reimb_type_id"))); // TODO may have complications bc ordinal in table
            System.out.println(temp);
            reimbs.add(temp);
        }

        return reimbs;

    }

}
