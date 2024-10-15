package itstep.learning.services.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDbService implements DbService {
    private Connection connection;
    @Override
    public Connection getConnection() throws SQLException {
        if(connection == null) {
            //процесс подключения: регистрируем драйвер СУБД(MySQL)
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            //формируем строку
            String connectionUrl = "jdbc:mysql://localhost:3308/java_kn_p_213"+
                    "?useUnicode=true&characterEncoding=utf8";
            String username = "root";
            String password = "root";
            //получаем подключение
            connection = DriverManager.getConnection(connectionUrl, username, password);
        }
        return connection;
    }
}
/*
JOBC -Java database Connectivity - технология доступа к данным, аналогично к ADO.NET
Дает общий интерфейс для работы с разными источниками данных (СУБД)
 */
