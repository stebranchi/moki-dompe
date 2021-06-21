package eu.fbk.ict.pdi.moki.requestmanager;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import com.google.gson.*;

import eu.fbk.ict.pdi.moki.utils.Config;
import eu.fbk.ict.pdi.moki.utils.DBLayer;
import eu.fbk.ict.pdi.moki.interfaces.Service;


/**
 * Servlet implementation class RequestManager
 */
@WebServlet("/RequestManager")
@MultipartConfig
public class RequestManager extends HttpServlet {
	HttpSolrClient client;
	private static final long serialVersionUID = 1L;
    Properties properties = new Properties();

    /**
     * @throws IOException 
     * @see HttpServlet#HttpServlet()
     */
    public RequestManager() throws IOException, SolrServerException {
        super();
        this.properties.load(getClass().getResourceAsStream("/config.properties"));
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("non accetto chiamate get");
		response.getWriter().append("ok");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		Gson gson = new Gson();
		ReturnMessage res;
		HttpSession session = request.getSession(false);
		//session.setAttribute("solr", this.client);
		
		RequestManagerRequest rc = (RequestManagerRequest) request.getAttribute("rc");
		
		System.out.println("JSON in entrata: \n" + gson.toJson(rc) + "\n\n");
		
		try {			
			Service service = (Service) Class.forName("eu.fbk.ict.pdi.moki." + rc.getLocationService() + "." + rc.getServiceName()).getDeclaredConstructor().newInstance();
			
			((Service) service).init(gson.toJson(rc.getData()), session);
			
			res = ((Service) service).run();
			
		} catch (Exception e) {
			//throws error if the service doesn't exist
			res = new ReturnMessage();
			res.setName_service(rc.getServiceName());
			res.setType_service(rc.getLocationService());
			res.setMessage_text("Error. Service not found");
		}
		
		//get the current datatime 
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		
		//insert json log in the database
		if (rc.getServiceName().equals("Upload")) {
			rc.setData("");
		}
		try {
			   DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
			   DBLayer.SQL("INSERT INTO log VALUES (DEFAULT, ?, ?)", currentTime, gson.toJson(rc, RequestManagerRequest.class));
			   
		} catch(Exception e) {
			   System.err.println("errore nell'inserimento del log");
		}
		
		//check if all is ok
		if(!res.getMessage_text().equals("ok")) {
			session.setAttribute("returnmessage", res);
            //System.out.println(prodotto.size());  
            RequestDispatcher d1 = request.getRequestDispatcher("/moki-dompe/index.jsp?page=error&module=common");
            d1.forward(request, response);
			return;
		}
		
		if (res.getName_service() == "ListManager") {
			System.out.println("JSON in uscita con SOP trovate");
		}
		else {
		System.out.println("JSON in uscita: \n" + gson.toJson(res) + "\n\n");
		}
		response.getWriter().append(gson.toJson(res));
		
	}
	
}
