package controllers;

import database.entity.Book;
import database.entity.User;
import exceptions.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.SearchService;
import utils.Const;
import exceptions.DbException;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class BookController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private Logger logger;

    @RequestMapping(value = "/books",method = RequestMethod.GET)
    public String showBooks(Model model) {
        System.out.println("showBookListGET");

        List<Book> bookList = searchService.findPopularBooksCriteria(Const.START_PAGE);

        if(logger.isDebugEnabled()){
            logger.debug("found bookList size = " + bookList.size());
        }

        model.addAttribute("books",bookList);
        model.addAttribute("size",bookList.size());
        model.addAttribute(Const.KEY_NUM_PAGE,Const.START_PAGE);

        return "books/book_list";

    }

    @RequestMapping(value = "/book/{id}",method = RequestMethod.GET)
    public String showBook(@PathVariable("id") Integer id,Model model, HttpSession session) {
        System.out.println("books");

        User user = (User) session.getAttribute("user");
        Book book;

        try {
            book = searchService.findBook(id, user.getId());
            if(book == null) {
                throw new Exception();
            }
        } catch (Exception ex){
            throw new NotFoundException(Const.KEY_BOOK_TYPE);
        }

        System.out.println("authors book = " + book.getAuthors().get(0).getName());

        model.addAttribute("book",book);

        return "books/book_content";

    }









}
