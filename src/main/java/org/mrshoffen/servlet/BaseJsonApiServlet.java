package org.mrshoffen.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.dto.request.PageRequestDto;
import org.mrshoffen.exception.ValidationException;
import org.mrshoffen.utils.DependencyManager;

import java.io.IOException;

public abstract class BaseJsonApiServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public final void init() throws ServletException {
        super.init();

        DependencyManager.getInjector().injectMembers(this);
    }


    protected final void writeJsonValueToResponse(HttpServletResponse resp, Object value) throws IOException {
        objectMapper.writeValue(resp.getWriter(), value);
    }

    protected final <T> T readDtoFromJsonRequest(HttpServletRequest req, Class<T> dtoClass) throws IOException {
        return objectMapper.readValue(req.getInputStream(), dtoClass);
    }


    protected PageRequestDto extractPageRequestDto(HttpServletRequest req) {
        return PageRequestDto.builder()
                .pageNumber(tryToExtractPositiveInt(req.getParameter("page_number")))
                .pageSize(tryToExtractPositiveInt(req.getParameter("page_size")))
                .playerNameFilterBy(req.getParameter("player_name"))
                .build();
    }

    protected Integer tryToExtractPositiveInt(String idString) {
        int id;

        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ValidationException("Incorrect parameter format!");
        }

        if (id <= 0) {
            throw new ValidationException("Missing or incorrect parameter format!");
        }

        return id;
    }

}
