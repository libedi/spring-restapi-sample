package com.libedi.demo.framework.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * HeaderForwardFilter
 * - 특정 요청 헤더값을 응답 헤더값으로 포워딩
 * @author Sangjun, Park
 *
 */
@Component
public class HeaderForwardFilter extends OncePerRequestFilter {
	
	private final String HEADER = "X-HEADER";

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {
		final String header = request.getHeader(HEADER);
		if (StringUtils.hasLength(header)) {
			response.addHeader(HEADER, header);
		}
		filterChain.doFilter(request, response);
	}
}
