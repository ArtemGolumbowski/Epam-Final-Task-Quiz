/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import entity.Role;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.RoutingUtils;

/**
 *
 * @author agolu
 */
@WebFilter(filterName = "AdminLoginFilter", urlPatterns = {"/admin/*"})
public class AdminLoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
            dispatcher.forward(request, response);
        }
        if (session != null && session.getAttribute("role") == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
            dispatcher.forward(request, response);
        }
        boolean isLoggedInAsAdmin = (session.getAttribute("role").equals(Role.ADMIN));
        boolean isLoggedInAsUser = (session.getAttribute("role").equals(Role.USER));

        if (isLoggedInAsUser) {

            RoutingUtils.forwardToPage("user.jsp", httpRequest, httpResponse);
        }
        if (isLoggedInAsAdmin) {

            chain.doFilter(request, response);

        }

    }

    public void destroy() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

}
