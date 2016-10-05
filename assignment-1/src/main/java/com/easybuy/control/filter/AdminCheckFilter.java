package com.easybuy.control.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easybuy.control.Constants;

/**
 * Filter checks if the current user is admin, if not redirect to access-denied page. This filter is bond to /manage/*.
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 4, 2016
 *
 */
public class AdminCheckFilter implements Filter {

    protected String encoding = null;


    @Override
    public void destroy() {
        this.encoding = null;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        // first check admin privilege
        Boolean isAdmin = (Boolean) ((HttpServletRequest) request).getSession().getAttribute(
                Constants.SESS_ATTR_NAME_IS_ADMIN);
        if (null == isAdmin || !isAdmin.booleanValue()) {
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getSession()
                    .getServletContext().getContextPath()
                    + "/" + Constants.PAGE_ACCESS_DENIED);
            return;
        }
        chain.doFilter(request, response);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}
