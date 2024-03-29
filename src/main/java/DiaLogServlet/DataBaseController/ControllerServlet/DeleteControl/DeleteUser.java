package DiaLogServlet.DataBaseController.ControllerServlet.DeleteControl;

import DiaLogServlet.ServletResponse.ErrorCode;
import DiaLogServlet.DataBaseController.SQLTableMethods.UserDataSQL;
import DiaLogServlet.ServletResponse.sendResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static DiaLogServlet.DataBaseController.ControllerServlet.AddControl.AddUser.LoginServlet.UserID;


@WebServlet(urlPatterns={"/api/post/delete/user"}, loadOnStartup=1)
public class DeleteUser extends Delete{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String servletPath = req.getServletPath();

        switch (servletPath) {
            case "/api/post/delete/user":
                delete(req, resp);
        }
    }


    @Override
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        if (UserID != 0){
            UserDataSQL.deleteUser(UserID);
            sendResponse.send(resp, ErrorCode.SUCCESS);
        }
        else {
            sendResponse.send(resp, ErrorCode.DATA_NOT_FOUND_ERROR);
        }

    }

}

