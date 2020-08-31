package com.revature.ers.repos;

import com.revature.ers.models.ErsUser;
import com.revature.ers.models.Role;
import com.revature.ers.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserRepository implements CrudRepository<ErsUser>{

    /**
     * Extract common query clauses into a easily referenced member for reusability.
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
     * CREATE operation
     * @param newUser
     * @return
     */
    @Override
    public Optional<ErsUser> save(ErsUser newUser) {
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

    @Override
    public Set<Optional<ErsUser>> findAll() {
        return null;
    }

    @Override
    public Optional<ErsUser> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean update(ErsUser ersUser) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    public Optional<ErsUser> findUserByCredentials(String username, String password) {

        Optional<ErsUser> _user = Optional.empty();

        /**
         * Try with resources; the resource is the JDB
         */
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = baseQuery + "WHERE username = ? AND password = ?";

            /**
             * Create a Statement
             *  PreparedStatement
             *      no hardcoded values, instead it is a parameterized query (uses question marks)
             *      the DB receives the parameterized query and compiles it and stores it in its memory
             *      now when we fill in the parameters of our PreparedStatement and send a new query, it
             *      is not compiled again,
             *      instead the DB just fills in the blanks (?) and executes the query works
             *      this prevents us from using up too much memory!
             *      the pre-compiled nature of PreparedStatements also makes the resilient against
             *      SQL Injection (SQLi) attacks
             *      our query is sent and compiled prior to any values being provided
             *      the values of the parameters are provided later
             *      therefore, there is no need for them to be properly escaped!
             */
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            /**
             * ExecuteQuery
             */
            ResultSet rs = pstmt.executeQuery();

            /**
             * Map the result set of the query to the _user Optional
             * to be returned to the findUserByCredentials method.
             */
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
            temp.setRole(Role.getByName(rs.getString("user_role_id")));
            System.out.println(temp);
            users.add(temp);
        }

        return users;

    }
}
