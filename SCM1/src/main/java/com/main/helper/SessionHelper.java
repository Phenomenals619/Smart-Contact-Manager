package com.main.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Component
public class SessionHelper {
	
	public void removeSessionMsg() {
		try {
			 RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		        if (requestAttributes instanceof ServletRequestAttributes) {
		            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		            // Remove session message logic
		            request.getSession().removeAttribute("message");
		        }	
		        
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
