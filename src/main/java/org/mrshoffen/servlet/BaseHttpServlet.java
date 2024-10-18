package org.mrshoffen.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.utils.DependencyManager;

import java.io.IOException;

public abstract class BaseHttpServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        super.init();

        DependencyManager.getInjector().injectMembers(this);
    }


    protected final void writeJsonValueToResponse(HttpServletResponse resp, Object value) throws IOException {
        objectMapper.writeValue(resp.getWriter(),value);
    }
}
