USE Lightspot_live_user;

DROP PROCEDURE IF EXISTS alter_t_user_100_charset;

DELIMITER $$

CREATE PROCEDURE alter_t_user_100_charset()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE table_name VARCHAR(30);
    DECLARE sql_text VARCHAR(1000);

    WHILE i < 100 DO
        IF i < 10 THEN
            SET table_name = CONCAT('t_user_0', i);
        ELSE
            SET table_name = CONCAT('t_user_', i);
        END IF;

        SET sql_text = CONCAT(
            'ALTER TABLE ',
            table_name,
            ' CONVERT TO CHARACTER SET utf8mb3 COLLATE utf8mb3_bin'
        );
        SET @sql_text = sql_text;
        PREPARE stmt FROM @sql_text;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

CALL alter_t_user_100_charset();

SELECT table_collation, COUNT(*) AS table_count
FROM information_schema.tables
WHERE table_schema = 'Lightspot_live_user'
  AND table_name LIKE 't_user_%'
GROUP BY table_collation;
