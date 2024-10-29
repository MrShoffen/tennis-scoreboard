package org.mrshoffen.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
public final class PlayerResponseDto  extends EntityResponseDto {

    private String name;
    private Integer matchesPlayed;

}
