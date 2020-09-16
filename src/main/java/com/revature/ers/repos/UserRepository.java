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
                               "ON eu.user_role_id = ur.role_id ";


    public UserRepository() {
        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
    }

    /**
     * CREATE operation
     * @param newUser
     */
    public void save(ErsUser newUser) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "INSERT INTO project1.ers_users (username, password, first_name, last_name, email, user_role_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            // second parameter here is used to indicate column names that will have generated values
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"ers_user_id"});
            pstmt.setString(1, newUser.getUsername());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getFirstName());
            pstmt.setString(4, newUser.getLastName());
            pstmt.setString(5, newUser.getEmail());
            pstmt.setInt(6, newUser.getRole().ordinal());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {

                ResultSet rs = pstmt.getGeneratedKeys();

                rs.next();
                System.out.println(rs.getInt(1));
                newUser.setId(rs.getInt(1));

            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }

    /**
     * READ operation
     * @param id
     * @return
     */
    public Optional<ErsUser> findUserById(int id) {

        Optional<ErsUser> _user = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = baseQuery + "WHERE eu.ers_user_id = ?";
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

    /**
     * READ operation
     * @return
     */
    public Set<ErsUser> findAllUsers() {

        Set<ErsUser> users = new HashSet<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            users = mapResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;

    }

    /**
     * READ operation
     * @param username
     * @param password
     * @return
     */
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

    /**
     * READ operation
     * @param username
     * @return
     */
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

    /**
     * READ operation
     * @param email
     * @return
     */
    public Optional<ErsUser> findUserByEmail(String email) {

        Optional<ErsUser> _user = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE email = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            _user = mapResultSet(rs).stream().findFirst();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return _user;

    }

    /**
     * UPDATE operation
     * @param ersUser
     * @return
     */
    public boolean update(ErsUser ersUser) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "UPDATE project1.ers_users "
                    + "SET email = '" + ersUser.getEmail() + "', "
                    + "username = '" + ersUser.getUsername() + "', "
                    + "password = '" + ersUser.getPassword() + "', "
                    + "first_name = '" + ersUser.getFirstName() + "', "
                    + "last_name = '" + ersUser.getLastName() + "', "
                    + "user_role_id = '" + ersUser.getRole().ordinal() + "', "
                    + "WHERE ers_user_id = '" + ersUser.getId() + "' ";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate(); //
            pstmt.close();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return true;

    }

    /**
     * DELETE operation
     * @param newUser
     */
    public void makeInactive(ErsUser newUser) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "UPDATE project1.ers_users "
                    + "SET role_id = 4";

            Statement stmt = conn.createStatement();
            stmt.executeQuery(sql);

//            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }

    /**
     * Convenience method (to call in READ operations)
     * @param rs
     * @return
     * @throws SQLException
     */
    private Set<ErsUser> mapResultSet(ResultSet rs) throws SQLException {

        Set<ErsUser> users = new HashSet<>();

        while (rs.next()) {
            ErsUser temp = new ErsUser();
            temp.setId(rs.getInt("ers_user_id"));
            temp.setFirstName(rs.getString("first_name"));
            temp.setLastName(rs.getString("last_name"));
            temp.setUsername(rs.getString("username"));
            temp.setPassword(rs.getString("password"));
            temp.setRole(Role.getByName(rs.getString("role_name")));
            temp.setEmail(rs.getString("email"));
//            System.out.println(temp);
            users.add(temp);
        }

        return users;

    }

}
