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
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String loginURI = httpRequest.getContextPath() + "/login";
        String registrationURI = httpRequest.getContextPath() + "/registration";

        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isRegistrationRequest = httpRequest.getRequestURI().equals(registrationURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("index.jsp");
        boolean isRegistered = session != null && session.getAttribute("role") != null;
        if (isRegistered) {
            if (isLoginRequest || isRegistrationRequest || isLoginPage) {
                if (session.getAttribute("role").equals(Role.ADMIN)) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/get/tests");
                    dispatcher.forward(request, response);
                } else if (session.getAttribute("role").equals(Role.USER)) {
                    RoutingUtils.forwardToPage("user.jsp", httpRequest, httpResponse);

                }
            } else {
                chain.doFilter(request, response);
            }
        }
        if (!isRegistered) {
            if (isLoginRequest || isRegistrationRequest || isLoginPage) {
                chain.doFilter(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
                dispatcher.forward(request, response);

            }
        }
    }

    public void destroy() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

}
