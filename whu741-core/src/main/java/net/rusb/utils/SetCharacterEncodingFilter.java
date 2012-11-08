package net.rusb.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetCharacterEncodingFilter implements Filter {

	protected String encoding = null;

	protected FilterConfig filterConfig = null;

	protected boolean ignore = true;

	public void destroy() {
		// TODO Auto-generated method stub
		
		this.encoding = null;
		this.filterConfig = null;

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		request.setAttribute("dateOfToday", new SimpleDateFormat("yyyyƒÍMM‘¬dd»’").format(DateTool.getCST()));
		if(ignore || (request.getCharacterEncoding() == null)) {
			String encoding = selectEncoding(request);
			if(encoding != null) {
				request.setCharacterEncoding(encoding);
			}
		}
		chain.doFilter(request, response);

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");
		
		if(value == null) this.ignore = true;
		else if(value.equalsIgnoreCase("true")) this.ignore = true;
		else this.ignore = false;
		
	}
	
	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}

}
