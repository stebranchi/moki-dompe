package eu.fbk.ict.pdi.moki.filters;

import java.util.HashMap;

import eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage;
import eu.fbk.ict.pdi.moki.utils.Config;
import eu.fbk.ict.pdi.moki.utils.Util;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class IndexFilter
 */
@WebFilter("/IndexFilter")
public class IndexFilter implements Filter {

    /**
     * Default constructor. 
     */
    public IndexFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		
		try {
			String page = request.getParameter("page");
			//String module = request.getParameter("module");
			
			String [] user_role;
			
			if(session != null && session.getAttribute("email") != null) 
				user_role = (String []) session.getAttribute("role");
			else {
				user_role = new String [1];
				user_role[0] = "not_logged";
			}
			
			HashMap<String, String []> white_list = Config.index_white_list;
			
			if(white_list.get(page) != null) {
				
				//I can access only if my role is in the role list of that page or if i'm logged and the service permission is "all"
				if(!( Util.commonInArrays(white_list.get(page), user_role) || ( !user_role[0].equals("not_logged") && (white_list.get(page))[0].equals("logged") ))) {
					System.out.println("access denied - bad role");
					((HttpServletResponse)response).sendRedirect(((HttpServletResponse)response).encodeRedirectURL("/moki-dompe/index.jsp"));
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
	}

}
