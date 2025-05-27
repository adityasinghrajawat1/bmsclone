package com.driver.bookMyShow.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "Screen")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance
@SQLDelete(sql = "UPDATE SCREEN SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Screen extends AbstractPersistable {
    @Column(name = "resolution")
    private String resolution;

    @ManyToOne
    @JoinColumn(name = "Theater_Id")
    private Theater theater;

//    @JsonIgnoreProperties(value = "screen")
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "capacity_id",referencedColumnName = "id")
//    private Capacity capacity;

//    @JsonIgnoreProperties(value = "screen")
//    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
//    private List<SeatingArrangement> seatingArrangementList;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
    private List<Show> shows;
}
