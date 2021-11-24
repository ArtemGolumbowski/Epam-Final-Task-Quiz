
package dbmanager;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;



/**
 *
 * @author agolu
 */
public class ConnectionPool {

    BasicDataSource ds;

    private ConnectionPool() {
        ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/quiz_schema?zeroDateTimeBehavior=CONVERT_TO_NULL");
        ds.setUsername("root");
        ds.setPassword("a1233218619a");
        
    }

    private static ConnectionPool instance = null;

    /**
     * Create instance of ConnectionPool
     * @return instance of ConnectionPool
     */
    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    /**
     * Get Connection to database
     * @return Connection
     */
    public Connection getConnection() {
        Connection c = null;
        try {
            c = ds.getConnection();
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionPool.class).error(ex);
        }
        return c;
    }

    /**
     * Close datasource
     */
    public void close(){
        try {
            if(ds!=null){
            ds.close();
            Logger.getLogger(ConnectionPool.class).info("datasource закрыт");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionPool.class).error(ex);
        }
    }

   
}
