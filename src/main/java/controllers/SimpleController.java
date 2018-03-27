package controllers;

import database.entity.Country;
import database.entity.User;
import database.entity.dop.ChangeForm;
import database.entity.dop.UserForm;
import database.exceptions.DbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import services.DbService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class SimpleController {

    private final HttpSession session;

    private final DbService databaseService;

    /*@Autowired
    private UserFormValidator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }*/

    @Autowired
    public SimpleController(HttpSession session, DbService databaseService) {
        this.session = session;
        this.databaseService = databaseService;
    }

    @RequestMapping("/")
    public String index(Model model) throws DbException {
        model.addAttribute("userForm",new UserForm());

        return "index";
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String enter(@ModelAttribute("userForm") @Valid UserForm userForm,
                        BindingResult bindingResult,Model model) throws DbException {
        if (bindingResult.hasErrors()) {
            printErrors(bindingResult);

            return "index";
        } else {

            System.out.println("userForm:\n");
            System.out.println(userForm.getCountryId());
            System.out.println(userForm.getUsername());

            User user = databaseService.getUser(userForm);
            model.addAttribute("user",user);

            session.setAttribute("user",user);
        }
        return "redirect:/personal";
    }

    @RequestMapping(value = "/personal",method = RequestMethod.GET)
    public String enter(Model model) throws DbException {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        return "user/personal_page";
    }

    @RequestMapping(value = "/registration",method = RequestMethod.GET)
    public String registration(Model model) throws DbException {
        model.addAttribute("userForm",new UserForm());

        List<Country> countries = databaseService.findAllCountries();

        System.out.println("countryList");
        for(Country country: countries) {
            System.out.println("country = " + country.getId() + " " + country.getName());
        }

        model.addAttribute("countries",countries);

        return "main/registration";
    }

    @RequestMapping(value = "/registration",method = RequestMethod.POST)
    public String registrationPost(@ModelAttribute("userForm") @Valid UserForm userForm,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            return "main/registration";
        } else {
            try {
                System.out.println("userForm:\n");
                System.out.println(userForm.getCountryId());
                System.out.println(userForm.getUsername());

                databaseService.insertUser(userForm);
            } catch (DbException e) {
                System.out.println(e.getMessage());
            }

            return "redirect:/";
        }
    }

    @RequestMapping(value = "/change-personal",method = RequestMethod.POST)
    public String changePersonal(@ModelAttribute("userForm") @Valid ChangeForm changeForm,
                        BindingResult bindingResult,Model model) throws DbException {
        System.out.println("changePersonalPOST");
        if (bindingResult.hasErrors()) {
            printErrors(bindingResult);

            return "user/change_personal";
        } else {

            System.out.println("loginForm:\n");
            System.out.println(changeForm.getCountryId());
            System.out.println(changeForm.getUsername());

            User user = (User) session.getAttribute("user");

            databaseService.updateUser(user, changeForm);

            session.setAttribute("user",user);

        }
        return "redirect:/personal";
    }

    @RequestMapping(value = "/change-personal",method = RequestMethod.GET)
    public String changePersonalGet(Model model) throws DbException {
        System.out.println("changePersonalGET");

        User user = (User) session.getAttribute("user");
        ChangeForm changeForm = new ChangeForm();
        changeForm.setUsername(user.getUsername());
        changeForm.setCountryId(user.getCountry().getId());
        changeForm.setGender(user.getGender());

        model.addAttribute("userForm", changeForm);

        List<Country> countries = databaseService.findAllCountries();

        model.addAttribute("countries",countries);

        return "user/change_personal";
    }


    @RequestMapping(value = "/exit",method = RequestMethod.GET)
    public String exit(Model model) throws DbException {
        session.removeAttribute("user");

        return "redirect:/";
    }

    @RequestMapping(value = "/delete-user",method = RequestMethod.GET)
    public String deleteUser(Model model) throws DbException {
        User user = (User) session.getAttribute("user");
        session.removeAttribute("user");

        databaseService.deleteUser(user.getId());

        return "redirect:/";
    }

    private void printErrors(BindingResult bindingResult){
        for(ObjectError error:bindingResult.getAllErrors()) {
            System.out.println("error : " + error.toString());
        }
    }

}
