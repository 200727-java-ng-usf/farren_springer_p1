package com.revature.ers.repos;

import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;
import com.revature.ers.util.ConnectionFactory;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/*
    Recommended methods to implement:
        - Set<ErsUser> findAllUsers()
        - Optional<ErsUser> findUserById(int id)
        - Set<ErsUser> findUsersByRole(String rolename)
        - boolean/void updateUser(ErsUser updatedUser)
        - boolean/void deleteUserById(int id)
        - Optional<ErsUser> findUserByEmail(String email)
 */
public class UserRepository {

    // extract common query clauses into a easily referenced member for reusability.
    private String baseQuery = "SELECT * FROM project1.ers_users eu " +
                               "JOIN project1.ers_user_roles ur " +
                               "ON eu.role_id = ur.id ";

    public UserRepository() {
        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
    }

    public Optional<ErsUser> findUserById(int id) {

        Optional<ErsUser> _user = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = baseQuery + "WHERE eu.id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            Set<ErsUser> result = mapResultSet(pstmt.executeQuery());

            if(!result.isEmpty()) {
                _user = result.stream().findFirst();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return _user;

    }


    public Set<ErsUser> findAllUsers() {

        Set<ErsUser> users = new HashSet<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "SELECT * FROM project1.ers_users eu " +
                         "JOIN project1.ers_user_roles ur " +
                         "ON eu.role_id = ur.id";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            users = mapResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;

    }

    public Optional<ErsUser> findUserByCredentials(String username, String password) {

        Optional<ErsUser> _user = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE username = ? AND password = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            _user = mapResultSet(rs).stream().findFirst();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return _user;
    }

    public Optional<ErsUser> findUserByUsername(String username) {

        Optional<ErsUser> _user = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            // you can control whether or not JDBC automatically commits DML statements
//            conn.setAutoCommit(false);

            String sql = baseQuery + "WHERE username = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            _user = mapResultSet(rs).stream().findFirst();

            // if you want to manually control the transaction
//            conn.commit();
//            conn.rollback();
//            conn.setSavepoint();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return _user;

    }

    public void save(ErsUser newUser) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "INSERT INTO project1.ers_users (username, password, first_name, last_name, email, role_id) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";

            // second parameter here is used to indicate column names that will have generated values
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"id"});
            pstmt.setString(1, newUser.getUsername());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getFirstName());
            pstmt.setString(4, newUser.getLastName());
            pstmt.setString(5, newUser.getEmail());
            pstmt.setInt(6, newUser.getRole().ordinal() + 1);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {

                ResultSet rs = pstmt.getGeneratedKeys();

                rs.next();
                newUser.setId(rs.getInt(1));

            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }

    private Set<ErsUser> mapResultSet(ResultSet rs) throws SQLException {

        Set<ErsUser> users = new HashSet<>();

        while (rs.next()) {
            ErsUser temp = new ErsUser();
            temp.setId(rs.getInt("id"));
            temp.setFirstName(rs.getString("first_name"));
            temp.setLastName(rs.getString("last_name"));
            temp.setUsername(rs.getString("username"));
            temp.setPassword(rs.getString("password"));
            temp.setRole(Role.getByName(rs.getString("name")));
            temp.setEmail(rs.getString("email"));
//            System.out.println(temp);
            users.add(temp);
        }

        return users;

    }

}
