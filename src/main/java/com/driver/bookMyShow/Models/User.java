package com.driver.bookMyShow.Models;

import com.driver.bookMyShow.Enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance
@SQLDelete(sql = "UPDATE USERS SET IS_DELETED = 1 WHERE id = ?")
@Where(clause = "IS_DELETED = false")
public class User extends AbstractPersistable {



    private String name;

    private Integer age;

    private String address;

    private String gender; //enum

    private String mobileNo;

    private String emailId;

}
