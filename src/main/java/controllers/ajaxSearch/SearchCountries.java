package controllers.ajaxSearch;

import database.dao.postgresDao.CountryDao;
import database.entity.Country;
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

@WebServlet(name = "searchCountries" , value = "/searchCountries")
public class SearchCountries extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String queryCountry = req.getParameter("country");

        System.out.println(queryCountry);

        List<Country> countries = new ArrayList<>();
        try {
            countries = new CountryDao(DbWrapper.getConnection()).findCountriesByName(queryCountry);
        } catch (DbException e) {
            e.printStackTrace();
        }

        req.setAttribute("countries", countries);
        req.getRequestDispatcher("/WEB-INF/xml/admin/countries.jsp").forward(req, resp);
    }
}
