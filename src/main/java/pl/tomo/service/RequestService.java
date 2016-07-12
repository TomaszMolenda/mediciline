package pl.tomo.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.jcabi.aspects.Loggable;

@Service
@Loggable
public class RequestService {

	public String getAuthCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null) return null;
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("AUTH"))
				return cookie.getValue();
		}
		return null;
	}
}
