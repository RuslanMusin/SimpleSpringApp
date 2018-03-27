package config;

import database.exceptions.DbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import utils.DbWrapper;

@Configuration
@PropertySource("classpath:db_properties/postgres.properties")
@ComponentScan({"database.dao"})
public class DataSourceConfig {

    @Autowired
    Environment env;

    @Bean
    public DbWrapper dataSource() {
        DbWrapper ds = new DbWrapper();
        try {
            ds.setConfig(env);
        } catch (DbException e) {
            System.out.println("have db config exception : " + e.getMessage());
        }
        return ds;
    }
}
