package org.mrshoffen.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.dto.request.NewMatchRequestDto;
import org.mrshoffen.dto.request.PointScoreDto;
import org.mrshoffen.dto.response.score.OngoingMatchResponseDto;
import org.mrshoffen.service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/api/match-score")
public class MatchScoreServlet extends BaseJsonApiServlet {

    @Inject
    private OngoingMatchesService ongoingMatchesService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        OngoingMatchResponseDto currentMatch = ongoingMatchesService.getMatchById(uuid);


        writeJsonValueToResponse(resp, currentMatch);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var scoredPlayer = readDtoFromJsonRequest(req, PointScoreDto.class);
        UUID uuid = UUID.fromString(req.getParameter("uuid"));


        OngoingMatchResponseDto updatedMatch = ongoingMatchesService.updateMatch(uuid, scoredPlayer);

        if (updatedMatch.isEnded()) {
            ongoingMatchesService.saveMatch(uuid);

            resp.sendRedirect(req.getContextPath() + "/matches");

        }

        writeJsonValueToResponse(resp, updatedMatch);

    }
}
