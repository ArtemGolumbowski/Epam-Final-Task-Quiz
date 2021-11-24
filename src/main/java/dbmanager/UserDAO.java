/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import entity.Role;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

/**
 *
 * @author agolu
 */
public class UserDAO {

    /**
     * Connection of database.
     */
    @NotNull
    private final Connection connection;

    /**
     * Init database connection.
     *
     * @param connection of database.
     */
    public UserDAO(final Connection connection) {
        this.connection = connection;
    }

    /**
     * Return true, if user with this email exist in db
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public boolean isExist(String email) throws SQLException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GETBYEMAIL.QUERY)) {
            statement.setString(1, email);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Create User in database.
     *
     * @param user for create.
     * @return false if User already exist. If creating success true.
     * @throws java.sql.SQLException
     */
//    @Override
    public boolean create(@NotNull final User user) throws SQLException {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.INSERT.QUERY)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().value);
            statement.setString(4, user.getName());
             statement.setString(5, user.getSurname());
              statement.setBoolean(6, user.getIsBanned());
            statement.executeUpdate();
        }
        return result;
    }

    /**
     * Select User by email.
     *
     * @param email
     * @param password
     * @return return valid entity if she exist. If entity does not exist return
     * empty User with id = -1.
     * @throws java.sql.SQLException
     */
//    @Override
    public User read(@NotNull final String email, @NotNull final String password) throws SQLException {
        final User result = new User();
        result.setId(-1);

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET.QUERY)) {
            statement.setString(1, email);
            statement.setString(2, password);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.setId(Integer.parseInt(rs.getString("id")));
                result.setLogin(email);
                result.setPassword(rs.getString("password"));
                result.setRole(Role.valueOf(rs.getString("value").toUpperCase()));
                result.setName(rs.getString("name"));
                result.setSurname(rs.getString("surname"));
                result.setIsBanned(Boolean.parseBoolean("is_banned"));
            }
        }
        return result;
    }
    public User readById(int userId) throws SQLException {
        final User result = new User();
        result.setId(-1);

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GETBYID.QUERY)) {
            statement.setInt(1, userId);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.setId(Integer.parseInt(rs.getString("id")));
                result.setLogin(rs.getString("email"));
                result.setPassword(rs.getString("password"));
                result.setName(rs.getString("name"));
                result.setSurname(rs.getString("surname"));
                result.setIsBanned(Boolean.parseBoolean("is_banned"));
            }
        }
        return result;
    }

    public List<User> readAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GETALL.QUERY)) {

            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User result = new User();
                result.setId(rs.getInt("id"));
                result.setLogin(rs.getString("email"));
                result.setPassword(rs.getString("password"));
                result.setRole(Role.valueOf(rs.getString("value").toUpperCase()));
                result.setName(rs.getString("name"));
                result.setSurname(rs.getString("surname"));
                result.setIsBanned(rs.getBoolean("is_banned"));
                users.add(result);
            }
        }
        return users;
    }

    /**
     * Update User by id.
     *
     * @param user new user's state.
     * @throws java.sql.SQLException
     */
    public void update(@NotNull final User user) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.UPDATE.QUERY)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().value);
            statement.setString(4, user.getName());
            statement.setString(5, user.getSurname());
            statement.setBoolean(6, user.getIsBanned());
            statement.setInt(7, user.getId());
            statement.executeUpdate();
        }
    }

    /**
     * Delete User by id.
     *
     * @param userId
     * @throws java.sql.SQLException
     */
    public void delete(@NotNull long userId) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.DELETE.QUERY)) {
            statement.setLong(1, userId);

            statement.executeUpdate();
        }
    }

    public Map<Integer, Boolean> getBannedList() throws SQLException {
        Map<Integer, Boolean> bannedList = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GETBANNED.QUERY)) {
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Integer userBannedId = rs.getInt("id");
                Boolean isBanned = rs.getBoolean("is_banned");
                bannedList.put(userBannedId, isBanned);
            }
        }
        return bannedList;
    }

    /**
     * SQL queries for users table.
     */
    enum SQLUser {
        GETBYEMAIL("SELECT * FROM user WHERE email = (?)"),
        GETBYID("SELECT * FROM user WHERE id = (?)"),
        GETBANNED("SELECT id, is_banned FROM user WHERE is_banned = true"),
        GET("SELECT u.id, u.email, u.password, u.name, u.surname, u.is_banned, r.id AS rol_id, r.value FROM user AS u JOIN role AS r ON u.role_id = r.id WHERE u.email = (?) AND u.password = (?)"),
        GETALL("SELECT u.id, u.email, u.password, u.name, u.surname, u.is_banned, r.id AS rol_id, r.value FROM user AS u JOIN role AS r ON u.role_id = r.id ORDER BY email"),
        INSERT("INSERT INTO user (id, email, password, role_id, name, surname, is_banned) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)"),
        DELETE("DELETE FROM user WHERE id = (?)"),
        UPDATE("UPDATE user SET email = (?), password = (?), role_id = (?), name = (?), surname = (?), is_banned = (?) WHERE id = (?)");

        String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }

}
