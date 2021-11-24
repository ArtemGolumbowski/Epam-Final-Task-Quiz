/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dbmanager.ConnectionPool;
import dbmanager.SubjectDao;
import dbmanager.TestDao;
import dbmanager.UserDAO;
import entity.Subject;
import entity.Test;
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
import utils.RoutingUtils;

/**
 *
 * @author agolu
 */
@WebServlet(name = "AdminGetServlet", urlPatterns = {"/admin/get/*"})
public class AdminGetServlet extends HttpServlet {

    private static final int SUBSTRING_INDEX = "/mavenproject1/admin/get/".length();

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
        String getUrl = request.getRequestURI().substring(SUBSTRING_INDEX);
        int page = 1;
        int recordsPerPage = 3;
        int noOfPages;
        if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        System.out.println(getUrl);
        switch (getUrl) {
            case ("tests"):
                List<Test> tests;
                String sort = "id";
                if (request.getParameter("testEditId") != null && !request.getParameter("testEditId").equals("")) {
                    int testEditId = Integer.parseInt(request.getParameter("testEditId"));
                    request.setAttribute("testEditId", testEditId);
                }
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
                    RoutingUtils.forwardToAdminPage("admintests.jsp", request, response);
                }

                break;

            case ("subjects"):
                RoutingUtils.forwardToAdminPage("adminsubjects.jsp", request, response);
                break;
            case ("users"):
                try (Connection con = ConnectionPool.getInstance().getConnection()) {
                UserDAO userDao = new UserDAO(con);
                List<User> users = userDao.readAll();
                request.setAttribute("users", users);
                RoutingUtils.forwardToAdminPage("adminusers.jsp", request, response);
            } catch (SQLException ex) {
                Logger.getLogger(AdminGetServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

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
        Test test = new Test();
        Subject subject;
        Connection con = null;
        test.setName(request.getParameter("name"));
        test.setDescription(request.getParameter("description"));
        test.setLevel(request.getParameter("level"));
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            TestDao testDao = new TestDao(con);
            SubjectDao subjectDao = new SubjectDao(con);
            subject = subjectDao.read(Long.parseLong(request.getParameter("subjectId")));
            test.setSubject(subject);
            testDao.create(test);
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(CreateTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(AdminGetServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            RoutingUtils.forwardToAdminPage("admintests.jsp", request, response);
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
