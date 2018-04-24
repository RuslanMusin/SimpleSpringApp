package controllers;

import database.entity.Country;
import database.entity.User;
import database.entity.Gender;
import database.entity.forms.LoginForm;
import database.entity.forms.RegistrationForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import services.IUserService;
import services.SearchService;
import services.UserService;
import exceptions.DbException;
import utils.MethodMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


import static utils.Const.BINDING_RESULT;
import static utils.Const.COUNTRIES_ATTR;
import static utils.Const.GENDERS_ATTR;


@Controller
public class SecurityController {

    private static final String FORBIDDEN = "/403";
    private static final String LOGOUT = "/logout";
    private static final String REGISTRATION = "/registration";

    public static final String LOGIN = "/login";
    public static final String PROFILE = "/profile" ;
    private static final String LOG_ERR = "/log-err";

    private static final String REGISTRATION_FORM = "registrationForm";
    private static final String LOGIN_FORM = "loginForm";

    private final String LOGIN_ERR_PARAM = "logErr";

    public static final String NAME = "SC#";

    public static final String LOGIN_GET = "loginGet";
    public static final String REGISTRATION_GET = "registration";
    public static final String PROFILE_GET = "profileGet";
    public static final String LOGOUT_GET = "logout";

    @Autowired
    private MethodMap methodMap;

    @Autowired
    private HttpSession session;

    @Autowired
//    @Qualifier(value = "userService")
    private IUserService userService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private Logger logger;

   /* @Autowired
    private User user;*/

    @RequestMapping("/")
    public String index(){

//        return methodMap.redirectReq(NAME + LOGIN_GET);
//        return "redirect:" + LOGIN;
//        return "redirect:" + MvcUriComponentsBuilder.fromMappingName(NAME + LOGIN_GET).arg(0,"").arg(1,"").build();
        return methodMap.redirectWithNullArgs(NAME + LOGIN_GET);
    }

    @RequestMapping(LOGIN)
    @PreAuthorize("isAnonymous()")
    public String loginGet(@RequestParam(name = "error", required = false) String error,
                           @RequestParam(name = "logout", required = false) String logout,
                           @ModelAttribute(LOGIN_FORM) LoginForm loginForm,
                           BindingResult bindingResult,
                           HttpServletRequest request,
                           Model model){

        if(!model.containsAttribute(LOGIN_FORM)) {
            model.addAttribute(LOGIN_FORM, new LoginForm());
        }else{
            model.addAttribute(LOGIN_ERR_PARAM,true);
        }
        System.out.println("error " +  error + " logout = " + logout);
        if (error != null && !error.trim().equals("")) {
            model.addAttribute("error", "Invalid username and password!");

            //login form for update page
            //if login error, get the targetUrl from session again.
            /*String targetUrl = getRememberMeTargetUrlFromSession(request);
            System.out.println(targetUrl);
            if(StringUtils.hasText(targetUrl)){
                model.addObject("targetUrl", targetUrl);
                model.addObject("loginUpdate", true);
            }
*/
        }

        if (logout != null && !logout.trim().equals("")) {
            model.addAttribute("msg", "You've been logged out successfully.");
        }
        /*if(err != null){
            model.addAttribute(LOGIN_ERR_PARAM,true);
        }*/

        return "index";
    }


    /*@RequestMapping(value = LOGIN,method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String loginPost(@ModelAttribute(LOGIN_FORM) @Valid LoginForm loginForm,
                        BindingResult bindingResult, Model model,RedirectAttributes attr) {

        System.out.println("enter");
        if (bindingResult.hasErrors()) {
            logger.error("Login was failed.Passed some invalid data");
            if(loginForm != null){
                logger.info(loginForm.getUsername() + loginForm.getPassword());
            }
            if(bindingResult.hasErrors()){
                logger.info("error " + bindingResult.getErrorCount());
            }
            attr.addFlashAttribute(LOGIN_FORM, loginForm);
            attr.addFlashAttribute(BINDING_RESULT + LOGIN_FORM,bindingResult);
            return methodMap.redirectWithNullArgs(NAME + LOGIN_GET);

//            return "redirect:" + LOGIN;
//            return "redirect:" + MvcUriComponentsBuilder.fromMappingName(NAME + LOGIN_GET).build();

        } else {

            User user = (User) userService.loadUserByUsername(loginForm.getUsername());

            logger.info("User Authority : " + user.getAuthorities());

            model.addAttribute("user",user);

            session.setAttribute("user",user);
        }
        return methodMap.redirectReq(NAME + PROFILE_GET);
    }*/

 /*   @RequestMapping(value = LOG_ERR,method = RequestMethod.POST)
    @PreAuthorize("isAnonymous()")
    public String loginError(RedirectAttributes attr,@ModelAttribute(LOGIN_FORM) LoginForm loginForm, BindingResult bindingResult) {
//        attr.addAttribute("error","true");
//        return methodMap.redirectReq(NAME + LOGIN_GET);
       *//* String path = MvcUriComponentsBuilder.fromMappingName(NAME + LOGIN_GET).build();
        System.out.println("path = " + path);
        return "redirect:" + path;*//*
        logger.error("Login was failed.Passed some invalid data");
        if(loginForm != null){
            logger.info(loginForm.getUsername() + loginForm.getPassword());
        }
        if(bindingResult.hasErrors()){
            logger.info("error " + bindingResult.getErrorCount());
        }
        attr.addFlashAttribute(LOGIN_FORM, loginForm);
        attr.addFlashAttribute(BINDING_RESULT + REGISTRATION_FORM,bindingResult);
        return methodMap.redirectWithNullArgs(NAME + LOGIN_GET);

    }*/

    @RequestMapping(value = PROFILE,method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String profileGet(Model model,@AuthenticationPrincipal User user) {
//        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        return "user/personal_page";
    }

    @RequestMapping(value = REGISTRATION,method = RequestMethod.GET)
    @PreAuthorize("isAnonymous()")
    public String registration(Model model) {

        if(!model.containsAttribute(REGISTRATION_FORM)) {
            model.addAttribute(REGISTRATION_FORM,new RegistrationForm());
        }
        List<Country> countries = searchService.findAllCountries();

        model.addAttribute(GENDERS_ATTR, Gender.values());
        model.addAttribute(COUNTRIES_ATTR,countries);

        return "main/registration";
    }

    @RequestMapping(value = REGISTRATION,method = RequestMethod.POST)
    @PreAuthorize("isAnonymous()")
    public String registrationPost(@ModelAttribute(REGISTRATION_FORM) @Valid RegistrationForm registrationForm,
                                   BindingResult bindingResult, RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            attr.addFlashAttribute(REGISTRATION_FORM, registrationForm);
            attr.addFlashAttribute(BINDING_RESULT + REGISTRATION_FORM,bindingResult);
//            return "redirect:" + REGISTRATION;
            return methodMap.redirectReq(NAME + REGISTRATION_GET);
        } else {
            System.out.println("registrationForm:\n");
            System.out.println(registrationForm.getUsernameReal());

            userService.registerUser(registrationForm);

//            return methodMap.redirectReq(NAME + LOGIN_GET);
//            return "redirect:" + MvcUriComponentsBuilder.fromMappingName(NAME + LOGIN_GET).arg(0,"").arg(1,"").build();
        return methodMap.redirectWithNullArgs(NAME + LOGIN_GET);
        }
    }

   /* @RequestMapping(value = LOGOUT, method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String logout(HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr) {
        User user = (User) session.getAttribute("user");
        List<SessionInformation> sessions = sessionRegistry.getAllSessions(user, false);
        sessionRegistry.getSessionInformation(sessions.get(0).getSessionId()).expireNow();

           *//* Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null){
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }*//*

        logger.info("Successful logout.Session was invalidate");

        attr.addAttribute("logout","true");

//        return methodMap.redirectReq(NAME + LOGIN_GET);
        return methodMap.redirectWithNullArgs(NAME + LOGIN_GET);
//        return "redirect:" + MvcUriComponentsBuilder.fromMappingName(NAME + LOGIN_GET).build();

    }
*/
    @RequestMapping(FORBIDDEN)
    public String forbidden() {
        logger.info("access denied");
        return "403";
    }

}
