package hr.fer.opp.bugbusters;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

@WebListener
public class Initialisation implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Properties prop = new Properties();
		
		Path propFilePath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties"));
		try (InputStream in = Files.newInputStream(propFilePath)) {
			prop.load(in);
		} catch (IOException ex) {
			throw new RuntimeException("error loading properties file! : " + ex.getMessage());
		}
		
		String host = prop.getProperty("host");
		String port = prop.getProperty("port");
		String dbName = prop.getProperty("name");
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");
		
		String connectionURL = "jdbc:postgresql://" + host + ":" + port + "/" + dbName + 
								"?user=" + user + "&password=" + password + "&ssl=false";

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.postgresql.Driver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.opp.bugbusters.dbpool", cpds);
		
		//CHECKING IF TABLES EXIST AND CREATING THEM IF THEY DO NOT
		try {
			createTables(cpds, sce);
		} catch (IOException | SQLException e) {
			throw new RuntimeException(e.getClass().getName() + " : " + e.getMessage());
		}
		
	}

	private void createTables(ComboPooledDataSource cpds, ServletContextEvent sce) throws IOException, SQLException {
		
		Connection con = null;
		con = cpds.getConnection();
		
		if(con.getMetaData().getTables(null, null, "profil", null).next()) {
			con.close();
			return;
		}
		
		Statement st = con.createStatement();
		
		Path bpInit = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/bazapodataka.sql"));
		String lines = Files.readString(bpInit);
		String sql[] = lines.split(";");
		
		for(String statement : sql) {
			String edited = statement.trim().replaceAll("\\s+", " ") + ";";
			if(edited.startsWith("--")) continue; // It is a comment, do not execute it!
			st.execute(edited);
		}
		
		st.close();
		con.close();
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.opp.bugbusters.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}