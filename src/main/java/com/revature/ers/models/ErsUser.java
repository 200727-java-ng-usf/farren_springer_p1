package com.revature.ers.models;

import java.util.Objects;
import java.util.Set;

/**
 * User POJOs
 */
public class ErsUser {

    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

    public ErsUser() {
        super();
    }

    /**
     * Constructor without role and id
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     */
    public ErsUser (String username, String password, String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role.EMPLOYEE;  // role not specified? Employee
    }

    /**
     * Constructor with role parameter
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * @param role
     */
    public ErsUser (String username, String password, String firstName, String lastName, String email, Role role) {
        this(username, password, firstName, lastName, email); // constructor chaining
        this.role = role; // role can be specified with this constructor
    }

    /**
     * Full constructor
     * @param id
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * @param role
     */
    public ErsUser (Integer id, String username, String password, String firstName, String lastName, String email, Role role) {
        this(username, password, firstName, lastName, email, role); // constructor chaining
        this.id = id;
    }

    /**
     * Copy constructor (used for conveniently copying the values of one ErsUser to create a new instance with those values)
     * @param copy
     */
    public ErsUser(ErsUser copy) {
        this(copy.id, copy.username, copy.password, copy.firstName, copy.lastName, copy.email, copy.role);
    }


    /**
     * Getters and Setters
     */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    /**
     * Overridden Object Methods
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErsUser ersUser = (ErsUser) o;
        return Objects.equals(id, ersUser.id) &&
                Objects.equals(username, ersUser.username) &&
                Objects.equals(password, ersUser.password) &&
                Objects.equals(firstName, ersUser.firstName) &&
                Objects.equals(lastName, ersUser.lastName) &&
                Objects.equals(email, ersUser.email) &&
                role == ersUser.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstName, lastName, email, role);
    }

    @Override
    public String toString() {
        return "ErsUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
