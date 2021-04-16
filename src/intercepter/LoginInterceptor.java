package intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import entity.*;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse respone, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("mAdmin") == null) {
			respone.sendRedirect(request.getContextPath() + "/account/login.htm");
			return false;
		}
		return true;
	}
}