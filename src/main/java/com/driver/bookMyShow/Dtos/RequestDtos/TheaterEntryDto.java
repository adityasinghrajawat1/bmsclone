package com.driver.bookMyShow.Dtos.RequestDtos;

import com.driver.bookMyShow.Exceptions.RequestFailedException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TheaterEntryDto {
    private String name;
    private String address;
    private List<String> facilityIds;

    public void validate() {
        if ((name == null || name.isEmpty()) || (address == null || address.isEmpty()) || (facilityIds.isEmpty() || facilityIds == null))
            throw new RequestFailedException();

        validate(name, address);
    }

    private void validate(String name, String address) {
        if (name.length() < 3)
            throw new RequestFailedException("name is too short!");

        if (address.length() > 50)
            throw new RequestFailedException("address is too long!");
    }
}
