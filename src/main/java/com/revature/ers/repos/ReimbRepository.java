package com.revature.ers.repos;

import com.revature.ers.models.ErsReimbursement;
import com.revature.ers.models.Role;
import com.revature.ers.models.Status;
import com.revature.ers.models.Type;
import com.revature.ers.util.ConnectionFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.time.LocalDate.now;

public class ReimbRepository {

    // extract common query clauses into an easily referenced member for reusability.
    private String baseQuery = "SELECT * FROM project1.ers_reimbursements er " +
                                "JOIN project1.ers_reimbursement_types ert " +
                                "ON er.reimb_type_id = ert.reimb_type_id " +
                                "JOIN project1.ers_reimbursement_statuses ers " +
                                "ON er.reimb_status_id = ers.reimb_status_id ";

    public ReimbRepository()  {
        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
    }

    public Optional<ErsReimbursement> findReimbById(int id) {

        Optional<ErsReimbursement> _reimb = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE er.reimb_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            Set<ErsReimbursement> result = mapResultSet(pstmt.executeQuery());

            if(!result.isEmpty()) {
                _reimb = result.stream().findFirst();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return _reimb;
    }

    public Set<ErsReimbursement> findAllReimbs() {

        Set<ErsReimbursement> reimbs = new HashSet<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            reimbs = mapResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reimbs;

    }


    public void save(ErsReimbursement newReimbursement) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "INSERT INTO project1.ers_reimbursements (amount, submitted, description, author_id, reimb_status_id, reimb_type_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            // second paramter here is used to indicate column names that will have generated values
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"reimb_id"});
            pstmt.setDouble(1, newReimbursement.getAmount());
            pstmt.setTimestamp(2, newReimbursement.getSubmitted());
            pstmt.setString(3, newReimbursement.getDescription());
            pstmt.setInt(4, newReimbursement.getAuthorId());
            pstmt.setInt(6, newReimbursement.getReimbursementStatus().ordinal() + 1);
            pstmt.setInt(6, newReimbursement.getReimbursementType().ordinal() + 1);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {

                ResultSet rs = pstmt.getGeneratedKeys(); // use second parameter of prepare statement

                rs.next(); // for each reimbursement saved
                newReimbursement.setId(rs.getInt(1)); // get the ID and set it in the service layer

            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }

    private Set<ErsReimbursement> mapResultSet(ResultSet rs) throws SQLException {

        Set<ErsReimbursement> reimbs = new HashSet<>();

        while(rs.next()) {
            ErsReimbursement temp = new ErsReimbursement();
            temp.setId(rs.getInt("reimb_id"));
            temp.setAmount(rs.getDouble("amount"));
            temp.setSubmitted(rs.getTimestamp("submitted"));
            temp.setResolved(rs.getTimestamp("resolved"));
            temp.setDescription(rs.getString("description"));
            temp.setAuthorId(rs.getInt("author_id"));
            temp.setResolverId(rs.getInt("resolver_id"));
            temp.setReimbursementType(Type.getByName(rs.getString("reimb_type")));
            temp.setReimbursementStatus(Status.getByName(rs.getString("reimb_status")));
            reimbs.add(temp);
        }

        return reimbs;

    }
}
