/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dbmanager.ConnectionPool;
import dbmanager.QuestionDao;
import entity.Question;
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
import utils.RoutingUtils;

/**
 *
 * @author agolu
 */
@WebServlet(name = "AdminQuestionServlet", urlPatterns = {"/admin/questions/*"})
public class AdminQuestionServlet extends HttpServlet {

    private static final int SUBSTRING_INDEX = "/mavenproject1/admin/questions/".length();

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
        long testId = Long.parseLong(request.getRequestURI().substring(SUBSTRING_INDEX));
        List<Question> questions;
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            QuestionDao questionDao = new QuestionDao(con);
            questions = questionDao.read(testId);
            request.getSession().setAttribute("questions", questions);
            request.getSession().setAttribute("currentTestId", testId);

        } catch (SQLException ex) {
            Logger.getLogger(AdminQuestionServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            RoutingUtils.forwardToAdminPage("adminquestions.jsp", request, response);
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
        Connection con = null;
        long testId = (Long) request.getSession().getAttribute("currentTestId");
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            Question question = new Question();
            question.setDescription(request.getParameter("description"));
            QuestionDao questionDao = new QuestionDao(con);

            questionDao.create(question, testId);

        } catch (SQLException ex) {
            Logger.getLogger(AdminQuestionServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminQuestionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("/mavenproject1/admin/questions/" + testId);

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
