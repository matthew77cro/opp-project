package hr.fer.opp.bugbusters.servleti.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import hr.fer.opp.bugbusters.control.LoginHandler;
import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Constants;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.Transakcija;
import hr.fer.opp.bugbusters.dao.model.VrstaRacuna;

@SuppressWarnings("serial")
@WebServlet(name="racuni", urlPatterns= {"/banka/racuni"})
public class RacuniServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.klijent)) {
			resp.sendRedirect("login");
			return;
		}
		
		if(LoginHandler.needsPasswordChange(req, resp)) {
			resp.sendRedirect("passwordchange");
			return;
		}
		
		String oib = DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib();
		List<Racun> racuni = DAOProvider.getDao().getRacunByOib(oib);
		Map<Integer, VrstaRacuna> vrsteRacuna = new HashMap<>();
		
		Map<Racun, VrstaRacuna> racunJsp = new HashMap<>();
		for(var racun : racuni) {
			VrstaRacuna vrsta = vrsteRacuna.getOrDefault(racun.getSifVrsteRacuna(), DAOProvider.getDao().getVrstaRacuna(racun.getSifVrsteRacuna()));
			vrsteRacuna.put(vrsta.getSifVrsteRacuna(), vrsta);
			
			racunJsp.put(racun, vrsta);
		}
		
		req.setAttribute("racuni", racunJsp);
		
		req.getRequestDispatcher("/WEB-INF/pages/client/account.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.klijent)) {
			resp.sendRedirect("login");
			return;
		}
		
		if(LoginHandler.needsPasswordChange(req, resp)) {
			resp.sendRedirect("passwordchange");
			return;
		}
		
		String action = req.getParameter("action");
		if(action==null) {
			req.setAttribute("errorMsg", "Request error - no action");
			doGet(req, resp);
			return;
		}
		
		switch(action) {
			case "downloadPDF" :
				String brojRacuna = req.getParameter("brojRacuna");
				if(brojRacuna==null || brojRacuna.isEmpty()) {
					req.setAttribute("errorMsg", "Request error - missing parameters");
					break;
				}
				
				resp.setContentType("application/pdf");
				resp.setHeader("Content-Disposition", "attachment; filename=\"Izvod " + brojRacuna + ".pdf\"");
				
				try {
					Document document = new Document();
					PdfWriter.getInstance(document, resp.getOutputStream());
			        document.open();
			        
			        PdfPTable table = new PdfPTable(5);
			        table.setWidthPercentage(105);
			        
			        table.addCell("Broj transakcije");
			        table.addCell("Racun terecenja");
			        table.addCell("Racun odobrenja");
			        table.addCell("Iznos");
			        table.addCell("Datum transakcije");
			        
			        for(Transakcija t : DAOProvider.getDao().getTransakcijaByBrRacun(brojRacuna)) {
						table.addCell(t.getBrTransakcija().toString());
						table.addCell(t.getRacTerecenja());
						table.addCell(t.getRacOdobrenja());
						table.addCell(t.getIznos().toString());
						table.addCell(t.getDatTransakcije().toString());
					}
			        
					
						document.add(table);
					
			        document.close();
				} catch (DocumentException e) {
					req.setAttribute("errorMsg", "Server side error! Try again.");
					doGet(req, resp);
					break;
				}
				
				break;
				
			case "downloadXLS" :
				brojRacuna = req.getParameter("brojRacuna");
				if(brojRacuna==null || brojRacuna.isEmpty()) {
					req.setAttribute("errorMsg", "Request error - missing parameters");
					break;
				}
				
				HSSFWorkbook hwb = new HSSFWorkbook();
				HSSFSheet sheet = hwb.createSheet(brojRacuna);
				
				HSSFRow rowhead = sheet.createRow(0);
				rowhead.createCell(0).setCellValue("Broj transakcije");
				rowhead.createCell(1).setCellValue("Račun terećenja");
				rowhead.createCell(2).setCellValue("Račun odobrenja");
				rowhead.createCell(3).setCellValue("Iznos");
				rowhead.createCell(4).setCellValue("Datum transakcije");
				
				int i = 1;
				for(Transakcija t : DAOProvider.getDao().getTransakcijaByBrRacun(brojRacuna)) {
					rowhead = sheet.createRow(i);
					rowhead.createCell(0).setCellValue(t.getBrTransakcija());
					rowhead.createCell(1).setCellValue(t.getRacTerecenja());
					rowhead.createCell(2).setCellValue(t.getRacOdobrenja());
					rowhead.createCell(3).setCellValue(t.getIznos().toString());
					rowhead.createCell(4).setCellValue(t.getDatTransakcije().toString());
					i++;
				}
				
				resp.setContentType("application/vnd.ms-excel");
				resp.setHeader("Content-Disposition", "attachment; filename=\"Izvod " + brojRacuna + ".xls\"");
				
				hwb.write(resp.getOutputStream());
				hwb.close();
				
				break;
				
			default :
				req.setAttribute("errorMsg", "Request error - invalid action");
		}
		
	}

}
