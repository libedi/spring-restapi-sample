package com.libedi.demo.framework.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class HeaderSendingFilter extends OncePerRequestFilter {
	
	private static final String HEADER = "X-HEADER";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String header = request.getHeader(HEADER);
		if(StringUtils.hasLength(header)) {
			filterChain.doFilter(request, new HeaderResponseWrapper(response, header));
		} else {
			filterChain.doFilter(request, response);
		}

	}
	
	static class HeaderResponseWrapper extends HttpServletResponseWrapper {

		public HeaderResponseWrapper(final HttpServletResponse response, final String header) {
			super(response);
			response.addHeader(HEADER, header);
		}
		
	}

}
