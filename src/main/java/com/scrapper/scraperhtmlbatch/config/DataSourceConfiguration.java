    package com.scrapper.scraperhtmlbatch.config;

    import jakarta.persistence.EntityManagerFactory;
    import org.hibernate.SessionFactory;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.boot.context.properties.EnableConfigurationProperties;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.datasource.DriverManagerDataSource;
    import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
    import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
    import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

    import javax.sql.DataSource;
    import java.util.Properties;

    @Configuration
    @EnableConfigurationProperties
    public class DataSourceConfiguration {

        @Value("${spring.datasource.url}")
        private String datasourceUrl;

        @Value("${spring.datasource.username}")
        private String datasourceUsername;

        @Value("${spring.datasource.password}")
        private String datasourcePassword;
        @Value("${spring.datasource.driver-class-name}")
        private String driverClassName;


        @Value("${spring.datasource.schema}")
        private String schema;


        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(datasourceUrl);
            dataSource.setUsername(datasourceUsername);
            dataSource.setPassword(datasourcePassword);
            dataSource.setSchema(schema);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean("entityManagerFactory")
        public SessionFactory sessionFactory() throws Exception {
            LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource());
            sessionFactoryBean.setPackagesToScan("com.scrapper.scraperhtmlbatch.models"); // Set your entity package here

            sessionFactoryBean.afterPropertiesSet();
            return sessionFactoryBean.getObject();
        }



    }
