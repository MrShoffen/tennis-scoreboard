package org.mrshoffen.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.dto.request.NewMatchRequestDto;
import org.mrshoffen.service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/api/new-match")
public class NewMatchServlet extends BaseJsonApiServlet {

    @Inject
    private OngoingMatchesService ongoingMatchesService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        NewMatchRequestDto newMatchRequestDto = readDtoFromJsonRequest(req, NewMatchRequestDto.class);

        UUID uuid = ongoingMatchesService.createMatch(newMatchRequestDto);

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
    }
}
