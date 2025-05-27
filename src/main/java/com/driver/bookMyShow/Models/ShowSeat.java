package com.driver.bookMyShow.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "SHOW_SEATS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance
@SQLDelete(sql = "UPDATE SHOW_SEATS SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class ShowSeat extends AbstractPersistable {
    @Column(name = "Seat_Type")
    private String seatType;

    @Column(name = "Price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "Show_Id")
    private Show show;
}
