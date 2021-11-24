/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author agolu
 */
@WebServlet(name = "RegistrationServlet", urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {

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
        request.getRequestDispatcher("WEB-INF/JSP/page/registration.jsp").forward(request, response);
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
        String email = request.getParameter("login");        
        String password = request.getParameter("password");
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(password);
        user.setRole(Role.valueOf(request.getParameter("role").toUpperCase()));
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            UserDAO userDao = new UserDAO(con);
            boolean exist = userDao.isExist(email);
            if (exist) {
                request.setAttribute("errorMessage", "this email allready exist");
                request.getRequestDispatcher("WEB-INF/JSP/page/registration.jsp").forward(request, response);
            }else{
            userDao.create(user);
            response.sendRedirect("login");
        }} catch (SQLException ex) {
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("login");
        }
        
    }
}
