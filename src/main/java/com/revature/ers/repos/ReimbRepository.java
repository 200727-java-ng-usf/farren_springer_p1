package com.revature.ers.repos;

import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import static com.revature.ers.services.UserService.app;

public class ReimbRepository implements CrudRepository<ErsReimbursement> {

    /**
     * Extract common query clauses into a easily referenced member for reusability.
     */
    private String baseQuery = "SELECT * FROM project1.ers_reimbursements er " +
            "JOIN project1.ers_reimbursement_types rt " +
            "ON er.reimb_type_id = rt.reimb_type_id " +
            "JOIN project1.ers_reimbursement_statuses rs " +
            "ON er.reimb_status_id = rs.reimb_status_id ";

    public ReimbRepository() {

//        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
    }

    @Override
    /**
     * CREATE operation
     * @param reimbursement
     * @return
     */
    public Optional<ErsReimbursement> save(ErsReimbursement reimbursement) {

        System.out.println("In save method");

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "INSERT INTO project1.ers_reimbursements (amount, reimb_type_id, description, submitted, reimb_status_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

            // second parameter here is used to indicate column names that will have generated values
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"reimb_id"}); // Uses the Connection object
            pstmt.setDouble(1, reimbursement.getAmount()); // the first ? mark and what value it will be in the query
            pstmt.setInt(2, reimbursement.getReimbursementTypeId().ordinal() + 1);
            pstmt.setString(3, reimbursement.getDescription());
            pstmt.setTimestamp(4, reimbursement.getSubmitted());
            pstmt.setInt(5, reimbursement.getReimbursementStatusId().ordinal() + 1);
//            pstmt.setInt(3, app.getCurrentUser().getId());

            int rowsInserted = pstmt.executeUpdate(); // execute the prepared statement using Connection object that uses the db

            if (rowsInserted != 0) {

                ResultSet rs = pstmt.getGeneratedKeys(); // get the primary keys created by a sequence

                rs.next(); // iterate through the result set
                reimbursement.setId(rs.getInt(1)); // get the first column from the db and
                // assign it to the id of the account object

            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }

    /**
     * Use SELECT STATEMENTS here to show on screen
     * @return
     */
    @Override
    public Set<Optional<ErsReimbursement>> findAll() {
        return null;
    }

    @Override
    public Optional<ErsReimbursement> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean update(ErsReimbursement ersReimbursement) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }
}
