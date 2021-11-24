/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import javax.servlet.Filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import utils.RoutingUtils;


@WebFilter(filterName="ErrorHandlerFilter", urlPatterns = "/*")
public class ErrorFilter  implements Filter{
    Logger LOGGER = Logger.getLogger(ErrorFilter.class);
    
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
		try {
			chain.doFilter(req, resp);
		} catch (Exception th) {
			String requestUrl = req.getRequestURI();
			LOGGER.error("Request " + requestUrl + " failed: " + th.getMessage(), th);
			RoutingUtils.forwardToPage("error.jsp", req, resp);
		}
	}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       LOGGER.info("init");
    }

    

    @Override
    public void destroy() {
        LOGGER.info("destroy filter");
    }
 
}