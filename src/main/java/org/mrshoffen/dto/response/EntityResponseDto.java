package org.mrshoffen.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
public abstract class EntityResponseDto {

    private Integer id;
}
