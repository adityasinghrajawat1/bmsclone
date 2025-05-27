package com.driver.bookMyShow.Transformers;

import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnSeatingArrangementDto;
import com.driver.bookMyShow.Models.SeatingArrangement;

public class SeatingArrangementTransformer {
    public static ReturnSeatingArrangementDto SeatingArrangementToReturnSeatingArrangementDto(SeatingArrangement seatingArrangement) {
        ReturnSeatingArrangementDto returnSeatingArrangementDto = ReturnSeatingArrangementDto.builder()
                .seatType(seatingArrangement.getSeatType())
                .seatName(seatingArrangement.getSeatName())
                .build();
        return returnSeatingArrangementDto;
    }
}
