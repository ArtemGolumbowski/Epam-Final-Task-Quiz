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
import java.util.stream.Collectors;
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
@WebServlet(name = "SubjectServlet", urlPatterns = {"/subject/*"})
public class SubjectServlet extends HttpServlet {

    private static final int SUBSTRING_INDEX = "/mavenproject1/subject/".length();

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
        HttpSession session = request.getSession();
        String subjectUrl = request.getRequestURI().substring(SUBSTRING_INDEX);
String sort = "id";

        List<Test> tests = (List<Test>) getServletContext().getAttribute("tests");
        if ("all".equals(subjectUrl)) {
            try(Connection con = ConnectionPool.getInstance().getConnection()){
                TestDao testDao = new TestDao(con);
                
            } catch (SQLException ex) {
                Logger.getLogger(SubjectServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            tests = (List<Test>) getServletContext().getAttribute("tests");
            session.setAttribute("tests", tests);
            RoutingUtils.forwardToPage("user.jsp", request, response);
        } else {
            tests = tests.stream().filter(test -> test.getSubject().getName()
                    .equals(subjectUrl)).collect(Collectors.toList());
            session.setAttribute("tests", tests);
            request.setAttribute("selectedSubjectName", subjectUrl);
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
}
