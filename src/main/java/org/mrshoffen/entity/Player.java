package org.mrshoffen.entity;


import jakarta.persistence.*;
import lombok.*;
import org.mrshoffen.repository.PlayerRepository;
import org.mrshoffen.utils.DependencyManager;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

}
