package org.mrshoffen.entity.persistence;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "match")
public class Match  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player1")
    private Player firstPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player2")
    private Player secondPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner")
    private Player winner;
}
