package com.revature.ers.repos;

import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;
import com.revature.ers.util.ConnectionFactory;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * UserRepository connects to the Postgres DB
 */
public class UserRepository implements CrudRepository<ErsUser>{

    /**
     * Common query clauses goes into an easily referenced member for reusability.
     */
    private String baseQuery = "SELECT * FROM project1.ers_users eu " +
            "JOIN project1.ers_user_roles er " +
            "ON eu.user_role_id = er.role_id ";

    /**
     * Constructor
     */
    public UserRepository() {
//        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
    }

    /**
     * CREATE operation (Persistence Layer)
     * @param newUser
     * @return
     */
    @Override
    public Optional<ErsUser> save(ErsUser newUser) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "INSERT INTO project1.ers_users (username, password, first_name, last_name, email, user_role_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            /**
             * Prepared Statement uses user generated values, denoted with ?s
             */
            // second parameter here is used to indicate column names that will have generated values
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"ers_user_id"});
            pstmt.setString(1, newUser.getUsername());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getFirstName());
            pstmt.setString(4, newUser.getLastName());
            pstmt.setString(5, newUser.getEmail());
            pstmt.setInt(6, newUser.getRole().ordinal() + 1);

            int rowsInserted = pstmt.executeUpdate(); // returns an int that represents the #rows inserted

            if (rowsInserted != 0) {

                ResultSet rs = pstmt.getGeneratedKeys();

                rs.next();
                newUser.setId(rs.getInt(1));

            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;

    }

    // TODO Read operation for admins
    /**
     * READ operation (Persistence Layer)
     * @return
     */
    @Override
    public Set<ErsUser> findAll() {

        Set<ErsUser> users = new HashSet<>();

        /**
         * Try with resources; the resource is the JDB
         */
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            users = mapResultSet(rs);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return users;

    }

    /**
     * READ operation (Persistence Layer)
     * Not used yet...
     * @param id
     * @return
     */
    @Override
    public Optional<ErsUser> findById(Integer id) {

        System.out.println("In findById method in UserRepository");

        Optional<ErsUser> _user = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE ers_user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            _user = mapResultSet(rs).stream().findFirst();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return _user;
    }

    /**
     * READ operation (Persistence Layer)
     * @param username
     * @param password
     * @return
     */
    public Optional<ErsUser> findUserByCredentials(String username, String password) {

        Optional<ErsUser> _user = Optional.empty();

        /**
         * Try with resources; the resource is the JDB
         */
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE username = ? AND password = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery(); // assign the SQL query to a ResultSet object

            /**
             * Map the result set of the query to the _user Optional
             */
            _user = mapResultSet(rs).stream().findFirst();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return _user;
    }

    /**
     * READ operation (Persistence Layer)
     * @param username
     * @return
     */
    public Optional<ErsUser> findUserByUsername(String username) {

        Optional<ErsUser> _user = Optional.empty();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            _user = mapResultSet(rs).stream().findFirst();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return _user;

    }

    /**
     * UPDATE operation (Persistence Layer)
     * @param ersUser
     * @return
     */
    @Override
    public boolean update(ErsUser ersUser) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "UPDATE project1.ers_users "
                    + "SET email = '" + ersUser.getEmail() + "', "
                    + "username = '" + ersUser.getUsername() + "', "
                    + "password = '" + ersUser.getPassword() + "', "
                    + "first_name = '" + ersUser.getFirstName() + "', "
                    + "last_name = '" + ersUser.getLastName() + "' "
//                    + "', " // TODO update role
//                    + "role_id = " + appUser.getRole() + " " // role is a number
                    + "WHERE id = " + ersUser.getId();

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate(); //
            pstmt.close();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return true;

    }

    /**
     * DELETE operation (Persistence Layer)
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(Integer id) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "DELETE from project1.ers_users WHERE ers_user_id = " + id;

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
     * Convenience method
     * To use in READ operations
     * @param rs
     * @return
     * @throws SQLException
     */
    private Set<ErsUser> mapResultSet(ResultSet rs) throws SQLException {

        Set<ErsUser> users = new HashSet<>();

        /**
         * Extract results, set the temporary AppUser fields, and add the temp AppUser to the Set.
         */
        while (rs.next()) {
            ErsUser temp = new ErsUser();
            temp.setId(rs.getInt("ers_user_id"));
            temp.setUsername(rs.getString("username"));
            temp.setPassword(rs.getString("password"));
            temp.setFirstName(rs.getString("first_name"));
            temp.setLastName(rs.getString("last_name"));
            temp.setEmail(rs.getString("email"));
            temp.setRole(Role.getByName(rs.getString("user_role_id"))); // TODO fix bug where Service layer shows employee always
            System.out.println(temp);
            users.add(temp);
        }

        return users;

    }
}
