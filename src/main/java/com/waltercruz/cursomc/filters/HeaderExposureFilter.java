package com.waltercruz.cursomc.filters;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*Para que meu front consiga ver o cabecalho*/
@Component
public class HeaderExposureFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("access-control-expose-headers", "location");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }


}
