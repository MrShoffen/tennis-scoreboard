package org.mrshoffen.dto.request;

public record MatchPageRequestDto(String pageNumber,
                                  String pageSize,
                                  String playerName) {
}
