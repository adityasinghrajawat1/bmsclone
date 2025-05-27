package com.driver.bookMyShow.Dtos.ResponseDtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReturnFacilityDto {
    public String facilityId;
    public String name;
    public String logo;
    public Boolean isActive;
    @JsonIgnore
    public Boolean isInactive;
    @JsonIgnore
    public LocalDateTime updatedAt;
    @JsonIgnore
    public LocalDateTime createdAt;
}
