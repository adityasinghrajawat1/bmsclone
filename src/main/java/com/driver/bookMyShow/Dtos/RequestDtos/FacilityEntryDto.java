package com.driver.bookMyShow.Dtos.RequestDtos;

import com.driver.bookMyShow.Enums.FacilityType;
import com.driver.bookMyShow.Exceptions.RequestFailedException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FacilityEntryDto {
    String name;
    String logo;

    public void validate() {
        if ((name == null || name.isEmpty()) || (logo == null || logo.isEmpty()))
            throw new RequestFailedException();
        FacilityType.validate(name);
    }
}
