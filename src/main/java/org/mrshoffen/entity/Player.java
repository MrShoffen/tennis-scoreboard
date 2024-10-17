package org.mrshoffen.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mrshoffen.repository.MatchRepository;
import org.mrshoffen.repository.PlayerRepository;
import org.mrshoffen.utils.DependencyManager;
import org.mrshoffen.utils.HibernateUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "player")
public class Player implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    public static void main(String[] args) {


        var instance = DependencyManager.getInjector().getInstance(PlayerRepository.class);


        int pageSize = 5;
        long pageCount = Math.ceilDiv(instance.size(),pageSize);

        System.out.println(pageCount);

        for (int i = 0; i < pageCount; i++) {
            System.out.println("Page " + (i+1));
            instance.findWithPagination(i+1, pageSize).forEach(System.out::println);
        }


        /////

    }
}
