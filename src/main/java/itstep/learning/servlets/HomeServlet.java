package itstep.learning.servlets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.dal.dao.AuthDao;
import itstep.learning.services.db.DbService;
import itstep.learning.services.hash.HashService;
import itstep.learning.services.kdf.KdfService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@Singleton
public class HomeServlet extends HttpServlet     {
    //Внедрение зависимостей(инжекция)

    private final AuthDao authDao; // инжектить классы (не интерфейсы) регистрировать не надо

    @Inject
    public HomeServlet( AuthDao authDao) {
        this.authDao = authDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ~ return View
        boolean isSigned = false;
        Object signature = req.getAttribute("signature");
        if(signature instanceof Boolean) {
            isSigned = (Boolean) signature;
        }
        if(isSigned) {
            String dbMessage =
                authDao.install() ?
                "Install OK" : "Install failed";

            req.setAttribute("hash",dbMessage);
            req.setAttribute("body","home.jsp"); //~ViewData["body"] = "home.jsp";
        }
        else{
            req.setAttribute("body","insecure.jsp");
        }
        req.getRequestDispatcher( "WEB-INF/views/_layout.jsp" ).forward(req, resp);
    }
    
}
/*
 Сервлеты - это специализированные классы для сетевых задач, кроме
 HttpServlet - для веб-задач, это аналог контролеров в ASP
 */
