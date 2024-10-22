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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ///
        String pageNumber = req.getParameter("page_number");
        String pageSize = req.getParameter("page_size");
        String playerName = req.getParameter("player_name");

        PageRequestDto requestDto = new PageRequestDto(pageNumber,pageSize, playerName );


        PageResponseDto responseDto  = matchService.findMatchesWithPaginationFilteredByName(requestDto);



        writeJsonValueToResponse(resp, responseDto);
    }

    private int parsePageNumberParameter(String pageNumber) {
        return pageNumber == null || pageNumber.isEmpty()
                ? 1
                : Integer.parseInt(pageNumber.trim());
    }


}
