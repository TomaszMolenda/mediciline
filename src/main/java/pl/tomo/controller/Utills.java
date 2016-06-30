package pl.tomo.controller;

import javax.servlet.http.HttpServletRequest;

public class Utills {
	
	public static String makeUrlQueryByPrevious(HttpServletRequest request)
	{	
		String referrer = request.getHeader("referer");
		int indexOf = referrer.indexOf("?");
	    return (indexOf == -1) ? "" : referrer.substring(indexOf);
	}

}
