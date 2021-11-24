/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dbmanager.ConnectionPool;
import dbmanager.TestDao;
import entity.Test;
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
@WebServlet(name = "UserGetServlet", urlPatterns = {"/user"})
public class UserGetServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        int page = 1;
        int recordsPerPage = 3;
        int noOfPages;
        if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        List<Test> tests;
        String sort = "id";
        if (request.getParameter("sort") != null && !request.getParameter("sort").equals("")) {
            sort = request.getParameter("sort");
            request.setAttribute("sort", sort);
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            TestDao testDao = new TestDao(con);
            String currentSubject = request.getParameter("currentSubject");
            if (currentSubject != null && !"".equals(currentSubject)) {
                int noOfRecords = testDao.testsCountBySubject(currentSubject);
                tests = testDao.readBySubjectName(currentSubject, sort, (page - 1) * recordsPerPage, recordsPerPage);
                noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                request.setAttribute("currentSubject", currentSubject);
            } else {
                int noOfRecords = testDao.allTestsPageCount();
                tests = testDao.readAll(sort, (page - 1) * recordsPerPage, recordsPerPage);
                noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            }
            request.setAttribute("tests", tests);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPageCount", page);
        } catch (SQLException ex) {
            Logger.getLogger(AdminGetServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            RoutingUtils.forwardToPage("user.jsp", request, response);
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
