package com.driver.bookMyShow.Dtos.RequestDtos;

import com.driver.bookMyShow.Enums.SeatType;
import com.driver.bookMyShow.Exceptions.RequestFailedException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowSeatEntryDTO {
    private String seatType;
    private Integer price;


    public void validate() {
        if ((seatType == null || seatType.isEmpty()) || (price < 50 || price > 2000))
            throw new RequestFailedException();

        SeatType.validate(seatType);
    }

}
