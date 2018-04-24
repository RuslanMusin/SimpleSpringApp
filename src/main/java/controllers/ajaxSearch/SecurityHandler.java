package services;

import database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@Component
public class SecurityHandler implements AuthenticationSuccessHandler {

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();

        System.out.println("user hand = "  + user.getUsername());


        HttpSession session = request.getSession();

        Object userSession = session.getAttribute("user");

        if(userSession == null){
            session.setAttribute("user",user);
            System.out.println("user hadn saved = "  + user.getCountry().getName());
        }

        response.sendRedirect("/login");
    }
}

