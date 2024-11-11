package org.mrshoffen.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.dto.request.PageRequestDto;
import org.mrshoffen.dto.response.pageable.PageResponseDto;
import org.mrshoffen.service.PlayersPersistenceService;

import java.io.IOException;

@WebServlet("/api/players")
public class PlayersServlet extends BaseJsonApiServlet {

    @Inject
    private PlayersPersistenceService playerService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PageRequestDto request = extractPageRequestDto(req);

        PageResponseDto responseDto = playerService.findPageFilteredByName(request);

        writeJsonValueToResponse(resp, responseDto);
    }

}
