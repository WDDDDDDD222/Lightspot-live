CREATE DATABASE IF NOT EXISTS Lightspot_live_user CHARACTER SET utf8mb3 COLLATE utf8mb3_bin;

USE Lightspot_live_user;

DROP PROCEDURE IF EXISTS create_t_user_100;

DELIMITER $$

CREATE PROCEDURE create_t_user_100()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE table_name VARCHAR(30);
    DECLARE sql_text VARCHAR(3000);
    DECLARE table_body VARCHAR(2000);

    SET table_body = '(
  user_id bigint NOT NULL DEFAULT -1 COMMENT ''user id'',
  nick_name varchar(35) DEFAULT NULL COMMENT ''nick name'',
  avatar varchar(255) DEFAULT NULL COMMENT ''avatar'',
  true_name varchar(20) DEFAULT NULL COMMENT ''true name'',
  sex tinyint(1) DEFAULT NULL COMMENT ''sex: 0 male, 1 female'',
  born_date datetime DEFAULT NULL COMMENT ''born date'',
  work_city int(9) DEFAULT NULL COMMENT ''work city'',
  born_city int(9) DEFAULT NULL COMMENT ''born city'',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin';

    WHILE i < 100 DO
        IF i < 10 THEN
            SET table_name = CONCAT('t_user_0', i);
        ELSE
            SET table_name = CONCAT('t_user_', i);
        END IF;

        SET sql_text = CONCAT('CREATE TABLE IF NOT EXISTS ', table_name, ' ', table_body);
        SET @sql_text = sql_text;
        PREPARE stmt FROM @sql_text;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

CALL create_t_user_100();

DROP PROCEDURE IF EXISTS create_t_user_100;

SHOW TABLES;
