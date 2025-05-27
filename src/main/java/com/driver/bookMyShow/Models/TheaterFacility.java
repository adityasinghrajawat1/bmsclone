package com.driver.bookMyShow.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "THEATER_FACILITY")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance
@SQLDelete(sql = "UPDATE THEATER_FACILITY SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class TheaterFacility extends AbstractPersistable {

    @JsonIgnoreProperties(value = "theaterFacilityList")
    @ManyToOne
    @JoinColumn(name = "Theater_Id")
    private Theater theater;

    @ManyToOne
    @JoinColumn(name = "Facility_id")
    private Facility facility;

}
