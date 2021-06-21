package eu.fbk.ict.pdi.moki.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class DirectAccessFilter
 */
@WebFilter("/DirectAccessFilter")
public class DirectAccessFilter implements Filter {

	/**
     * Default constructor. 
     */
    public DirectAccessFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
		try {
			
			String url = ((HttpServletRequest)request).getRequestURL().toString();

			if(url.toLowerCase().contains(".jsp") && url.toLowerCase().contains("/modules/")) {
				System.out.println("direct access to jsp is forbidden");
				((HttpServletResponse)response).sendRedirect(((HttpServletResponse)response).encodeRedirectURL("/moki-dompe/index.jsp"));
				return;
			}
			
			chain.doFilter(request, response);
		
		} catch (Exception e) {
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
