package controllers.filters;

import controllers.SecurityController;
import database.entity.User;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

@WebFilter(urlPatterns = {"/","/login"}, description = "Session Checker Filter")
public class SessionFilter implements Filter {
    private FilterConfig config = null;

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        config.getServletContext().log("Initializing SessionFilter");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;


        HttpSession session = req.getSession();
        Principal principal = req.getUserPrincipal();

      /*  if (session.getAttribute("user") == null && principal instanceof RememberMeAuthenticationToken) {
            RememberMeAuthenticationToken userToken = (RememberMeAuthenticationToken) principal;
            System.out.println("princ = " + userToken.getName());
            User user = (User) userToken.getPrincipal();
            session.setAttribute("user", user);
        }*/
//        if(((HttpServletRequest) request).getRequestURI().equals(SecurityController.LOGIN)) {
      System.out.println("req = " + req.getRequestURI() + " path = " + req.getContextPath());

        if (principal != null && principal instanceof RememberMeAuthenticationToken) {
        /*RememberMeAuthenticationToken userToken = (RememberMeAuthenticationToken) principal;
        System.out.println("princ = " + userToken.getName());
        User user = (User) userToken.getPrincipal();
        session.setAttribute("user", user);*/
            ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/403");
        }
        else {

            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        config.getServletContext().log("Destroying SessionFilter");
    }
}