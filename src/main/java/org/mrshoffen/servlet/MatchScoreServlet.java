package org.mrshoffen.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.dto.request.PointScoreDto;
import org.mrshoffen.dto.response.pageable.MatchResponseDto;
import org.mrshoffen.dto.response.score.OngoingMatchResponseDto;
import org.mrshoffen.exception.ValidationException;
import org.mrshoffen.service.FinishedMatchesPersistenceService;
import org.mrshoffen.service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/api/match-score")
public class MatchScoreServlet extends BaseJsonApiServlet {

    @Inject
    private OngoingMatchesService ongoingMatchesService;

    @Inject
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UUID uuid;
        try {
            uuid = UUID.fromString(req.getParameter("uuid"));
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid UUID!");
        }

        OngoingMatchResponseDto currentMatch = ongoingMatchesService.getMatchById(uuid);


        writeJsonValueToResponse(resp, currentMatch);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var scoredPlayer = readDtoFromJsonRequest(req, PointScoreDto.class);
        UUID uuid = UUID.fromString(req.getParameter("uuid"));


        OngoingMatchResponseDto updatedMatch = ongoingMatchesService.updateMatch(uuid, scoredPlayer);

        if (updatedMatch.isEnded()) {
            ongoingMatchesService.removeMatchById(uuid);

            MatchResponseDto matchResponseDto
                    = finishedMatchesPersistenceService.saveFinishedMatch(updatedMatch);

            resp.sendRedirect(req.getContextPath() + "/match?id=" + matchResponseDto.getId());
        }

        writeJsonValueToResponse(resp, updatedMatch);

    }
}
