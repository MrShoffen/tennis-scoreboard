package org.mrshoffen.dto.response.score;


import lombok.*;
import org.mrshoffen.entity.Player;

import java.util.Arrays;

@Data
public class OngoingMatchResponseDto {

     private String firstPlayer;
     private String secondPlayer;

     private Integer [] firstPlayerSets ;
     private Integer [] secondPlayerSets ;

     private Integer firstPlayerCurrentPoints ;
     private Integer secondPlayerCurrentPoints ;

}
