package org.mrshoffen.dto.request;

public record PlayerPageRequestDto(String pageNumber,
                                  String pageSize,
                                  String playerName) {
}
