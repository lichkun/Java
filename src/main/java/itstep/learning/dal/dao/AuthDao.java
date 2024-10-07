package itstep.learning.dal.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.services.db.DbService;
import itstep.learning.services.kdf.KdfService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

@Singleton
public class AuthDao {
    private final DbService dbService;
    private final Logger logger;
    private final KdfService kdfService;

    @Inject
    public AuthDao(DbService dbService, Logger logger, KdfService kdfService) {
        this.dbService = dbService;
        this.logger = logger;
        this.kdfService = kdfService;
    }

    public boolean install(){
        String sql= "CREATE TABLE IF NOT EXISTS `users` ("+
                "`user_id`     CHAR(36) PRIMARY KEY DEFAULT ( UUID() )," +
                "`user_name`   VARCHAR(64)   NOT NULL, "+
                "`email`       VARCHAR(128)  NOT NULL, "+
                "`phone`       VARCHAR(16)       NULL, "+
                "`avatar_url`  VARCHAR(16)       NULL, "+
                "`birthdate`   DATETIME      NOT NULL, "+
                "`delete_dt`   DATETIME          NULL "+
                ")ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;";
        try(Statement stmt = dbService.getConnection().createStatement()){
            stmt.executeUpdate(sql);
        }
        catch(SQLException ex){
            logger.warning(ex.getMessage() + " -- " + sql);
            return false;
        }

        sql= "CREATE TABLE IF NOT EXISTS `users_access` ("+
                "`access_id`  CHAR(36) PRIMARY KEY DEFAULT ( UUID() )," +
                "`user_id`    VARCHAR(36)   NOT NULL, "+
                "`login`      VARCHAR(32)   NOT NULL, "+
                "`salt`       CHAR(16)          NULL, "+
                "`dk`         CHAR(20)          NULL, "+
                "`role_id`    CHAR(36)      NOT NULL, "+
                "`is_active`  TINYINT       DEFAULT 1 "+
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        try(Statement stmt = dbService.getConnection().createStatement()){
            stmt.executeUpdate(sql);
        }
        catch(SQLException ex){
            logger.warning(ex.getMessage() + " -- " + sql);
            return false;
        }

        sql= "CREATE TABLE IF NOT EXISTS `users_roles` ("+
                "`role_id`       CHAR(36)    PRIMARY KEY DEFAULT ( UUID() )," +
                "`role_name`     VARCHAR(36) NOT NULL, "+
                "`can_create`    TINYINT     DEFAULT 0, "+
                "`can_read`      TINYINT     DEFAULT 1, "+
                "`cac_update`    TINYINT     DEFAULT 0, "+
                "`can_delete`    TINYINT     DEFAULT 0 "+
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        try(Statement stmt = dbService.getConnection().createStatement()){
            stmt.executeUpdate(sql);
        }
        catch(SQLException ex){
            logger.warning(ex.getMessage() + " -- " + sql);
            return false;
        }

        sql= "CREATE TABLE IF NOT EXISTS `tokens` ("+
                "`token_id`  CHAR(36)     PRIMARY KEY DEFAULT ( UUID() )," +
                "`user_id`   CHAR(36)     NOT NULL, "+
                "`iat`       DATETIME     DEFAULT CURRENT_TIMESTAMP, "+
                "`exp`       DATETIME     NOT  NULL "+
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        try(Statement stmt = dbService.getConnection().createStatement()){
            stmt.executeUpdate(sql);
        }
        catch(SQLException ex){
            logger.warning(ex.getMessage() + " -- " + sql);
            return false;
        }

        sql = "CREATE TABLE IF NOT EXISTS `access_log` (" +
                "`log_id` CHAR(36) PRIMARY KEY DEFAULT (UUID()), " +
                "`user_id` CHAR(36) NOT NULL, " +
                "`access_time` DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "`page` VARCHAR(255) NOT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;";

        try (Statement stmt = dbService.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            logger.warning(ex.getMessage() + " -- " + sql);
            return false;
        }


        //Seed - заполнение начальных данных/ возврат заводских настроек
        sql = "INSERT INTO `users_roles`(`role_id`,`role_name`,`can_create`,`can_read`,`cac_update`,`can_delete`) " +
                "VALUES ('81661d9f-815d-11ef-bb48-fcfbf6dd7098','Administrator',1,1,1,1) " +
                "ON DUPLICATE KEY UPDATE " +
                "`role_name` = 'Administrator', " +
                "`can_create` = 1, `can_read` = 1,`cac_update` = 1,`can_delete` = 1";

        try(Statement stmt = dbService.getConnection().createStatement()){
            stmt.executeUpdate(sql);
        }
        catch(SQLException ex){
            logger.warning(ex.getMessage() + " -- " + sql);
            return false;
        }

        sql = "INSERT INTO `users`(`user_id` ,`user_name`,`email`,`birthdate`,`delete_dt`) " +
                "VALUES ('7dd7d8a9-815e-11ef-bb48-fcfbf6dd7098','Administrator','admin@change.me','1970-01-01',NULL) " +
                "ON DUPLICATE KEY UPDATE " +
                "`user_name` = 'Administrator', " +
                "`email` = 'admin@change.me'," +
                " `birthdate` = '1970-01-01'," +
                "`delete_dt` = NULL ";

        try(Statement stmt = dbService.getConnection().createStatement()){
            stmt.executeUpdate(sql);
        }
        catch(SQLException ex){
            logger.warning(ex.getMessage() + " -- " + sql);
            return false;
        }

        String salt = "bb48fcfbf6dd70.4";
        String password = "root";
        String dk= kdfService.dk(password,salt);

        sql = "INSERT INTO `users_access`(`access_id`,`user_id` ,`role_id`,`login`,`salt`,`dk`, `is_active`) " +
                "VALUES (" +
                "'5392cdda-815f-11ef-bb48-fcfbf6dd7098'," +
                "'7dd7d8a9-815e-11ef-bb48-fcfbf6dd7098'," +
                "'81661d9f-815d-11ef-bb48-fcfbf6dd7098'," +
                "'admin',?,?,1) " +
                "ON DUPLICATE KEY UPDATE " +
                "`login` = 'admin', " +
                "`salt` = ?," +
                " `dk` = ?," +
                "`is_active` = 1 ";

        try(PreparedStatement prep= dbService.getConnection().prepareStatement(sql)){
            prep.setString(1,salt);
            prep.setString(2,dk);
            prep.setString(3,salt);
            prep.setString(4,dk);
            prep.executeUpdate();
        }
        catch(SQLException ex){
            logger.warning(ex.getMessage() + " -- " + sql);
            return false;
        }

        sql = "INSERT INTO `access_log` (`user_id`, `page`) " +
                "VALUES ('7dd7d8a9-815e-11ef-bb48-fcfbf6dd7098', 'home')";
        try(Statement stmt = dbService.getConnection().createStatement()){
            stmt.executeUpdate(sql);
        }
        catch(SQLException ex){
            logger.warning(ex.getMessage() + " -- " + sql);
            return false;
        }


        return true;

    }
}
/*
DAO - Data Access Object
набор инструментов (бизнес-логика) для работы с
DTO -Data Transfer Object (Entities) - моделями
передачи данных

Задачи авторизации/Аутентификации
|[users]     [users_access]   [users_roles]           |[tokens]
|user_id     |access_id       |role_id                |token_id
|name        |user_id         |role_name              |user_id
|email       |login           |can_create             |iat
|phone       |salt            |can_read               |exp
|avatar_url  |dk              |cac_update             |
|birthdate   |is_active
|delete_dt   |role_id         |can_delete             |
 */
