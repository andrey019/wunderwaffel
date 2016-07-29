package andrey019.configuration;

//import org.hibernate.SessionFactory;
import andrey019.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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

//    @PersistenceContext(name = "JPAController")
//    EntityManager entityManager;

//    private static final Charset UTF8 = Charset.forName("UTF-8");
//
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        //stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", UTF8)));
        converters.add(stringConverter);
    }

    @PostConstruct
    private void mailSenderServiceInit() {
        MailSenderService.getInstance().start();
    }

    @Bean
    public MailSenderService getMailSenderService() {
        return MailSenderService.getInstance();
    }

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mail.ru");
        mailSender.setPort(465);
        mailSender.setUsername("wunderwaffelapp@mail.ru");
        mailSender.setPassword("90gdfg70gsds897");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtps");
        javaMailProperties.put("mail.debug", "false");

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        resolver.setOrder(1);
        //resolver.setContentType("text/html;charset=UTF-8");
        return resolver;
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }


//    @Bean
//    public MessageSource messageSource() {
//
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasenames("classpath:messages/messages", "classpath:messages/validation");
//        // if true, the key of the message will be displayed if the key is not
//        // found, instead of throwing a NoSuchMessageException
//        messageSource.setUseCodeAsDefaultMessage(true);
//        messageSource.setDefaultEncoding("UTF-8");
//        // # -1 : never reload, 0 always reload
//        messageSource.setCacheSeconds(0);
//        return messageSource;
//    }

//    @Bean
//    public CharacterEncodingFilter characterEncodingFilter() {
//        CharacterEncodingFilter filter = new CharacterEncodingFilter();
//        filter.setEncoding("UTF-8");
//        filter.setForceEncoding(true);
//
//
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(filter);
//        registrationBean.addUrlPatterns("/*");
//        return filter;
//    }

//    @Bean
//    public DataSource dataSource() {
//        EntityManagerFactoryInfo info = (EntityManagerFactoryInfo) entityManager().getEntityManagerFactory();
//        return info.getDataSource();
//    }

//    @Bean
//    public EntityManager entityManager() {
//        return entityManager;
//    }

//    @Bean
//    public EntityManager entityManager() {
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAController");
//        return factory.createEntityManager();
//    }

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://127.2.153.130:3306/wunderwaffel?autoReconnect=true");
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
