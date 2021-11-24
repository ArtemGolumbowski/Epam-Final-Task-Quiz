
package listeners;
import org.apache.log4j.Logger;
import dbmanager.ConnectionPool;
import dbmanager.SubjectDao;
import dbmanager.UserDAO;

import entity.Subject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author agolu
 */
public class ServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger.getLogger(ServletListener.class.getName()).info("приложение запускается");
        try(Connection con = ConnectionPool.getInstance().getConnection()){
        SubjectDao subjectDao = new SubjectDao(con);
        UserDAO userDao = new UserDAO(con);
        List<Subject>subjects = subjectDao.readAll();
        Map<Integer,Boolean> bannedList = userDao.getBannedList();
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("subjects", subjects);
        sc.setAttribute("bannedList", bannedList);
        } catch (SQLException ex) {
            Logger.getLogger(ServletListener.class.getName()).error(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Logger.getLogger(ServletListener.class.getName()).info("приложение закрывается");
        ConnectionPool.getInstance().close();
    }
}
