/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dbmanager.ConnectionPool;
import dbmanager.QuestionDao;
import dbmanager.UserTestBeanDao;
import entity.Answer;
import entity.Question;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.RoutingUtils;

/**
 *
 * @author agolu
 */
@WebServlet(name = "StartTestServlet", urlPatterns = {"/start"})
public class StartTestServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        long testId = Long.parseLong(request.getParameter("testId"));
        int userId = (Integer) session.getAttribute("userId");
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            UserTestBeanDao utb = new UserTestBeanDao(con);
            boolean isPassed = utb.checkPassed(testId, userId);
            System.out.println(isPassed);
            if (isPassed) {
                request.setAttribute("errorMessage", "You can pass this quiz once");
                RoutingUtils.forwardToPage("error.jsp", request, response);
            } else {
                LocalDateTime start = LocalDateTime.now();
                QuestionDao questionDao = new QuestionDao(con);
                List<Question> questions = questionDao.read(testId);
                Integer currentQuestionNumber = 0;
                Integer rightAnswersCount = 0;
                Question currentQuestion = questions.get(currentQuestionNumber);
                List<Answer> answers = currentQuestion.getAnswers();
                session.setAttribute("questions", questions);
                session.setAttribute("rightAnswersCount", rightAnswersCount);
                session.setAttribute("currentQuestionNumber", currentQuestionNumber);
                session.setAttribute("currentQuestion", currentQuestion);
                session.setAttribute("answers", answers);
                session.setAttribute("testId", testId);
                session.setAttribute("start", start);
                System.out.println(start);
                response.sendRedirect("/mavenproject1/testing");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StartTestServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
