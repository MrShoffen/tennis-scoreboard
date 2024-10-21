package org.mrshoffen.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.dto.request.PlayerPageRequestDto;
import org.mrshoffen.dto.response.PageResponseDto;
import org.mrshoffen.service.PlayerService;

import java.io.IOException;

@WebServlet(urlPatterns = "/players-data", name = "PlayerData")
public class PlayersServlet extends BaseHttpServlet {
    @Inject
    private PlayerService playerService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pageNumber = req.getParameter("page_number");
        String pageSize = req.getParameter("page_size");
        String playerName = req.getParameter("player_name");

        PlayerPageRequestDto request = new PlayerPageRequestDto(pageNumber, pageSize, playerName);


        PageResponseDto responseDto  = playerService.findWithPaginationFilteredByName(request);


        writeJsonValueToResponse(resp, responseDto);
    }

    private int parsePageNumberParameter(String pageNumber) {
        return pageNumber == null || pageNumber.isEmpty()
                ? 1
                : Integer.parseInt(pageNumber.trim());
    }
}
