package org.mrshoffen.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mrshoffen.dto.response.MatchResponseDto;
import org.mrshoffen.service.MatchService;

import java.io.IOException;
import java.util.List;


@WebServlet("/matches")
public class MatchesServlet extends BaseHttpServlet {


    @Inject
    private MatchService matchService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pageNumber = req.getParameter("page_number");
        String playerName = req.getParameter("filter_by_player_name");

        int page = parsePageNumberParameter(pageNumber);


        List<MatchResponseDto> matchesWithPagination = matchService.findMatchesWithPagination(page, 5);

        System.out.println();
    }

    private int parsePageNumberParameter(String pageNumber) {
        return pageNumber == null || pageNumber.isEmpty()
                ? 1
                : Integer.parseInt(pageNumber.trim());
    }


}
