package itstep.learning.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.dal.dao.AuthDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class AuthServlet extends HttpServlet {
    private final AuthDao authDao;

    @Inject
    public AuthServlet(AuthDao authDao) {
        this.authDao = authDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        RestResponse restResponse = new RestResponse();
        try {
            String authHeader = req.getHeader("Authorization");
            if(authHeader ==null) {
                throw new Exception("Authorization header not found");
            }
        }
        catch (Exception ex) {
            restResponse.setStatus("error");
            restResponse.setData(ex.getMessage());
        }


        Gson gson = new GsonBuilder().serializeNulls().create();
        resp.setContentType("application/json");
        resp.getWriter().println(gson.toJson(restResponse));
    }
    class RestResponse {
        private int code;
        private String status;
        private Object data;

        public RestResponse() {}

        public RestResponse(int code, String status, Object data) {
            this.code = code;
            this.status = status;
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
