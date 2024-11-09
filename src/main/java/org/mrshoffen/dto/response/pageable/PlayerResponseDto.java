package org.mrshoffen.dto.response.pageable;

import lombok.Data;


@Data
public final class PlayerResponseDto {

    private Integer id;
    private String name;
    private Integer matchesPlayed;
    private Integer matchesWon;

}
