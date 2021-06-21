package eu.fbk.ict.pdi.moki.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import eu.fbk.ict.pdi.moki.requestmanager.RequestManagerRequest;
import eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage;
import eu.fbk.ict.pdi.moki.utils.Config;
import eu.fbk.ict.pdi.moki.utils.Util;

/**
 * Servlet Filter implementation class RequestFilter
 */
@WebFilter("/RequestFilter")
public class RequestFilter implements Filter {

    /**
     * Default constructor. 
     */
    public RequestFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		
		try {
			StringBuffer jb = new StringBuffer();
			String line = null;
			try {
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null)
				jb.append(line);
			} catch (Exception e) { 
				e.printStackTrace(); 
			}
			  
			Gson gson = new Gson();
			RequestManagerRequest rc = gson.fromJson(jb.toString(), RequestManagerRequest.class);
			
			request.setAttribute("rc", rc);
			
			String [] user_role;
			
			if(session != null && session.getAttribute("email") != null) 
				user_role = (String []) session.getAttribute("role");
			else {
				user_role = new String [1];
				user_role[0] = "not_logged";
			}
			
			//to-do da spostare e rendere statica
			HashMap<String, String []> white_list = Config.request_white_list;
						
			
			if(white_list.get(rc.getServiceName()) != null) {
				
				//I can access only if my role is in the role list of that page or if i'm logged and the service permission is "all"
				if(!( Util.commonInArrays(white_list.get(rc.getServiceName()), user_role) || ( !user_role[0].equals("not_logged") && (white_list.get(rc.getServiceName()))[0].equals("logged") ))) {
					System.out.println("access denied - bad role");
					((HttpServletResponse)response).sendRedirect(((HttpServletResponse)response).encodeRedirectURL("index.jsp"));
					return;
				}
			}
			
				chain.doFilter(request, response);
		
		} catch (Exception e) {
			e.printStackTrace();
			ReturnMessage res = new ReturnMessage();
			res.setName_service("index.jsp");
			res.setType_service("");
			res.setMessage_text("An error occured.");
			session.setAttribute("returnmessage", res);
            //System.out.println(prodotto.size());  
            RequestDispatcher d1 = request.getRequestDispatcher("/moki-dompe/index.jsp?page=error&module=common");
            try {
				d1.forward(request, response);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
