package org.mrshoffen.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.dto.request.PageRequestDto;
import org.mrshoffen.dto.response.PageResponseDto;
import org.mrshoffen.service.FinishedMatchesPersistenceService;

import java.io.IOException;


@WebServlet(urlPatterns = "/api/matches", name = "MatchData")
public class MatchesServlet extends BaseHttpServlet {

    @Inject
    private FinishedMatchesPersistenceService matchService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PageRequestDto pageRequestDto = extractPageRequestDto(req);

        PageResponseDto responseDto  = matchService.findPageFilteredByName(pageRequestDto);

        writeJsonValueToResponse(resp, responseDto);
    }


}
