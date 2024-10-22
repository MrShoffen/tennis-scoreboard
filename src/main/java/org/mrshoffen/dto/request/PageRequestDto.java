package org.mrshoffen.dto.request;

public record PageRequestDto(String pageNumber,
                             String pageSize,
                             String playerName) {
}
