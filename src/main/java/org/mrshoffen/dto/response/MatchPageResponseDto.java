package org.mrshoffen.dto.response;

import java.util.List;

public record MatchPageResponseDto (List<MatchResponseDto> matches,
                                    int totalItems,
                                    int totalPages){
}
