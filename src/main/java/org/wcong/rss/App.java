package org.wcong.rss;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;
import org.wcong.rss.mapper.RssMapper;
import org.wcong.rss.model.Rss;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * ok start with app
 * Created by wcong on 2016/9/26.
 */
public class App {

	public static void main(String[] args) throws ServletException, LifecycleException, InterruptedException {
		Tomcat tomcat = new Tomcat();
		tomcat.getHost().setAppBase("");
		Context context = tomcat.addContext("", "");

		addGuiceFilter(context);

		tomcat.addServlet("", IndexServlet.class.getSimpleName(), IndexServlet.class.getName());
		context.addServletMapping("", IndexServlet.class.getSimpleName());

		tomcat.init();
		tomcat.start();
		tomcat.getServer().await();
	}

	private static void addGuiceFilter(Context context) {
		FilterDef filter1definition = new FilterDef();
		filter1definition.setFilterName(GuiceFilter.class.getSimpleName());
		filter1definition.setFilterClass(GuiceFilter.class.getName());
		context.addFilterDef(filter1definition);
		FilterMap filter1mapping = new FilterMap();
		filter1mapping.setFilterName(GuiceFilter.class.getSimpleName());
		filter1mapping.addURLPattern("/*");
		context.addFilterMap(filter1mapping);
		context.addApplicationListener(MyListener.class.getName());
	}

	public static class MyListener extends GuiceServletContextListener {

		protected Injector getInjector() {
			final Properties myBatisProperties = new Properties();
			myBatisProperties.setProperty("mybatis.environment.id", "test");
			myBatisProperties.setProperty("JDBC.schema", "rss");
			myBatisProperties.setProperty("derby.create", "true");
			myBatisProperties.setProperty("JDBC.username", "root");
			myBatisProperties.setProperty("JDBC.password", "");
			myBatisProperties.setProperty("JDBC.autoCommit", "true");
			return Guice.createInjector(new ServletModule() {
				@Override
				protected void configureServlets() {
					serve("*").with(MyHttpServlet.class);
				}
			}, new MyBatisModule() {
				@Override
				protected void initialize() {
					install(JdbcHelper.MySQL);
					bindDataSourceProviderType(PooledDataSourceProvider.class);
					bindTransactionFactoryType(JdbcTransactionFactory.class);
					addMapperClass(RssMapper.class);
					Names.bindProperties(binder(), myBatisProperties);
				}
			});
		}
	}

	public static class IndexServlet extends HttpServlet {
		public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			resp.setStatus(200);
			resp.getOutputStream().write("index".getBytes());
		}
	}

	@Singleton
	public static class MyHttpServlet extends HttpServlet {

		private RssMapper rssMapper;

		@Inject
		public MyHttpServlet(RssMapper rssMapper) {
			this.rssMapper = rssMapper;
		}

		public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			resp.setStatus(200);
			Rss rss = rssMapper.getById(1);
			resp.getOutputStream().write(rss.toString().getBytes());
		}

	}

}
