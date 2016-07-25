package andrey019.configuration;

//import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

//import org.hibernate.cfg.Configuration;

@org.springframework.context.annotation.Configuration
@ComponentScan("andrey019")
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

//    @Bean
//    public SessionFactory sessionFactory() {
//        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//        return sessionFactory;
//    }

//    @Bean
//    public AdvDAO advDAO() {
//        return new AdvDAOImpl();
//    }
//    @Autowired
//    private Environment environment;
    @PersistenceContext
    EntityManager entityManager;

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    public DataSource dataSource() {
        EntityManagerFactoryInfo info = (EntityManagerFactoryInfo) entityManager.getEntityManagerFactory();
        return info.getDataSource();
    }

    @Bean
    public EntityManager entityManager() {
        return entityManager;
    }

//    @Bean
//    public EntityManager entityManager() {
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAController");
//        return factory.createEntityManager();
//    }

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://127.2.153.130:3306/wunderwaffel");
//        dataSource.setUsername("adminWT8YK3d");
//        dataSource.setPassword("4CwgJKNXD34T");
//        return dataSource;
//    }

//    @Bean
//    public CustomSuccessHandler customSuccessHandler() {
//        return new CustomSuccessHandler();
//    }

//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        return new CommonsMultipartResolver();
//    }
}
