package controllers.ajaxSearch;

import database.dao.postgresDao.GenreDao;
import database.entity.Genre;
import exceptions.DbException;
import utils.DatabaseUtils.DbWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "searchGenres" , value = "/searchGenres")
public class SearchGenres extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String queryGenre = req.getParameter("genre");

        List<Genre> genres = new ArrayList<>();
        try {
            genres = new GenreDao(DbWrapper.getConnection()).findGenresByName(queryGenre);
        } catch (DbException e) {
            e.printStackTrace();
        }


        req.setAttribute("genres", genres);
        req.getRequestDispatcher("/WEB-INF/xml/admin/genres.jsp").forward(req, resp);
    }
}

