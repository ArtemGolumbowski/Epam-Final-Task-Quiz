package servlet;

import dbmanager.ConnectionPool;
import dbmanager.SubjectDao;
import dbmanager.TestDao;
import entity.Subject;
import entity.Test;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
@WebServlet(name = "CreateTest", urlPatterns = {"/createtest"})
public class CreateTest extends HttpServlet {

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
        Test test = new Test();
        Subject subject;
        Connection con = null;
        test.setName(request.getParameter("name"));
        test.setDescription(request.getParameter("description"));
        test.setLevel(request.getParameter("level"));
        test.setDuration(Long.parseLong(request.getParameter("duration")));
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            TestDao testDao = new TestDao(con);
            SubjectDao subjectDao = new SubjectDao(con);

            subject = subjectDao.read(Long.parseLong(request.getParameter("subject")));
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
                    Logger.getLogger(CreateTest.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            response.sendRedirect("/mavenproject1/admin/get/tests");
        }
    }

}
