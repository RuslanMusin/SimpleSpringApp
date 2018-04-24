package controllers.ajaxSearch;

import database.dao.postgresDao.ActorDao;
import database.entity.Actor;
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

@WebServlet(name = "searchActors" , value = "/searchActors")
public class SearchActors extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String queryActor = req.getParameter("actor");

        String searchType = req.getParameter("searchType");

        Integer numPage = Integer.valueOf(req.getParameter(Const.KEY_NUM_PAGE));

        List<Actor> actors = new ArrayList<>();
        try {
            if (queryActor != null && !queryActor.trim().equals("")) {
                String [] queryParts = divideQueryOnParts(queryActor);
                String actorName = queryParts[0];
                String actorSurname = queryParts[1];
                actors = new ActorDao(DbWrapper.getConnection()).findActorsByName(actorName,actorSurname, numPage);
                for(Actor actor : actors){
                    System.out.println(actor.getName() + actor.getSurname());
                }
                req.setAttribute("title","Найденные по данному имени");
            } else {
                actors = new ActorDao(DbWrapper.getConnection()).findMostPopular(numPage);
                req.setAttribute("title","Самые популярные");
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

        req.setAttribute("actors", actors);
        req.setAttribute("size",actors.size());

        switch (searchType){
            case "user":
                req.getRequestDispatcher("/WEB-INF/xml/user/user_actors.jsp").forward(req, resp);
                break;

            case "movie":
                req.getRequestDispatcher("/WEB-INF/xml/admin/actors.jsp").forward(req, resp);
                break;

        }

    }

    private String[] divideQueryOnParts(String queryActor){
        String [] searchParts = queryActor.split(" ");
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
