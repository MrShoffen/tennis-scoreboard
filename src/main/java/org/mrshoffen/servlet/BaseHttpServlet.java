package org.mrshoffen.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import org.mrshoffen.utils.DependencyManager;

public abstract class BaseHttpServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        super.init();

        DependencyManager.getInjector().injectMembers(this);
    }
}
