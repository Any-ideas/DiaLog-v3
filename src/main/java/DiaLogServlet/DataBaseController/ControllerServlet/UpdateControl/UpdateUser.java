package DiaLogServlet.DataBaseController.ControllerServlet.UpdateControl;


import DiaLogApp.TaskData;
import DiaLogApp.UserData;
import DiaLogServlet.DataBaseController.SQLTableMethods.UserDataSQL;
import DiaLogServlet.ServletResponse.ErrorCode;
import DiaLogServlet.ServletResponse.sendResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import static DiaLogServlet.DataBaseController.ControllerServlet.AddControl.AddUser.LoginServlet.UserID;


@WebServlet(urlPatterns={"/api/post/update/user"}, loadOnStartup=1)
public class UpdateUser extends Update {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String servletPath = req.getServletPath();
        switch (servletPath) {
            case "/api/post/update/user":
                try {
                    update(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        }
    }
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String jsonData = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        UserData userData = gson.fromJson(jsonData, UserData.class);

        if (userData != null) {
            if (1 == UserDataSQL.updateUser(userData)){
                sendResponse.send(resp, ErrorCode.SUCCESS);
            };
            sendResponse.send(resp, ErrorCode.OPERATION_ERROR);
        } else {
            sendResponse.send(resp, ErrorCode.DATA_NOT_FOUND_ERROR);
        }
    }
}
