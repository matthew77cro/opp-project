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

import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Constants;
import hr.fer.opp.bugbusters.dao.sql.SQLConnectionProvider;

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
		
		// CHECKING IF TABLES EXIST AND CREATING THEM IF THEY DO NOT
		
		Connection con = null;
		try {
			con = cpds.getConnection();
			SQLConnectionProvider.setConnection(con);
			if(createTables(sce)) {
				initializeTypes();
				populateTablesInitialValues();
			}
		} catch (IOException | SQLException e) {
			throw new RuntimeException(e.getClass().getName() + " : " + e.getMessage());
		} finally {
			SQLConnectionProvider.setConnection(null);
			try { con.close(); } catch (Exception ignorable) {}
		}
		
	}

	private boolean createTables(ServletContextEvent sce) throws IOException, SQLException {
		
		Connection con = SQLConnectionProvider.getConnection();
		
		if(con.getMetaData().getTables(null, null, "profil", null).next())
			return false;
		
		Statement st = con.createStatement();
		
		Path bpInit = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/bazapodataka.sql"));
		String lines = Files.readString(bpInit);
		String sql[] = lines.split(";");
		
		for(String statement : sql) {
			if(statement.isBlank()) continue;
			String edited = statement.trim().replaceAll("\\s+", " ") + ";";
			if(edited.startsWith("--")) continue; // It is a comment, do not execute it!
			st.execute(edited);
		}
		
		st.close();
		
		return true;
		
	}
	
	private void initializeTypes() {
		
		var dao = DAOProvider.getDao();
		
		dao.addRazinaOvlasti(Constants.administrator);
		dao.addRazinaOvlasti(Constants.bankar);
		dao.addRazinaOvlasti(Constants.sluzbenikZaKredite);
		dao.addRazinaOvlasti(Constants.klijent);
		
		dao.addVrstaRacuna(Constants.tekuci);
		dao.addVrstaRacuna(Constants.ziro);
		dao.addVrstaRacuna(Constants.stedni);
		
		dao.addVrstaKredita(Constants.stambeni);
		dao.addVrstaKredita(Constants.namjenski);
		dao.addVrstaKredita(Constants.nenamjenski);
		
		dao.addVrstaKartice(Constants.amex);
		dao.addVrstaKartice(Constants.diners);
		dao.addVrstaKartice(Constants.discover);
		dao.addVrstaKartice(Constants.mastercard);
		dao.addVrstaKartice(Constants.visa);
		
	}
	
	private void populateTablesInitialValues() throws SQLException {
		
		Connection con = SQLConnectionProvider.getConnection();
		
		Statement st = con.createStatement();
		
		String sql[] = Constants.initialValues.split(";");
		
		for(String statement : sql) {
			if(statement.isBlank()) continue;
			String edited = statement.trim().replaceAll("\\s+", " ") + ";";
			if(edited.startsWith("--")) continue; // It is a comment, do not execute it!
			st.execute(edited);
		}
		
		st.close();
		
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