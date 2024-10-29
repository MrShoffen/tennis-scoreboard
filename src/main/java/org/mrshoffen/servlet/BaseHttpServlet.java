package org.mrshoffen.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.dto.request.PageRequestDto;
import org.mrshoffen.utils.DependencyManager;

import java.io.IOException;

public abstract class BaseHttpServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final static int DEFAULT_PAGE_NUMBER = 1;
    private final static int DEFAULT_PAGE_SIZE = 10;

    @Override
    public final void init() throws ServletException {
        super.init();

        DependencyManager.getInjector().injectMembers(this);
    }


    protected final void writeJsonValueToResponse(HttpServletResponse resp, Object value) throws IOException {
        objectMapper.writeValue(resp.getWriter(), value);
    }


    protected PageRequestDto extractPageRequestDto(HttpServletRequest req) {
        return PageRequestDto.builder()
                .pageNumber(parsePageNumber(req.getParameter("page_number")))
                .pageSize(parsePageSize(req.getParameter("page_size")))
                .playerName(req.getParameter("player_name"))
                .build();
    }

    private int parsePageNumber(String pageNumber) {
        return pageNumber == null || pageNumber.isBlank()
                ? DEFAULT_PAGE_NUMBER
                : Integer.parseInt(pageNumber.trim());
    }

    private int parsePageSize(String pageSize) {
        return pageSize == null || pageSize.isBlank()
                ? DEFAULT_PAGE_SIZE
                : Integer.parseInt(pageSize.trim());
    }
}
