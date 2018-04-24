package controllers;

import database.entity.Country;
import database.entity.Gender;
import database.entity.User;
import database.entity.forms.UserChangeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import services.IUserService;
import services.SearchService;
import services.UserService;
import exceptions.DbException;
import utils.MethodMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static utils.Const.*;

@Controller
@PreAuthorize("isAuthenticated()")
public class UserController {

    private static final String DELETE_USER = "/delete-user";
    private static final String CHANGE_PROFILE = "/change-personal";

    private static final String CHANGE_FORM = "userChangeForm";

    @Autowired
    private MethodMap methodMap;

    @Autowired
    private HttpSession session;

    @Autowired
//    @Qualifier(value = "userService")
    private IUserService userService;

    @Autowired
    private SearchService searchService;

    private final String CHANGE_PERSONAL_GET = "changePersonalGet";

    private final String NAME = "UC#";

    @RequestMapping(value = CHANGE_PROFILE,method = RequestMethod.GET)
    public String changePersonalGet(Model model,@AuthenticationPrincipal User user) throws DbException {
        System.out.println("changePersonalGET");

//        User user = (User) session.getAttribute("user");

        List<Country> countries = searchService.findAllCountries();

        if(!model.containsAttribute(CHANGE_FORM)) {
            UserChangeForm userChangeForm = new UserChangeForm();
            userChangeForm.setUsernameReal(user.getUsernameReal());
            userChangeForm.setCountry(user.getCountry());
            userChangeForm.setGender(user.getGender().toString());
            model.addAttribute(CHANGE_FORM, userChangeForm);
        }
        model.addAttribute(GENDERS_ATTR, Gender.values());
        model.addAttribute(COUNTRIES_ATTR,countries);

        return "user/change_personal";
    }

    @RequestMapping(value = CHANGE_PROFILE,method = RequestMethod.POST)
    public String changePersonalPost(@ModelAttribute(CHANGE_FORM) @Valid UserChangeForm userChangeForm,
                                 BindingResult bindingResult, Model model,
                                 RedirectAttributes attr,
                                     @AuthenticationPrincipal User user) throws DbException, UnsupportedEncodingException {
        System.out.println("changePersonalPOST");
        if (bindingResult.hasErrors()) {
            attr.addFlashAttribute(CHANGE_FORM, userChangeForm);
            attr.addFlashAttribute(BINDING_RESULT + CHANGE_FORM,bindingResult);
            return methodMap.redirectReq(NAME + CHANGE_PERSONAL_GET);
        } else {

            System.out.println("loginForm:\n");
            System.out.println(userChangeForm.getUsernameReal());

//            User user = (User) session.getAttribute(USER_SESSION);

            userService.updateUser(user, userChangeForm);

//            session.setAttribute(USER_SESSION,user);

        }
        return methodMap.redirectReq(
                SecurityController.NAME + SecurityController.PROFILE_GET);
    }

    @RequestMapping(value = DELETE_USER,method = RequestMethod.GET)
    public String deleteUser(@AuthenticationPrincipal User user) throws DbException {

//        User user = (User) session.getAttribute(USER_SESSION);

        userService.deleteUser(user.getId());

        return "redirect:/logout";
//        return methodMap.redirectReq(
//                SecurityController.NAME + SecurityController.LOGOUT_GET);
    }


}
