package org.mrshoffen.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.dto.response.MatchPageResponseDto;
import org.mrshoffen.dto.request.MatchPageRequestDto;
import org.mrshoffen.service.MatchService;

import java.io.IOException;


@WebServlet(urlPatterns = "/matches-data", name = "MatchData")
public class MatchesServlet extends BaseHttpServlet {


    @Inject
    private MatchService matchService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ///
        String pageNumber = req.getParameter("page_number");
        String pageSize = req.getParameter("page_size");
        String playerName = req.getParameter("player_name");

        MatchPageRequestDto requestDto = new MatchPageRequestDto(pageNumber,pageSize, playerName );


        MatchPageResponseDto responseDto  = matchService.findMatchesWithPaginationFilteredByName(requestDto);



        writeJsonValueToResponse(resp, responseDto);
    }

    private int parsePageNumberParameter(String pageNumber) {
        return pageNumber == null || pageNumber.isEmpty()
                ? 1
                : Integer.parseInt(pageNumber.trim());
    }


}
