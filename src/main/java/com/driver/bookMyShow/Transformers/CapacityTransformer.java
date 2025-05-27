package com.driver.bookMyShow.Transformers;

import com.driver.bookMyShow.Dtos.RequestDtos.CapacityEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnCapacityDto;
import com.driver.bookMyShow.Models.Capacity;

public class CapacityTransformer {
    public static Capacity CapacityEntryDtoToCapacity(CapacityEntryDto capacityEntryDto) {
        Capacity capacity = Capacity.builder()
                .diamond(capacityEntryDto.getDiamond())
                .gold(capacityEntryDto.getGold())
                .silver(capacityEntryDto.getSilver())
                .build();
        return capacity;
    }

    public static ReturnCapacityDto CapacityDtoToReturnCapacityDto(Capacity capacity) {
        ReturnCapacityDto returnCapacityDto = ReturnCapacityDto.builder()
                .diamond(capacity.getDiamond())
                .gold(capacity.getGold())
                .silver(capacity.getSilver())
                .build();
        return returnCapacityDto;
    }
}
