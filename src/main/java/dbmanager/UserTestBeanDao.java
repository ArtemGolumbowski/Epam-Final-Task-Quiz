package dbmanager;

import bean.UserTestBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 *
 * @author agolu
 */
public class UserTestBeanDao {

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
    public UserTestBeanDao(final Connection connection) {
        this.connection = connection;
    }

    /**
     *
     * @param userId
     * @return
     * @throws java.sql.SQLException
     */
    public List<UserTestBean> read(@NotNull final int userId) throws SQLException {
        List<UserTestBean> userTests = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQLUserTestBean.GETALL.QUERY)) {
            statement.setInt(1, userId);
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                final UserTestBean result = new UserTestBean();
                result.setUserId(userId);
                result.setTestName(rs.getString("name"));
                result.setSubjectName(rs.getString("subject_name"));
                result.setScore(rs.getInt("result"));
                result.setLevel(rs.getString("level"));
                result.setUserPassDate(rs.getTimestamp("user_quiz_date_time").toLocalDateTime());
                result.setUserQuizTime(rs.getLong("user_duration"));
                result.setTestDuration(rs.getLong("duration"));
                userTests.add(result);
            }
            return userTests;
        }

    }

    /**
     *
     * @param userId
     * @param testId
     * @param score
     * @param time
     * @param ldt
     * @throws java.sql.SQLException
     */
    public void create(int userId, long testId, int score, long time, LocalDateTime ldt) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(SQLUserTestBean.INSERT.QUERY)) {
            statement.setInt(1, userId);
            statement.setLong(2, testId);
            statement.setInt(3, score);
            statement.setTimestamp(5, Timestamp.valueOf(ldt));
            statement.setLong(4, time);
            statement.executeUpdate();
        }
    }

    /**
     *
     * @param testId
     * @param userId
     * @return
     * @throws SQLException
     */
    public boolean checkPassed(long testId, int userId) throws SQLException {
        boolean isPassed = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLUserTestBean.GETCHECK.QUERY)) {
            statement.setLong(1, testId);
            statement.setInt(2, userId);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                isPassed = true;
            }
        }
        return isPassed;
    }

    enum SQLUserTestBean {
        GETALL("SELECT t.name, t.level, t.duration, s.name AS subject_name, uht.result, uht.user_quiz_date_time, uht.user_duration FROM test t"
                + " JOIN subject_has_test sht ON t.id = sht.test_id"
                + " JOIN subjects s ON s.id = sht.subjects_id"
                + " JOIN user_has_test uht ON t.id = uht.test_id WHERE uht.user_id = (?)"),
        GETCHECK("SELECT * FROM user_has_test WHERE test_id = (?) AND user_id = (?)"),
        INSERT("INSERT INTO quiz_schema.user_has_test  VALUES (?, ?, ?, ?, ?)");

        String QUERY;

        SQLUserTestBean(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
