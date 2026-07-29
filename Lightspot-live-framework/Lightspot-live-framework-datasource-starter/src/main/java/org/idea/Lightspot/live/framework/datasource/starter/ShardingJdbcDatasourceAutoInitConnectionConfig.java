package org.idea.Lightspot.live.framework.datasource.starter;

import java.sql.Connection;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShardingJdbcDatasourceAutoInitConnectionConfig {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ShardingJdbcDatasourceAutoInitConnectionConfig.class);

    @Bean
    public ApplicationRunner runner(ObjectProvider<DataSource> dataSourceProvider) {
        return args -> {
            DataSource dataSource = dataSourceProvider.getIfAvailable();
            if (dataSource == null) {
                LOGGER.info("================== [ShardingJdbcDatasourceAutoInitConnectionConfig] dataSource not found");
                return;
            }
            LOGGER.info("================== [ShardingJdbcDatasourceAutoInitConnectionConfig] dataSource: {}", dataSource);
            try (Connection connection = dataSource.getConnection()) {
                LOGGER.info("================== [ShardingJdbcDatasourceAutoInitConnectionConfig] connection: {}", connection);
            }
        };
    }
}
