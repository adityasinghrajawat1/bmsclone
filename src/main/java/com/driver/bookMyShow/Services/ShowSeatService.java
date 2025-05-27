package com.driver.bookMyShow.Services;

import com.driver.bookMyShow.Dtos.RequestDtos.ShowSeatEntryDTO;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnShowSeatDto;
import com.driver.bookMyShow.Exceptions.*;
import com.driver.bookMyShow.Models.Show;
import com.driver.bookMyShow.Models.ShowSeat;
import com.driver.bookMyShow.Repositories.ShowRepository;
import com.driver.bookMyShow.Repositories.ShowSeatRepository;
import com.driver.bookMyShow.Transformers.ShowSeatTransformer;
import com.driver.bookMyShow.constant.Messages;
import com.driver.bookMyShow.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShowSeatService {
    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private ShowRepository showRepository;

    public String addSeatDetailsService(ShowSeatEntryDTO showSeatEntryDTO, String showId) {
        showSeatEntryDTO.validate();
        Optional<Show> optionalShow = showRepository.findById(showId);
        Show show = optionalShow
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.SHOW));

        Optional<ShowSeat> optionalShowSeatv1 = showSeatRepository.findByShowAndSeatType(show, showSeatEntryDTO.getSeatType());
        if (optionalShowSeatv1.isPresent())
            throw new AlreadyPresentException(showSeatEntryDTO.getSeatType() + Messages.ONE_TAB + Messages.ALREADY_PRESENT);

        ShowSeat showSeat = ShowSeatTransformer.showSeatDtoToShowSeat(showSeatEntryDTO);
        showSeat.setId(Utils.generateUUID(10));
        showSeat.setShow(show);
        showSeat.setPrice(showSeatEntryDTO.getPrice());
        showSeat.setIsActive(Messages.TRUE);
        showSeat.setDeleted(Messages.FALSE);
        showSeatRepository.save(showSeat);
        return Messages.SUCCESS;
    }

    public String updateSeatPriceService(ShowSeatEntryDTO showSeatEntryDTO, String showId) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.SHOW));

        Optional<ShowSeat> optionalShowSeatv1 = showSeatRepository.findByShowAndSeatType(show, showSeatEntryDTO.getSeatType());

        ShowSeat showSeat = optionalShowSeatv1
                .orElseThrow(() -> InvalidException.getExceptionWithDesc());

        showSeat.setPrice(showSeatEntryDTO.getPrice());
        showSeatRepository.save(showSeat);
        return "Price updated successfully";
    }


    public List<ReturnShowSeatDto> getAllSeatDetailsService() {
        List<ShowSeat> seatTypeV1List = showSeatRepository.findAll();
        if (seatTypeV1List.isEmpty())
            throw NotFoundException.getExceptionWithDesc(Messages.SEATS);

        List<ReturnShowSeatDto> returnShowSeatDtoList = seatTypeV1List.stream()
                .map(ShowSeatTransformer::showSeatToReturnShowSeatDto)
                .collect(Collectors.toList());
        return returnShowSeatDtoList;
    }
}
