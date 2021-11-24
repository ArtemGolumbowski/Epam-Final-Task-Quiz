/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import bean.UserTestBean;
import dbmanager.ConnectionPool;
import dbmanager.TestDao;
import dbmanager.UserTestBeanDao;
import entity.Answer;
import entity.Question;
import entity.Test;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import utils.TestingUtils;

/**
 *
 * @author agolu
 */
@WebServlet(name = "TestingServlet", urlPatterns = {"/testing"})
public class TestingServlet extends HttpServlet {

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
        RoutingUtils.forwardToPage("Answers.jsp", request, response);
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
        String[] userAnswers = request.getParameterValues("userAnswer");
        int currentQuestionNumber = (Integer) session.getAttribute("currentQuestionNumber");
        int rightAnswersCount = (Integer) session.getAttribute("rightAnswersCount");
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Question currentQuestion = (Question) session.getAttribute("currentQuestion");
        List<Answer> answers = currentQuestion.getAnswers();
        rightAnswersCount += TestingUtils.checkAnswer(userAnswers, answers);
        if (currentQuestionNumber + 1 == questions.size() || request.getParameter("finish") != null) {
            LocalDateTime finish = LocalDateTime.now();
            System.out.println(finish);
            LocalDateTime start = (LocalDateTime) session.getAttribute("start");
            System.out.println(start);
            
            long userTime = Duration.between(start, finish).toMinutes();
            System.out.println(start);
            System.out.println(finish);
            System.out.println(userTime);
            int score = rightAnswersCount * 100 / questions.size();
            long testId = (Long) session.getAttribute("testId");
            int userId = (Integer) session.getAttribute("userId");
            try (Connection con = ConnectionPool.getInstance().getConnection()) {
                UserTestBeanDao userTestBeanDao = new UserTestBeanDao(con);
                TestDao testDao = new TestDao(con);
                Test test = testDao.read(testId);
                long popularity = test.getPopularity() + 1;
                test.setPopularity(popularity);
                testDao.update(test);
                userTestBeanDao.create(userId, testId, score, userTime, finish);
                session.removeAttribute("answers");
                session.removeAttribute("rightAnswersCount");
                session.removeAttribute("currentQuestionNumber");
                session.removeAttribute("testId");
                session.removeAttribute("start");
                session.removeAttribute("questions");

            } catch (SQLException ex) {
                Logger.getLogger(TestingServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("results");

        } else {
            currentQuestion = questions.get(++currentQuestionNumber);
            answers = currentQuestion.getAnswers();
            session.setAttribute("answers", answers);
            session.setAttribute("rightAnswersCount", rightAnswersCount);
            session.setAttribute("currentQuestionNumber", currentQuestionNumber);
            session.setAttribute("currentQuestion", currentQuestion);
            response.sendRedirect("testing");
        }
    }
}
