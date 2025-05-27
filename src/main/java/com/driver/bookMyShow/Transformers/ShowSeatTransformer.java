package com.driver.bookMyShow.Transformers;

import com.driver.bookMyShow.Dtos.RequestDtos.ShowSeatEntryDTO;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnShowSeatDto;
import com.driver.bookMyShow.Models.ShowSeat;

public class ShowSeatTransformer {
    public static ShowSeat showSeatDtoToShowSeat(ShowSeatEntryDTO showSeatEntryDTO) {
        ShowSeat showSeat = ShowSeat.builder()
                .price(showSeatEntryDTO.getPrice())
                .seatType(showSeatEntryDTO.getSeatType())
                .build();
        return showSeat;
    }

    public static ReturnShowSeatDto showSeatToReturnShowSeatDto(ShowSeat showSeat) {
        ReturnShowSeatDto returnShowSeatDto = ReturnShowSeatDto.builder()
                .seatType(showSeat.getSeatType())
                .price(showSeat.getPrice())
                .build();
        return returnShowSeatDto;
    }
}
