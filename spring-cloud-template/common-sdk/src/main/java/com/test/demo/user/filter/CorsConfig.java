package com.test.demo.user.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsConfig implements Filter {
	static final Logger logger = LoggerFactory.getLogger(CorsConfig.class);
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods",
				"POST, GET, PUT, OPTIONS,PATCH, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Authorization,content-type,*");
		chain.doFilter(req, res);
	}
	
	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}
}