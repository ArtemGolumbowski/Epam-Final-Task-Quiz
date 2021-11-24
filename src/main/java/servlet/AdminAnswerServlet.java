/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dbmanager.AnswerDao;
import dbmanager.ConnectionPool;
import entity.Answer;
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
@WebServlet(name = "AdminAnswerServlet", urlPatterns = {"/admin/answers/*"})
public class AdminAnswerServlet extends HttpServlet {

    private static final int SUBSTRING_INDEX = "/mavenproject1/admin/answers/".length();

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
        long currentQuestionId = Long.parseLong(request.getRequestURI().substring(SUBSTRING_INDEX));
        List<Answer> answers;
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            AnswerDao answerDao = new AnswerDao(con);
            answers = answerDao.read(currentQuestionId);
            request.getSession().setAttribute("answers", answers);
            request.getSession().setAttribute("currentQuestionId", currentQuestionId);
            RoutingUtils.forwardToAdminPage("adminanswers.jsp", request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AdminQuestionServlet.class.getName()).log(Level.SEVERE, null, ex);
           
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
        Answer answer = new Answer();
        answer.setDescription(request.getParameter("description"));
        answer.setValue(Boolean.valueOf(request.getParameter("value")));
        long currentQuestionId = (Long) request.getSession().getAttribute("currentQuestionId");
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            AnswerDao answerDao = new AnswerDao(con);
            answerDao.create(answer, currentQuestionId);
        } catch (SQLException ex) {
            Logger.getLogger(AdminAnswerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            response.sendRedirect("/mavenproject1/admin/answers/" + currentQuestionId);
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
