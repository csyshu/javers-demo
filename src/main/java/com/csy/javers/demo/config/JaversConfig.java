package com.csy.javers.demo.config;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.repository.sql.ConnectionProvider;
import org.javers.repository.sql.DialectName;
import org.javers.repository.sql.JaversSqlRepository;
import org.javers.repository.sql.SqlRepositoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author shuyun.cheng
 * @version 1.0
 * @desc
 * @date 2022-06-10 16:01
 */
@Configuration
public class JaversConfig {

    @Bean
    public Javers getJavers() throws SQLException {
        final Connection dbConnection = DriverManager.getConnection("jdbc:mysql://192.168.162.108:3306/sigma_lab?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true",
                "sigma_lab", "sigma_lab.1206..rL");
        ConnectionProvider connectionProvider = () -> {
            //suitable only for testing!
            dbConnection.setAutoCommit(true);
            return dbConnection;
        };
        JaversSqlRepository sqlRepository = SqlRepositoryBuilder.sqlRepository().withSchemaManagementEnabled(false)
                //optionally, provide the schame name
                .withConnectionProvider(connectionProvider)
                .withDialect(DialectName.MYSQL).build();
        return JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE).registerJaversRepository(sqlRepository).build();
    }
}
