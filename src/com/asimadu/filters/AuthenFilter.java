package com.asimadu.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.asimadu.data.DataAccess;

public class AuthenFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("user_r")){
					String identifier = cookie.getValue().split("___")[0];
					String token = cookie.getValue().split("___")[1];
					String res = DataAccess.verifyCookie(identifier,token);
					
					if(res != null){
						System.out.println("res is "+res);
						
						HttpSession session = request.getSession();
						session.setAttribute("auth", "true");
				        session.setAttribute("firstname", res.split(":")[0]);
				        session.setAttribute("imageId", res.split(":")[1]);
					} else {
						System.out.println("res is null");
					}
				}
			}
		} else {
			System.out.println("cookies is null for this");
		}
		
		chain.doFilter(req, resp);
	}

}
