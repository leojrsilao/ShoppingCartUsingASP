package Shopping;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AnnonCookie {
	
	public static ArrayList<Cookie> ck = new ArrayList<Cookie>();
	
	public static Cookie getCookie(String name, HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		for (int i=0; cookies != null && i<cookies.length; i++)
		{
			if(cookies[i].getName().equals(name))
				return cookies[i];
		}
		return null;
	}
	
	public static void createCookie(String name, String value, HttpServletResponse response){
		Cookie cookie = new Cookie(name, value);
		response.addCookie(cookie);
		cookie.setMaxAge(1);//1 day or 1440 minutes
		ck.add(cookie);
	}
	
	public static void checkUserCookie(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		
		String id = "";
		if(AnnonCookie.getCookie("annon_id", request) == null){
			id = UUID.randomUUID().toString();
			AnnonCookie.createCookie("annon_id", id, response);
		}
		else{
			id = AnnonCookie.getCookie("annon_id", request).getValue();
		}
		
		
		if(session.getAttribute("annon_id") == null){
			//System.out.println(AnnonCookie.getCookie("annon_id", request).getValue());
			session.setAttribute("annon_id", id); //set the annon_id to the uuid
		}
	}
}
