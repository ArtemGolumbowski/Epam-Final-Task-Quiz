package dbmanager;

import entity.Answer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.NotNull;

/**
 *
 * @author agolu
 */
public class AnswerDao {

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
    public AnswerDao(final Connection connection) {
        this.connection = connection;
    }

    /**
     * Create Answer in database.
     *
     * @param answer
     * @param questionId
     * @throws java.sql.SQLException
     */
    public void create(@NotNull final Answer answer, long questionId) throws SQLException {
        PreparedStatement st = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLAnswer.INSERT.QUERY, Statement.RETURN_GENERATED_KEYS);
            st = connection.prepareStatement(SQLAnswer.INSERTINAQ.QUERY);
            connection.setAutoCommit(false);
            statement.setString(1, answer.getDescription());
            statement.setBoolean(2, answer.getValue());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long answerId = generatedKeys.getLong(1);
                    answer.setId(answerId);
                }
            }
            st.setLong(1, answer.getId());
            st.setLong(2, questionId);
            st.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
            connection.rollback();

        } finally {
            connection.setAutoCommit(true);
            if (statement != null) {
                statement.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    /**
     * Select all Answer for current Question.
     *
     * @param questionId
     * @return List answers
     * @throws java.sql.SQLException
     */
    public List<Answer> read(@NotNull final Long questionId) throws SQLException {
        List<Answer> answers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLAnswer.GET.QUERY)) {
            statement.setLong(1, questionId);
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                final Answer result = new Answer();
                result.setId(Long.parseLong(rs.getString("id")));
                result.setDescription(rs.getString("description"));
                result.setValue(rs.getBoolean("value"));
                answers.add(result);
            }
            return answers;
        } 
        
    }

    /**
     * Update Answer by id.
     *
     * @param answer
     * @throws java.sql.SQLException
     */
    public void update(@NotNull final Answer answer) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(SQLAnswer.UPDATE.QUERY)) {
            statement.setString(1, answer.getDescription());
            statement.setBoolean(2, answer.getValue());

            statement.setLong(3, answer.getId());
            statement.executeUpdate();
        } 

    }

    /**
     * Delete Answer by id.
     *
     * @param answerId
     */
    public void delete(@NotNull final Long answerId) {
        try (PreparedStatement statement = connection.prepareStatement(SQLAnswer.DELETE.QUERY)) {
            statement.setLong(1, answerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(AnswerDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * SQL queries for answers table.
     */
    enum SQLAnswer {
        GET("SELECT a.id, a.description, a.value "
                + "FROM answers AS a JOIN answers_has_questions AS aq "
                + "ON a.id = aq.answers_id WHERE aq.questions_id = (?)"),
        INSERT("INSERT INTO answers (id, description, value) VALUES (DEFAULT, ?, ?)"),
        INSERTINAQ("INSERT INTO answers_has_questions (answers_id, questions_id) VALUES (?, ?)"),
        DELETE("DELETE FROM answers WHERE id = (?)"),
        UPDATE("UPDATE answers SET description = (?), value = (?) WHERE id = (?)");

        String QUERY;

        SQLAnswer(String QUERY) {
            this.QUERY = QUERY;
        }
       
    }
  
}
