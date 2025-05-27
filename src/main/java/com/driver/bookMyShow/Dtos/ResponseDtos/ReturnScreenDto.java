package com.driver.bookMyShow.Dtos.ResponseDtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnScreenDto {
    private String id;
    private String resolution;
    private Boolean isActive;

}
