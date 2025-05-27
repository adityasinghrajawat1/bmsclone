package com.driver.bookMyShow.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "SeatingArrangement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance
@SQLDelete(sql = "UPDATE SeatingArrangement SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class SeatingArrangement extends AbstractPersistable {
    @Column(name = "Seat_Name")
    private String seatName;

    @Column(name = "Seat_Type") // DIAMOND, GOLD, SILVER
    private String seatType;

//    @JsonIgnoreProperties(value = "seatingArrangementList")
//    @ManyToOne
//    @JoinColumn(name = "screen_id")
//    private Screen screen;
}
