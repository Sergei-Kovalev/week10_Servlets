package com.gmail.kovalev.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/all")
public class WrongPageNumberFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String pageStr = request.getParameter("page");
        int page;
        try {
            page = Integer.parseInt(pageStr);
            if (page > 0) {
                chain.doFilter(request, response);
            } else {
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.sendRedirect("http://localhost:8080/Faculty/all?page=1");
            }
        } catch (NumberFormatException e) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.sendRedirect("http://localhost:8080/Faculty/all?page=1");
        }
    }
}
