package org.mrshoffen.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
public class NewMatchRequestDto {
    String firstPlayer;
    String secondPlayer;

}
