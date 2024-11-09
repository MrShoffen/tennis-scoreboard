package org.mrshoffen.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.dto.response.ErrorResponseDto;
import org.mrshoffen.exception.EntityNotFoundException;
import org.mrshoffen.exception.ValidationException;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@WebFilter("/*")
public class ExceptionFilter extends HttpFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException {
        try {
            chain.doFilter(req, res);
        } catch (Exception exception) {
            int responseCode =
                    switch (exception) {
                        case EntityNotFoundException _,
                             ValidationException _ -> SC_BAD_REQUEST;
                        default -> SC_INTERNAL_SERVER_ERROR;
                    };

            res.setStatus(responseCode);

            objectMapper.writeValue(res.getOutputStream(), ErrorResponseDto.of(exception.getMessage()));
        }

    }


}
