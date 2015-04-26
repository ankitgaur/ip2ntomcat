package com.ip2n.interceptors;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LogInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("Inside Interceptor");
		if (request.getMethod().equals("POST")
				&& request.getRequestURL().toString().contains("incidents")) {

			InputStream is = request.getInputStream();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			IOUtils.copy(is, os);
			System.out.println(os.toString());
		}

		return true;
	}
}
