package servlet;

import dbmanager.AnswerDao;
import dbmanager.ConnectionPool;
import dbmanager.QuestionDao;
import dbmanager.SubjectDao;
import dbmanager.TestDao;
import dbmanager.UserDAO;
import entity.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author agolu
 */
@WebServlet(name = "DeleteServlet", urlPatterns = {"/admin/delete/*"})
public class DeleteServlet extends HttpServlet {

    long testForQuestionsId;
    long questionForAnswersId;
    private static final int SUBSTRING_INDEX = "/mavenproject1/admin/delete/".length();

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String deleteUrl = request.getRequestURI().substring(SUBSTRING_INDEX);
        switch (deleteUrl) {
            case ("test"):
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
                long testId = Long.parseLong(request.getParameter("testId"));
                TestDao testDao = new TestDao(con);
                testDao.delete(testId);

            } catch (SQLException ex) {
                Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                response.sendRedirect("/mavenproject1/admin/get/tests");
            }
            break;
            case ("user"):
            try (Connection con = ConnectionPool.getInstance().getConnection()) {
                long userId = Long.parseLong(request.getParameter("userDeleteId"));
                UserDAO userDao = new UserDAO(con);
                userDao.delete(userId);
                List<User> users = userDao.readAll();
                request.getSession().setAttribute("users", users);
            } catch (SQLException ex) {
                Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                response.sendRedirect("/mavenproject1/admin/get/users");
            }
            break;
            case ("subject"):
            try (Connection con = ConnectionPool.getInstance().getConnection()) {
                long subjectId = Long.parseLong(request.getParameter("subjectId"));
                SubjectDao subjectDao = new SubjectDao(con);
                subjectDao.delete(subjectId);
            } catch (SQLException ex) {
                Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                response.sendRedirect("/mavenproject1/admin/get/subjects");
            }
            break;
            case ("question"):
            try (Connection con = ConnectionPool.getInstance().getConnection()) {
                testForQuestionsId = (Long) request.getSession().getAttribute("currentTestId");
                long questionId = Long.parseLong(request.getParameter("questionId"));
                QuestionDao questionDao = new QuestionDao(con);
                questionDao.delete(questionId);
            } catch (SQLException ex) {
                Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                response.sendRedirect("/mavenproject1/admin/questions/" + testForQuestionsId);
            }
            break;
            case ("answer"):
            try (Connection con = ConnectionPool.getInstance().getConnection()) {
                long answerId = Long.parseLong(request.getParameter("answerId"));
                questionForAnswersId = (Long) request.getSession().getAttribute("currentQuestionId");
                AnswerDao answerDao = new AnswerDao(con);
                answerDao.delete(answerId);
            } catch (SQLException ex) {
                Logger.getLogger(DeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                response.sendRedirect("/mavenproject1/admin/answers/" + questionForAnswersId);
            }
            break;
        }
    }
}
