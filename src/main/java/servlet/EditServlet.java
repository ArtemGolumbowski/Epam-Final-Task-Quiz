/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dbmanager.AnswerDao;
import dbmanager.ConnectionPool;
import dbmanager.QuestionDao;
import dbmanager.TestDao;
import dbmanager.UserDAO;
import entity.Answer;
import entity.Question;
import entity.Role;
import entity.Test;
import entity.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.RoutingUtils;

/**
 *
 * @author agolu
 */
@WebServlet(name = "EditServlet", urlPatterns = {"/admin/edit/*"})
public class EditServlet extends HttpServlet {

    private static final int SUBSTRING_INDEX = "/mavenproject1/admin/edit/".length();

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
        String editUrl = request.getRequestURI().substring(SUBSTRING_INDEX);
        switch (editUrl) {
            case ("test"):
                String testId = request.getParameter("testId");
                int currentPageCount = Integer.parseInt(request.getParameter("page"));
                String currentSubject = request.getParameter("currentSubject");
                String sort = request.getParameter("sort");
//                request.setAttribute("sort", sort);
//                request.setAttribute("currentSubject", currentSubject);
//                request.setAttribute("testEditId", testId);
//                request.setAttribute("currentPageCount", currentPageCount);
                response.sendRedirect("/mavenproject1/admin/get/tests?page=" + currentPageCount
                        + "&testEditId=" + testId + "&sort=" + sort
                        + "&currentSubject=" + currentSubject);
//                RoutingUtils.forwardToAdminPage("admintests.jsp", request, response);
                break;
            case ("user"):
                String userId = request.getParameter("userEditId");

                try (Connection con = ConnectionPool.getInstance().getConnection()) {
                    UserDAO userDao = new UserDAO(con);
                    List<User> users = userDao.readAll();
                    request.setAttribute("users", users);
                    request.setAttribute("userEditId", userId);
                    RoutingUtils.forwardToAdminPage("adminusers.jsp", request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(AdminGetServlet.class.getName()).log(Level.SEVERE, null, ex);
                    RoutingUtils.forwardToAdminPage("adminusers.jsp", request, response);
                }
                break;
            case ("question"):
                String questionId = request.getParameter("questionId");
                request.setAttribute("questionEditId", questionId);
                RoutingUtils.forwardToAdminPage("adminquestions.jsp", request, response);
                break;
            case ("answer"):
                String answerId = request.getParameter("answerId");
                request.setAttribute("answerEditId", answerId);
                RoutingUtils.forwardToAdminPage("adminanswers.jsp", request, response);
                break;
            case ("subject"):
                String subjectId = request.getParameter("subjectId");
                request.setAttribute("subjectEditId", subjectId);
                RoutingUtils.forwardToAdminPage("adminsubjects.jsp", request, response);
                break;
            default:
                RoutingUtils.forwardToAdminPage("admintests.jsp", request, response);
        }

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
        String editUrl = request.getRequestURI().substring(SUBSTRING_INDEX);
        Long testForQuestionId = (Long) request.getSession().getAttribute("currentTestId");
        Long questionForAnswerId = (Long) request.getSession().getAttribute("currentQuestionId");
        switch (editUrl) {
            case ("test"):
                try (Connection con = ConnectionPool.getInstance().getConnection()) {
                long testId = Long.parseLong(request.getParameter("testId"));
                TestDao testDao = new TestDao(con);
                Test test = new Test();
                test.setId(testId);
                test.setDescription(request.getParameter("description"));
                test.setName(request.getParameter("name"));
                test.setLevel(request.getParameter("level"));
                testDao.update(test);
            } catch (SQLException ex) {
                Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                response.sendRedirect("/mavenproject1/admin/get/tests");
            }
            break;
            case ("user"):
                try (Connection con = ConnectionPool.getInstance().getConnection()) {
                int userId = Integer.parseInt(request.getParameter("userEditId"));
                UserDAO userDao = new UserDAO(con);
                User user = new User();
                user.setId(userId);
                user.setLogin(request.getParameter("login"));
                user.setPassword(request.getParameter("password"));
                user.setRole(Role.valueOf(request.getParameter("role").toUpperCase()));
                user.setName(request.getParameter("name"));
                user.setSurname(request.getParameter("surname"));
                user.setIsBanned(Boolean.parseBoolean(request.getParameter("isBanned")));
                    System.out.println(user.getIsBanned());
                userDao.update(user);
                Map<Integer, Boolean> bannedList = userDao.getBannedList();
                getServletContext().setAttribute("bannedList", bannedList);
            } catch (SQLException ex) {
                Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                response.sendRedirect("/mavenproject1/admin/get/users");
            }
            break;

            case ("question"):
                try (Connection con = ConnectionPool.getInstance().getConnection()) {
                Long questionId = Long.parseLong(request.getParameter("questionId"));
                QuestionDao questionDao = new QuestionDao(con);
                Question question = new Question();
                question.setId(questionId);
                question.setDescription(request.getParameter("description"));
                questionDao.update(question);

            } catch (SQLException ex) {
                Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                response.sendRedirect("/mavenproject1/admin/questions/" + testForQuestionId);
            }

            break;

            case ("answer"):
                try (Connection con = ConnectionPool.getInstance().getConnection()) {
                Long answerId = Long.parseLong(request.getParameter("answerId"));
                AnswerDao answerDao = new AnswerDao(con);
                Answer answer = new Answer();
                answer.setId(answerId);
                answer.setDescription(request.getParameter("description"));
                answer.setValue(Boolean.parseBoolean(request.getParameter("value")));
                answerDao.update(answer);
            } catch (SQLException ex) {
                Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                response.sendRedirect("/mavenproject1/admin/answers/" + questionForAnswerId);
            }
            break;
            default:
                response.sendRedirect("/mavenproject1/admin/get/tests");
        }
    }
}
