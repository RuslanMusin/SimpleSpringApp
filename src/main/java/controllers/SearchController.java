package controllers;

import database.entity.*;
import exceptions.SearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import services.SearchService;
import utils.Const;

import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/searchBooks",method = RequestMethod.GET)
    public String searchBooks(Model model,String query,String type,String numPage) {
        System.out.println("showBookListGET");
        String view = "";

        try {
            List<Book> books = new ArrayList<>();
            if (query != null && !query.trim().equals("")) {
                System.out.println("ищем по имени");
                books = searchService.findBookByNameCriteria(query, Integer.valueOf(numPage));
                model.addAttribute("title", "Найденные по данному имени");
            } else {
                books = searchService.findPopularBooksCriteria(Integer.valueOf(numPage));
                model.addAttribute("title", "Самые популярные");
            }

            if (books.size() == 0) {
                System.out.println("actors is null");
            } else {
                for (Book movie : books) {
                    System.out.println(movie.getName());
                }
            }

            model.addAttribute("books", books);
            model.addAttribute("size", books.size());


            switch (type) {
                case "user":
                    view = "xml_parts/user/user_books";
                    break;

                case "menu":
                    view = "xml_parts/menu_search";
                    break;
            }

        } catch (Exception ex) {
            throw new SearchException(Const.KEY_BOOK_TYPE);
        }

        return view;

    }


    @RequestMapping(value = "/searchAuthors",method = RequestMethod.GET)
    public String searchAuthors(Model model,@RequestParam String query,
                                 @RequestParam String type,@RequestParam String numPage) {
        System.out.println("showBookListGET");
        String view = "";

        try {
            List<Author> authors = new ArrayList<>();
            if (query != null && !query.trim().equals("")) {
                System.out.println("ищем по имени");
                authors = searchService.findAuthorByName(query, Integer.valueOf(numPage));
                model.addAttribute("title", "Найденные по данному имени");
            } else {
                authors = searchService.findPopularAuthors(Integer.valueOf(numPage));
                model.addAttribute("title", "Самые популярные");
            }

            if (authors.size() == 0) {
                System.out.println("actors is null");
            } else {
                for (Author author : authors) {
                    System.out.println("author =" + author.getName());
                }
            }

            model.addAttribute("authors", authors);
            model.addAttribute("size", authors.size());


            switch (type) {
                case "user":
                    view = "xml_parts/user/user_authors";
                    break;

                case "movie":
                    view = "xml_parts/admin/authors";
                    break;

            }

        } catch(Exception ex) {
            throw new SearchException(Const.KEY_AUTHOR_TYPE);
        }

        return view;

    }

    @RequestMapping(value = "/searchCountries",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public String searchCountries(Model model,@RequestParam String query) {
        System.out.println("searchCountries");

        try {
            List<Country> countries = searchService.findCountriesByName(query);

            model.addAttribute("countries", countries);
        }catch (Exception ex){
            throw new SearchException(Const.KEY_COUNTRY_TYPE);
        }

        return "xml_parts/admin/countries";

    }

    @RequestMapping(value = "/searchGenres",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public String searchGenres(Model model,@RequestParam String query) {
        System.out.println("searchGenres");

        try {
            List<Genre> genres = searchService.findGenresByName(query);

            model.addAttribute("genres", genres);
        } catch (Exception e){
            throw new SearchException(Const.KEY_GENRE_TYPE);
        }

        return "xml_parts/admin/genres";

    }


}
