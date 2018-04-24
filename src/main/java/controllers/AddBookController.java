package controllers;

import database.entity.Country;
import database.entity.forms.BookForm;
import exceptions.AddPhotoException;
import exceptions.AddRelatedEntitiesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import services.AddMainEntityService;
import services.SearchService;
import exceptions.DbException;
import utils.Const;
import utils.MethodMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static utils.Const.BINDING_RESULT;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AddBookController {

    private static final String ADD_BOOK = "/add-book";

    private static final String BIND_AUTHORS = "bind-authors";

    private static final String BIND_COUNTRIES = "bind-countries";

    private static final String BIND_GENRES = "bind-genres";

    private static final String SAVE_BOOK_DATA = "saveBookData";

    private final String BOOK_FORM = "bookForm";

    private final String NAME = "ABC#";

    private final String ADD_BOOK_GET = "addBook";

    @Autowired
    private AddMainEntityService addMainEntityService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private HttpSession session;

    @Autowired
    private MethodMap methodMap;

    @RequestMapping(value = ADD_BOOK,method = RequestMethod.GET)
    public String addBook(Model model) {
        System.out.println("addBook");

        if(!model.containsAttribute(BOOK_FORM)) {
            Object bookObject = session.getAttribute("bookForm");
            BookForm bookForm;
            if (bookObject != null) {
                bookForm = (BookForm) bookObject;
            } else {
                bookForm = new BookForm();
                session.setAttribute(BOOK_FORM, bookForm);
            }

            model.addAttribute(BOOK_FORM, bookForm);
        }

        return "addInstance/addBook";

    }

    @RequestMapping(value = ADD_BOOK,method = RequestMethod.POST)
    public String addBookPost(@ModelAttribute(BOOK_FORM) @Valid BookForm bookForm,
                              BindingResult bindingResult, HttpServletRequest req,
                              RedirectAttributes attr) throws AddPhotoException, AddRelatedEntitiesException {
        if (bindingResult.hasErrors()) {
            attr.addFlashAttribute(BOOK_FORM, bookForm);
            attr.addFlashAttribute(BINDING_RESULT + BOOK_FORM,bindingResult);
            addMainEntityService.printErrors(bindingResult);
            return "redirect:" + ADD_BOOK;
        } else {

            System.out.println("userForm:\n");
            System.out.println(bookForm.getYear());
            System.out.println(bookForm.getDescription());
            System.out.println(bookForm.getCountriesId());
            System.out.println(bookForm.getName());
            System.out.println(bookForm.getDescription());
            System.out.println("authPhoto = " + bookForm.getPhoto().getOriginalFilename());

            addMainEntityService.insertBook(bookForm,req);

            session.removeAttribute(BOOK_FORM);
        }
        return methodMap.redirectReq(
                SecurityController.NAME + SecurityController.PROFILE_GET);

    }

    @RequestMapping(value = BIND_AUTHORS,method = RequestMethod.GET)
    public String bindAuthors(Model model) {
        System.out.println("bindAuthors");

        List<Country> countries = searchService.findAllCountries();
        model.addAttribute("countries",countries);

        return "adminSearch/search-authors";

    }

    @RequestMapping(value = BIND_GENRES,method = RequestMethod.GET)
    public String bindGenres(Model model) {
        System.out.println("bindGenres");

        return "adminSearch/search-genres";

    }

    @RequestMapping(value = BIND_COUNTRIES,method = RequestMethod.GET)
    public String bindCountries(Model model) {
        System.out.println("bindCountries");

        return "adminSearch/search-countries";

    }

    @RequestMapping(value = BIND_AUTHORS,method = RequestMethod.POST)
    public String bindAuthorsPost(Model model, HttpSession session,
                                  String authorsId, String authorsNames) {
        System.out.println("bindAuthors");

        BookForm bookForm = (BookForm) session.getAttribute("bookForm");

        bookForm.setAuthorsId(authorsId);
        bookForm.setAuthorsNames(authorsNames);

        model.addAttribute("bookForm",bookForm);
        session.setAttribute("bookForm",bookForm);

        return methodMap.redirectReq(NAME + ADD_BOOK_GET);

    }

    @RequestMapping(value = BIND_GENRES,method = RequestMethod.POST)
    public String bindGenresPost(Model model, HttpSession session,
                                 String genresId, String genresNames) {
        System.out.println("bindGenres");

        BookForm bookForm = (BookForm) session.getAttribute("bookForm");

        bookForm.setGenresId(genresId);
        bookForm.setGenresNames(genresNames);

        model.addAttribute("bookForm",bookForm);

        session.setAttribute("bookForm",bookForm);

        return methodMap.redirectReq(NAME + ADD_BOOK_GET);

    }

    @RequestMapping(value = BIND_COUNTRIES,method = RequestMethod.POST)
    public String bindCountriesPost(Model model, HttpSession session,
                                    String countriesId, String countriesNames) {
        System.out.println("bindCountries");

        BookForm bookForm = (BookForm) session.getAttribute("bookForm");

        bookForm.setCountriesId(countriesId);
        bookForm.setCountriesNames(countriesNames);

        model.addAttribute("bookForm",bookForm);

        session.setAttribute("bookForm",bookForm);

        return methodMap.redirectReq(NAME + ADD_BOOK_GET);

    }

    @RequestMapping(value = SAVE_BOOK_DATA,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity saveBookData(HttpSession session,
                                       @RequestParam String name,
                                       @RequestParam String description,
                                       @RequestParam String authorsId,
                                       @RequestParam String genresId,
                                       @RequestParam String countriesId) {
        System.out.println("saveBookData");

        BookForm bookForm = (BookForm) session.getAttribute("bookForm");

        bookForm.setName(name);
        bookForm.setDescription(description);
        bookForm.setAuthorsId(authorsId);
        bookForm.setGenresId(genresId);
        bookForm.setCountriesId(countriesId);

        System.out.println("bookName = " + bookForm.getName());
        System.out.println("description = " + description);
        System.out.println("authorsId = " + authorsId);
        System.out.println("genresId = " + genresId);
        System.out.println("countriesId = " + countriesId);


        session.setAttribute("bookForm",bookForm);

        return ResponseEntity.status(HttpStatus.OK).body(null);

    }


}
