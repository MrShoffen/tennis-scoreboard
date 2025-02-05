package org.mrshoffen.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.dto.response.pageable.MatchResponseDto;
import org.mrshoffen.service.FinishedMatchesPersistenceService;

import java.io.IOException;

@WebServlet("/api/finished-match")
public class FinishedMatchServlet extends BaseJsonApiServlet {

    @Inject
    private FinishedMatchesPersistenceService matchService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idString = req.getParameter("id");

        Integer id = tryToExtractIntParameter(idString);

        MatchResponseDto match = matchService.findById(id);

        writeJsonValueToResponse(resp, match);

    }



}
