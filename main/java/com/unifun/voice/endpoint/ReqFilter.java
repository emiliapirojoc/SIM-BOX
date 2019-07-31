package com.unifun.voice.endpoint;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;

@WebFilter(urlPatterns = "/*", filterName = "RequestsFilter")
public class ReqFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		 final HttpServletResponse servletResponse = (HttpServletResponse) response;
	        final HttpServletRequest servletRequest = (HttpServletRequest) request;

	        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
	        servletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
	        servletResponse.setHeader("Access-Control-Max-Age", "1000");
	        servletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, origin, authorization, accept, client-security-token, Session-Token");
			if (servletRequest.getMethod().equals(HttpMethod.OPTIONS)) {
	            servletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
	            return;
	        }


			chain.doFilter(request, response);
		
	}
	

}
