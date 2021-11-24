package servlet;

import dbmanager.ConnectionPool;
import dbmanager.UserDAO;
import entity.Role;
import entity.User;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author agolu
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

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
        request.getRequestDispatcher("/index.jsp").forward(request, response);
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

        User user;
        String email = request.getParameter("login");
        String password = request.getParameter("password");
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            UserDAO userDao = new UserDAO(con);
            boolean exist = userDao.isExist(email);
            if (!exist) {
                request.setAttribute("errorMessage", "wrong email");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }else{
            user = userDao.read(email, password);
            if (user.getId() == -1) {
                request.setAttribute("errorMessage", "wrong password");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            Role role = user.getRole();
            HttpSession session = request.getSession();
            if(user.getIsBanned()){
                request.setAttribute("errorMessage", "your account is blocked by administrator!");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", role);
            session.setAttribute("email", email);
            

            if (role.equals(Role.ADMIN)) {
                response.sendRedirect("/mavenproject1/admin/get/tests");
            } else {
                if (role.equals(Role.USER)) {
                    response.sendRedirect("user");
                } else {
                    request.getRequestDispatcher("WEB-INF/JSP/page/login.jsp").forward(request, response);
                }
            }
        }} catch (SQLException ex) {
            Logger.getLogger(TestingServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "не удалось войти");
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
