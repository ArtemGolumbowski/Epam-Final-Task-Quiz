package dbmanager;

import entity.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.NotNull;

/**
 *
 * @author agolu
 */
public class SubjectDao {

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
    public SubjectDao(Connection connection) {
        this.connection = connection;
    }
    public boolean isExist(String name) throws SQLException{
    boolean result = false;
    try (PreparedStatement statement = connection.prepareStatement(SQLSubject.GETBYNAME.QUERY)) {
        statement.setString(1, name);       
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = true;
            }
    }
    return result;
}

    public Subject read(long id) throws SQLException {
        Subject result = new Subject();
        try (PreparedStatement statement = connection.prepareStatement(SQLSubject.GET.QUERY)) {
            statement.setLong(1, id);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.setId(id);
                result.setName(rs.getString("name"));
                result.setDescription(rs.getString("description"));
            }
        } 
        return result;
    }

    public List<Subject> readAll() throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLSubject.GETALL.QUERY)) {
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Subject result = new Subject();
                result.setId(rs.getLong("id"));
                result.setName(rs.getString("name"));
                result.setDescription(rs.getString("description"));
                subjects.add(result);
            }
        } 
        return subjects;
    }

    /**
     *
     * @param subject
     * @throws SQLException
     */
    public void update(Subject subject) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQLSubject.GETALL.QUERY)) {
            statement.setString(1, subject.getName());
            statement.setString(2, subject.getDescription());
            statement.setLong(3, subject.getId());
            statement.executeUpdate();
        } 
    }

    public void delete(long subjectId) {
        try (PreparedStatement statement = connection.prepareStatement(SQLSubject.GETALL.QUERY)) {
            statement.setLong(1, subjectId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    enum SQLSubject {
        GETBYNAME("SELECT * FROM quiz_schema.subjects WHERE name = (?)"),
        GETALL("SELECT * FROM quiz_schema.subjects"),
        GET("SELECT name, description FROM quiz_schema.subjects  WHERE id = (?)"),
        INSERT("INSERT INTO quiz_schema.subjects (id, name, description) VALUES (DEFAULT, ?, ?)"),
        DELETE("DELETE FROM quiz_schema.subjects WHERE id = (?)"),
        UPDATE("UPDATE quiz_schema.subjects SET name = (?), description = (?) WHERE id = (?)");

        String QUERY;

        SQLSubject(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
