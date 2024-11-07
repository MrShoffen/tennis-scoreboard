package org.mrshoffen.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.utils.JspHelper;

import java.io.IOException;


@WebFilter(urlPatterns = {"/matches", "/players",
        "/new-match", "/match-score", "/match"})
public class JspDispatcherFilter extends HttpFilter {


    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String page = req.getRequestURI().substring(req.getContextPath().length()+1);

        req.getRequestDispatcher(JspHelper.getPath(page)).forward(req, res);
    }
}
