package org.mrshoffen.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
}
