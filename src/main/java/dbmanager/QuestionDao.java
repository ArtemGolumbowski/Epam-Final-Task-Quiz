package dbmanager;

import entity.Answer;
import entity.Question;
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
public class QuestionDao {

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
    public QuestionDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Create a Question in database
     *
     * @param question
     * @param testId
     * @throws SQLException
     */
    public void create(@NotNull final Question question, long testId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQLQuestion.INSERT.QUERY, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement st = connection.prepareStatement(SQLQuestion.INSERTQT.QUERY)) {

            statement.setString(1, question.getDescription());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long questionId = generatedKeys.getLong(1);
                    question.setId(questionId);
                }
            }
            st.setLong(1, testId);
            st.setLong(2, question.getId());
            st.executeUpdate();
            connection.commit();

        }
    }

    /**
     * Select all Questions with Answers from database
     *
     * @param testId
     * @return List questions
     */
    public List<Question> read(@NotNull final long testId) {
        List<Question> questions = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(QuestionDao.SQLQuestion.GETALL.QUERY)) {
            statement.setLong(1, testId);
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                final Question result = new Question();
                long questionId = Long.parseLong(rs.getString("id"));
                result.setId(questionId);
                result.setDescription(rs.getString("description"));
                AnswerDao answerDao = new AnswerDao(connection);
                List<Answer> answers = answerDao.read(questionId);
                result.setAnswers(answers);
                questions.add(result);
            }
            return questions;
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return questions;
    }

    /**
     * Update question in database
     *
     * @param question
     * @throws java.sql.SQLException
     */
    public void update(final Question question) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QuestionDao.SQLQuestion.UPDATE.QUERY)) {
            statement.setString(1, question.getDescription());
            statement.setLong(2, question.getId());
            statement.executeUpdate();
        } 
    }

    public void delete(long questionId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QuestionDao.SQLQuestion.DELETE.QUERY)) {
            statement.setLong(1, questionId);
            statement.executeUpdate();
        }
    }

    enum SQLQuestion {
        GETALL("SELECT q.id, q.description FROM quiz_schema.questions q JOIN quiz_schema.test_has_questions thq ON q.id = thq.questions_id WHERE thq.test_id = (?)"),
        INSERT("INSERT INTO quiz_schema.questions (id, description) VALUES (DEFAULT, ?)"),
        INSERTQT("INSERT INTO quiz_schema.test_has_questions (test_id, questions_id) VALUES (?, ?)"),
        DELETE("DELETE FROM quiz_schema.questions WHERE id = (?)"),
        UPDATE("UPDATE quiz_schema.questions SET description = (?) WHERE id = (?)");

        String QUERY;

        SQLQuestion(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
