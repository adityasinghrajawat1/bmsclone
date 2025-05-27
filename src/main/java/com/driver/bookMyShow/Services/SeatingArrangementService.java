package com.driver.bookMyShow.Services;

import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnSeatingArrangementDto;
import com.driver.bookMyShow.Exceptions.*;
import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.SeatingArrangement;
import com.driver.bookMyShow.Repositories.ScreenRepository;
import com.driver.bookMyShow.Repositories.SeatingArrangementRepository;
import com.driver.bookMyShow.Transformers.SeatingArrangementTransformer;
import com.driver.bookMyShow.constant.Messages;
import com.driver.bookMyShow.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatingArrangementService {
    @Autowired
    private SeatingArrangementRepository seatingArrangementRepository;

    @Autowired
    private ScreenRepository screenRepository;
//
//    public List<ReturnSeatingArrangementDto> generateSeatingFromCapacityService(String screenId)
//    {
//        if(!screenRepository.existsById(screenId))
//            throw new NotFoundException(Messages.SCREEN+Messages.ONE_TAB+Messages.NOT_FOUND+Messages.DOT);
//
//        Screen screen = screenRepository.findById(screenId).get();
//        if(!screen.getSeatingArrangementList().isEmpty())
//            throw new AlreadyPresentException(Messages.SEATS+Messages.ONE_TAB+Messages.ALREADY_PRESENT+Messages.DOT);
//
//        List<SeatingArrangement> seats = new ArrayList<>();
//        //diamond seats
//        for(int i = 1; i <= screen.getCapacity().getDiamond(); i++)
//        {
//            seats.add(SeatingArrangement.builder()
//                   .id(Utils.generateUUID(10))
//                    .seatName("D"+i)
//                    .seatType("DIAMOND")
//                    .isActive(Messages.TRUE)
//                    .deleted(Messages.FALSE)
//                    .screen(screen)
//                    .build());
//        }
//
//        //gold seats
//        for(int i = 1; i<= screen.getCapacity().getGold(); i++)
//        {
//            seats.add(SeatingArrangement.builder()
//                    .id(Utils.generateUUID(10))
//                    .seatName("G"+i)
//                    .seatType("GOLD")
//                    .isActive(Messages.TRUE)
//                    .deleted(Messages.FALSE)
//                    .screen(screen)
//                    .build());
//        }
//
//        //silver seats
//        for(int i = 1; i<= screen.getCapacity().getSilver(); i++)
//        {
//            seats.add(SeatingArrangement.builder()
//                    .id(Utils.generateUUID(10))
//                    .seatName("S"+i)
//                    .seatType("SILVER")
//                    .isActive(Messages.TRUE)
//                    .deleted(Messages.FALSE)
//                    .screen(screen)
//                    .build());
//        }
//
//        List<SeatingArrangement> listAfterAddingSeats = seatingArrangementRepository.saveAll(seats);
//
//        List<ReturnSeatingArrangementDto> returnSeatingArrangementDtosList = listAfterAddingSeats.stream()
//                .map(SeatingArrangementTransformer::SeatingArrangementToReturnSeatingArrangementDto)
//                .collect(Collectors.toList());
//        return returnSeatingArrangementDtosList;
//    }
//
//
//    public List<ReturnSeatingArrangementDto> getAllGeneratedSeatsService(String screenId)
//    {
//       Optional<Screen> optionalScreenv1 = screenRepository.findById(screenId);
//        if(optionalScreenv1.isEmpty())
//            throw new NotFoundException(Messages.SCREEN+Messages.ONE_TAB+Messages.NOT_FOUND+Messages.DOT);
//
//        Screen screen = optionalScreenv1.get();
//        Optional<List<SeatingArrangement>> optionalSeatingArrangementV1 = seatingArrangementRepository.findByScreen(screen);
//         if(optionalSeatingArrangementV1.isEmpty())
//             throw new AlreadyPresentException(Messages.SEATS+Messages.ONE_TAB+Messages.ALREADY_PRESENT+Messages.DOT);
//
//         List<SeatingArrangement> seatingArrangementList = optionalSeatingArrangementV1.get();
//         List<ReturnSeatingArrangementDto> returnSeatingArrangementDtoList = seatingArrangementList.stream()
//                 .map(SeatingArrangementTransformer::SeatingArrangementToReturnSeatingArrangementDto)
//                 .collect(Collectors.toList());
//         return returnSeatingArrangementDtoList;
//    }
}
