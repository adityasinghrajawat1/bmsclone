package com.driver.bookMyShow.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "CAPACITY")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance
@SQLDelete(sql = "UPDATE Capacity SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class Capacity extends AbstractPersistable {
    @Column(name = "no_of_diamond_seats")
    private int diamond;

    @Column(name = "no_of_gold_seats")
    private int gold;

    @Column(name = "no_of_silver_seats")
    private int silver;

//    @OneToOne(mappedBy = "capacity")
//    @JsonIgnoreProperties(value = "capacity")
//    private Screen screen;
}
