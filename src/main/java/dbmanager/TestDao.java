package dbmanager;

import entity.Question;
import entity.Subject;
import entity.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 *
 * @author agolu
 */
public class TestDao {

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
    public TestDao(final Connection connection) {
        this.connection = connection;
    }

    /**
     * Create Test in database.
     *
     * @param test
     *
     * @throws java.sql.SQLException
     */
//    @Override
    public void create(@NotNull final Test test) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(SQLTest.INSERT.QUERY,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, test.getName());
            statement.setString(2, test.getDescription());
            statement.setString(3, test.getLevel());
            statement.setLong(4, test.getPopularity());
            statement.setLong(5, test.getDuration());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long testId = generatedKeys.getLong(1);
                    test.setId(testId);
                }
            }

        }
        try (PreparedStatement statement = connection.prepareStatement(SQLTest.INSERTSUBJECT.QUERY)) {
            statement.setLong(2, test.getId());
            statement.setLong(1, test.getSubject().getId());
            statement.execute();
        }
    }

    /**
     * Select Test by id.
     *
     * @param id
     * @return return valid entity if she exist. If entity does not exist return
     * empty Test with id = -1.
     * @throws java.sql.SQLException
     */
    public Test read(long id) throws SQLException {
        final Test result = new Test();
        result.setId(-1);
        Subject subject = new Subject();
        try (PreparedStatement statement = connection.prepareStatement(SQLTest.GET.QUERY)) {
            statement.setLong(1, id);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.setId(id);
                result.setDescription(rs.getString("description"));
                result.setName(rs.getString("name"));
                result.setLevel(rs.getString("level"));
                subject.setId(rs.getLong("subject_id"));
                subject.setName("subject_name");
                subject.setDescription("subject_description");
                result.setSubject(subject);
                QuestionDao questionDao = new QuestionDao(connection);
                List<Question> questions = questionDao.read(id);
                result.setQuestions(questions);
            }
        } 
        return result;
    }

    /**
     * Select all Test.
     *
     *
     * @param sort
     * @param page
     * @param countPerPage
     * @return return valid entity if she exist. If entity does not exist return
     * empty List.
     * @throws java.sql.SQLException
     */
    public List<Test> readAll(String sort, int page, int countPerPage) throws SQLException {
        List<Test> tests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLTest.GETALL.QUERY
                + " ORDER BY " + sort + " LIMIT " + page + ", " + countPerPage)) {

            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Test result = new Test();
                Subject subject = new Subject();
                result.setId(rs.getLong("id"));
                result.setDescription(rs.getString("description"));
                result.setName(rs.getString("name"));
                result.setLevel(rs.getString("level"));
                result.setPopularity(rs.getLong("popularity"));
                subject.setId(rs.getLong("subject_id"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                result.setSubject(subject);
                tests.add(result);

            }
        }
        return tests;
    }

    /**
     * Select all Tests by Subject
     *
     * @param subjectName
     * @param orderBy
     * @param page
     * @param countPerPage
     * @return valid entity if she exist. If entity does not exist return empty
     * List
     * @throws java.sql.SQLException
     */
    public List<Test> readBySubjectName(String subjectName, String orderBy, int page, int countPerPage) throws SQLException {
        List<Test> tests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLTest.GETBYSUBJECT.QUERY
                + " ORDER BY " + orderBy + " LIMIT ?, ?")) {
            statement.setString(1, subjectName);
            statement.setInt(2, page);
            statement.setInt(3, countPerPage);
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Test result = new Test();
                result.setId(rs.getLong("id"));
                result.setDescription(rs.getString("description"));
                result.setName(rs.getString("name"));
                result.setLevel(rs.getString("level"));
                result.setPopularity(rs.getLong("popularity"));
                tests.add(result);
            }
        } 
        return tests;
    }

    public int allTestsPageCount() throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLTest.ALL_COUNT.QUERY)) {
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result = rs.getInt("count");
            }
        }
        return result;
    }

    public int testsCountBySubject(String currentSubject) throws SQLException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLTest.COUNT_BY_SUBJECT.QUERY)) {
            statement.setString(1, currentSubject);
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result = rs.getInt("count");
            }
        }
        return result;
    }

    /**
     * Update Test by id.
     *
     * @param test
     * @throws java.sql.SQLException
     *
     */
    public void update(@NotNull final Test test) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(SQLTest.UPDATE.QUERY)) {
            statement.setString(1, test.getName());
            statement.setString(2, test.getDescription());
            statement.setString(3, test.getLevel());
            statement.setLong(5, test.getId());
            statement.setLong(4, test.getPopularity());
            statement.executeUpdate();
        }
    }

    /**
     * Delete Test.
     *
     * @param testId
     */
//    @Override
    public void delete(@NotNull final Long testId) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(SQLTest.DELETE.QUERY)) {
            statement.setLong(1, testId);
            statement.executeUpdate();
        } 

    }

    /**
     * SQL queries for test table.
     */
    enum SQLTest {
        COUNT_BY_SUBJECT("SELECT COUNT(*) AS count FROM quiz_schema.test t"
                + " JOIN quiz_schema.subject_has_test ON t.id = test_id"
                + " JOIN quiz_schema.subjects s ON s.id = subjects_id WHERE s.name = (?)"),
        ALL_COUNT("SELECT COUNT(*) AS count FROM quiz_schema.test"),
        GETALL("SELECT t.id, t.name, t.description, t.level, t.popularity, s.id AS subject_id,"
                + " s.name AS subject_name, s.description AS subject_description"
                + " FROM quiz_schema.test t JOIN quiz_schema.subject_has_test ON t.id = test_id"
                + " JOIN quiz_schema.subjects s ON s.id = subjects_id"),
        GET("SELECT t.name, t.description, t.level, t.popularity, s.id AS subject_id, s.name AS subject_name,"
                + " s.description AS subject_description FROM quiz_schema.test t"
                + " JOIN quiz_schema.subject_has_test ON t.id = test_id"
                + " JOIN quiz_schema.subjects s ON s.id = subjects_id WHERE t.id =(?)"),
        GETBYSUBJECT("SELECT t.id, t.name, t.description, t.level, t.popularity FROM quiz_schema.test t"
                + " JOIN quiz_schema.subject_has_test ON t.id = test_id"
                + " JOIN quiz_schema.subjects s ON s.id = subjects_id WHERE s.name = (?)"
        ),
        INSERT("INSERT INTO test (id, name, description, level, popularity, duration) VALUES (DEFAULT, ?, ?, ?, ?, ?)"),
        INSERTSUBJECT("INSERT INTO subject_has_test (subjects_id, test_id) VALUES (?, ?)"),
        DELETE("DELETE FROM test WHERE id = (?)"),
        UPDATE("UPDATE test SET name = (?), description = (?), level = (?), popularity = (?) WHERE id = (?)");

        String QUERY;

        SQLTest(String QUERY) {
            this.QUERY = QUERY;
        }
    }

}
