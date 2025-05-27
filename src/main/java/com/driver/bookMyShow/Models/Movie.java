package com.driver.bookMyShow.Models;

import com.driver.bookMyShow.Enums.Genre;
import com.driver.bookMyShow.Enums.Language;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "MOVIES")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance
@SQLDelete(sql = "UPDATE Movies SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class Movie extends AbstractPersistable {

    private String movieName;

    private Integer duration;

//    private Double rating;
//
//    private Date releaseDate;
//
//    private List<String> genre; //enum
//
//    private List<String> language; //enum

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Show> shows;
}
