package andrey019.configuration;


import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;


public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class };
	}
 
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}
 
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		WebApplicationContext context = getContext();
		servletContext.addListener(new ContextLoaderListener(context));
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/*");

	}

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("andrey019.configuration");
		return context;
	}


//	@Override
//	protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
////		characterEncodingFilter.setEncoding("UTF-8");
////		characterEncodingFilter.setForceEncoding(true);
//		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", characterEncodingFilter);
//		encodingFilter.setInitParameter("encoding", "UTF-8");
//		encodingFilter.setInitParameter("forceEncoding", "true");
//		encodingFilter.addMappingForUrlPatterns(null, false, "/*");
//		return encodingFilter;
//	}

//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true);
//		servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
////		encodingFilter.setInitParameter("encoding", "UTF-8");
////		encodingFilter.setInitParameter("forceEncoding", "true");
////		encodingFilter.addMappingForUrlPatterns(null, false, "/*");
//	}

//	@Override
//	protected Filter[] getServletFilters() {
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true);
//
//		return new Filter[] {characterEncodingFilter};
//	}

}
