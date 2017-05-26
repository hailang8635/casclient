package com.smart.casclient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginCtrl {
	static public Logger log = LoggerFactory.getLogger(LoginCtrl.class);

	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/login")
	public ModelAndView login(HttpSession session) {
		String user = request.getRemoteUser();
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
		log.info("{}, {}, {}", session.getId(), user, principal.getAttributes());
		session.setAttribute("login", true);
		session.setAttribute("userName", principal.getAttributes().get("firstname"));
//		return "success";
		return new ModelAndView("success");
	}
	
	
	
	@RequestMapping(value = "/getInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getInfo(HttpSession session) {
		log.info("getInfo, {}", session.getAttribute("userName"));
		return new ModelAndView("success");
	}
	
	
    @RequestMapping(value = "/loginout", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView loginout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:https://smart:8443/cas/logout?service=https://smart:7443/cas/login");
    }
}
