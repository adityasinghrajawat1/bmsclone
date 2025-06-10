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

    @Column(name = "ADULT")
    private boolean adult;

    @Column(name = "BACKDROP_PATH")
    private String backdropPath;

    @ElementCollection
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "genre_id")
    private List<Integer> genreIds;

    @Column(name = "ORIGINAL_LANGUAGE")
    private String originalLanguage;

    @Column(name = "ORIGINAL_TITLE")
    private String originalTitle;

    @Column(name = "OVERVIEW",columnDefinition = "LONGTEXT")
    private String overview;

    @Column(name = "POPULARITY")
    private double popularity;

    @Column(name = "POSTER_PATH")
    private String posterPath;

    @Column(name = "RELEASE_DATE")
    private String releaseDate;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "VIDEO")
    private boolean video;

    @Column(name = "VOTE_AVERAGE")
    private double voteAverage;

    @Column(name = "VOTE_COUNT")
    private int voteCount;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Show> shows;
}
