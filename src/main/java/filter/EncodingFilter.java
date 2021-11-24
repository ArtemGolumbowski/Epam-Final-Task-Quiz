/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.Logger;

/**
 * Context Listener
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(EncodingFilter.class);
	private static final String DEFAULT_ENCODING = "UTF-8";
	private String encoding;

        @Override
	public void destroy() {
		LOG.trace("====================================");
		LOG.info("Filter destruction starts");
	}

        @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		LOG.info("Filter starts");
		request.setCharacterEncoding(DEFAULT_ENCODING);
		response.setContentType("text/html; charset=UTF-8");

		response.setCharacterEncoding(encoding);

		LOG.info("Filter finished");
		chain.doFilter(request, response);
	}

        @Override
	public void init(FilterConfig config) throws ServletException {
		LOG.info("Filter initialization starts");
		encoding = config.getInitParameter("encoding");
		if (encoding == null) {
			encoding = DEFAULT_ENCODING;
			LOG.info("Encoding from Filter Config: " + encoding);
		}
		LOG.info("Filter initialization finished");
	}

}
