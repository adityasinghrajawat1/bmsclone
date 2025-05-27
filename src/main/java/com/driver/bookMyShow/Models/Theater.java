package com.driver.bookMyShow.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "THEATERS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance
@SQLDelete(sql = "UPDATE THEATERS SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class Theater extends AbstractPersistable {
    @Column(name = "Name")
    private String name;

    @Column(unique = true, name = "Address")
    private String address;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Screen> screenList;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<TheaterFacility> theaterFacilityList;
}
