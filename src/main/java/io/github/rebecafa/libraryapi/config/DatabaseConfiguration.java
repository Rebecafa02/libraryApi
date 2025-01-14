package io.github.rebecafa.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

   // @Bean
   /* public DataSource dataSource() {

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);

        return ds;
    }*/

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig(); //recomendado e por padrão o spring boot ja utiliza o hikari
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);

        config.setMaximumPoolSize(10); //maximo de conexões liberadas
        config.setMinimumIdle(1); //minimo de conexões liberadas de inicio
        config.setPoolName("library-db-pool"); //nome que aparece no log
        config.setMaxLifetime(600000); //tempo maximo de uma conexão 600mil ms - 10min
        config.setConnectionTimeout(100000); //timeout para conseguir uma conexao, passando disso vai dar erro
        config.setConnectionTestQuery("select 1"); //query de teste

        return new HikariDataSource(config);
    }
}
