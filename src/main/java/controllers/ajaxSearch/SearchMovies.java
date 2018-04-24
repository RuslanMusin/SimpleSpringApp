package controllers.ajaxSearch;

import database.dao.postgresDao.MovieDao;
import database.entity.Movie;
import exceptions.DbException;
import utils.Const;
import utils.DatabaseUtils.DbWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "searchMovies" , value = "/searchMovies")
public class SearchMovies extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Hella");

        String queryMovie = req.getParameter("movie");

        String searchType = req.getParameter("searchType");

        Integer numPage = Integer.valueOf(req.getParameter(Const.KEY_NUM_PAGE));

        System.out.println("query = " + queryMovie + " type = "  +searchType + " num = " + numPage);

        List<Movie> movies = new ArrayList<>();
        try {
            if (queryMovie != null && !queryMovie.trim().equals("")) {
                System.out.println("ищем по имени");
                movies = new MovieDao(DbWrapper.getConnection()).findMovieByName(queryMovie, numPage);
                req.setAttribute("title","Найденные по данному имени");
            } else {
                movies = new MovieDao(DbWrapper.getConnection()).findMostPopular(numPage);
                req.setAttribute("title","Самые популярные");
            }
        } catch (DbException exception) {
            exception.getMessage();
        }
        if (movies.size() == 0) {
            System.out.println("actors is null");
        } else {
            for (Movie movie : movies) {
                System.out.println(movie.getName());
            }
        }

        req.setAttribute("movies", movies);
        req.setAttribute("size",movies.size());


        switch (searchType) {
            case "user":
                req.getRequestDispatcher("/WEB-INF/xml/user/user_movies.jsp").forward(req, resp);
                break;

            case "group":
                req.getRequestDispatcher("/WEB-INF/xml/movies.jsp").forward(req, resp);
                break;

            case "menu":
                req.getRequestDispatcher("/WEB-INF/xml/menu_search.jsp").forward(req, resp);
                break;
        }

    }
}
