package controllers.ajaxSearch;

import database.dao.postgresDao.DirectorDao;
import database.entity.Director;
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

@WebServlet(name = "searchDirectors" , value = "/searchDirectors")
public class SearchDirectors extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String queryDirector = req.getParameter("director");

        String searchType = req.getParameter("searchType");

        Integer numPage = Integer.valueOf(req.getParameter(Const.KEY_NUM_PAGE));

        List<Director> directors = new ArrayList<>();
        try {
            if (queryDirector != null && !queryDirector.trim().equals("")) {
                String [] queryParts = divideQueryOnParts(queryDirector);
                String directorName = queryParts[0];
                String directorSurname = queryParts[1];
                directors = new DirectorDao(DbWrapper.getConnection()).findDirectorsByName(directorName,directorSurname, numPage);

                req.setAttribute("title","Найденные по данному имени");
            } else {
                directors = new DirectorDao(DbWrapper.getConnection()).findMostPopular(numPage);
                req.setAttribute("title","Самые популярные");
            }

        } catch (DbException e) {
            e.printStackTrace();
        }


        req.setAttribute("directors", directors);
        req.setAttribute("size",directors.size());

        switch (searchType){
            case "user":
                req.getRequestDispatcher("/WEB-INF/xml/user/user_directors.jsp").forward(req, resp);
                break;

            case "movie":
                req.getRequestDispatcher("/WEB-INF/xml/admin/directors.jsp").forward(req, resp);
                break;

        }

    }

    private String[] divideQueryOnParts(String queryDirector){
        String [] searchParts = queryDirector.split(" ");
        String [] realParts = new String[2];
        int partsCount = 0;
        for(int i = 0; i < searchParts.length && partsCount < 2; i++){
            System.out.println(searchParts[i]);
            if(!searchParts[i].trim().equals("")){
                realParts[partsCount] = searchParts[i].trim();
                partsCount++;
            }
        }
        if(realParts[1] == null){
            realParts[1] = realParts[0];
        }
        System.out.println(realParts[0] + " : " + realParts[1]);
        return realParts;
    }
}
