package com.driver.bookMyShow.Dtos.RequestDtos;

import com.driver.bookMyShow.Exceptions.RequestFailedException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CapacityEntryDto {
    private int diamond;
    private int gold;
    private int silver;

    public void validate() {
        if (diamond > 50 || gold > 50 || silver > 50)
            throw new RequestFailedException();
    }

}
