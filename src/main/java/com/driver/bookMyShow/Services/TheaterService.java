package com.driver.bookMyShow.Services;

import com.driver.bookMyShow.Dtos.RequestDtos.ScreenEntryDto;
import com.driver.bookMyShow.Dtos.RequestDtos.TheaterEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnScreenDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterDetailsDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterFacilityDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterDto;
import com.driver.bookMyShow.Exceptions.AlreadyPresentException;
import com.driver.bookMyShow.Exceptions.NotFoundException;
import com.driver.bookMyShow.Models.Facility;
import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.TheaterFacility;
import com.driver.bookMyShow.Models.Theater;
import com.driver.bookMyShow.Repositories.TheaterRepository;
import com.driver.bookMyShow.Transformers.ScreenTransformer;
import com.driver.bookMyShow.Transformers.TheaterTransformer;
import com.driver.bookMyShow.constant.Messages;
import com.driver.bookMyShow.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TheaterService {
    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ScreenService screenService;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private TheaterFacilityService theaterFacilityService;

    public ReturnTheaterDto addTheaterService(TheaterEntryDto theaterEntryDto) {
        if (theaterRepository.existsByAddress(theaterEntryDto.getAddress()))
            throw AlreadyPresentException.getExceptionWithDesc(Messages.THEATER);
        theaterEntryDto.validate();

        List<String> facilityIds = theaterEntryDto.getFacilityIds();
        for (String str : facilityIds) {
            if (!facilityService.existsById(str))
                throw new NotFoundException(Messages.FACILITY + " with id " + str + Messages.ONE_TAB + Messages.NOT_FOUND + Messages.DOT);
            if (!facilityService.existsByIdAndIsActiveTrue(str))
                throw new NotFoundException(Messages.FACILITY + " with id " + str + " is not provided by BookMyShow.");
        }
        List<Facility> facilityList = facilityService.findAllByIdInAndIsActiveTrue(facilityIds);
        List<Facility> uniqueFacilities = facilityList.stream().distinct().toList();

        Theater theater = Theater.builder()
                .id(Utils.generateUUID(10))
                .name(theaterEntryDto.getName())
                .address(theaterEntryDto.getAddress())
                .isActive(Messages.TRUE)
                .deleted(Messages.FALSE)
                .createdAt(LocalDateTime.now())
                .build();

        List<TheaterFacility> facilityLinks = uniqueFacilities.stream().map(facility -> {
            TheaterFacility theaterFacility = TheaterFacility.builder()
                    .id(Utils.generateUUID(10))
                    .theater(theater)
                    .facility(facility)
                    .isActive(Messages.TRUE)
                    .deleted(Messages.FALSE)
                    .createdAt(LocalDateTime.now())
                    .build();
            return theaterFacility;
        }).toList();
        theater.setTheaterFacilityList(facilityLinks);
        theaterRepository.save(theater);
        return TheaterTransformer.TheaterToReturnTheaterDto(theater);
    }

    public ReturnTheaterFacilityDto theaterFacilityOnAndOffService(String theaterId, String facilityId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.THEATER));
        boolean facilityFound = theater.getTheaterFacilityList().stream()
                .anyMatch(f -> f.getFacility().getId().equals(facilityId));
        if (Boolean.FALSE.equals(facilityFound))
            throw NotFoundException.getExceptionWithDesc(Messages.FACILITY);
        TheaterFacility theaterFacility = theater.getTheaterFacilityList().stream()
                .filter(f -> f.getFacility().getId().equals(facilityId))
                .toList().get(0);
        theaterFacility.setIsActive(theaterFacility.getIsActive() ? Boolean.FALSE : Boolean.TRUE);
        return theaterFacilityService.addTheaterFacility(theaterFacility);
    }

    public List<ReturnTheaterDetailsDto> getAllTheaterDetailsService() {
        List<Theater> theaterList = theaterRepository.findAll();
        if (theaterList.isEmpty())
            throw NotFoundException.getExceptionWithDesc(Messages.THEATER);
        return theaterList.stream().map(theater -> {
            return TheaterTransformer.TheaterToReturnTheaterDetailsDto(theater);
        }).toList();
    }

    public ReturnTheaterDto getTheaterDetailsByIdService(String id) {
        Optional<Theater> optionalTheater = theaterRepository.findById(id);
        if (optionalTheater.isPresent()) {
            Theater theater = optionalTheater.get();

            List<TheaterFacility> theaterFacilityList = theater.getTheaterFacilityList().stream().toList();
            theater.setTheaterFacilityList(theaterFacilityList);
            return TheaterTransformer.TheaterToReturnTheaterDto(theater);
        } else
            throw NotFoundException.getExceptionWithDesc(Messages.THEATER);
    }

    @Transactional
    public ReturnTheaterDto updateTheaterDetailsByIdService(String theaterId, TheaterEntryDto theaterEntryDto) {
        theaterEntryDto.validate();
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.THEATER));

        Optional<Theater> existing = theaterRepository.findByAddress(theaterEntryDto.getAddress());
        if (existing.isPresent() && !existing.get().getId().equals(theaterId)) {
            throw AlreadyPresentException.getExceptionWithDesc(Messages.THEATER);
        }

        theater.setName(theaterEntryDto.getName());
        theater.setAddress(theaterEntryDto.getAddress());
        List<String> facilityIds = theaterEntryDto.getFacilityIds();

        for (String str : facilityIds) {
            if (!facilityService.existsById(str))
                throw new NotFoundException(Messages.FACILITY + " with id " + str + Messages.ONE_TAB + Messages.NOT_FOUND + Messages.DOT);
            if (!facilityService.existsByIdAndIsActiveTrue(str))
                throw new NotFoundException(Messages.FACILITY + " with id " + str + " is not provided by BookMyShow.");
        }

        List<Facility> facilityList = facilityService.findAllByIdInAndIsActiveTrue(facilityIds);
        List<Facility> uniqueFacilities = facilityList.stream().distinct().toList();
        theaterFacilityService.deleteByTheater(theater);

        List<TheaterFacility> facilityLinks = uniqueFacilities.stream().map(facility -> {
            return TheaterFacility.builder()
                    .id(Utils.generateUUID(10))
                    .theater(theater)
                    .facility(facility)
                    .isActive(Messages.TRUE)
                    .deleted(Messages.FALSE)
                    .createdAt(LocalDateTime.now())
                    .build();
        }).collect(Collectors.toList());

        theater.setTheaterFacilityList(facilityLinks);
        theaterRepository.save(theater);
        return TheaterTransformer.TheaterToReturnTheaterDto(theater);
    }


    public String deleteTheaterService(String theaterId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.THEATER));
        theaterRepository.deleteById(theaterId);

        List<TheaterFacility> tfList = theater.getTheaterFacilityList();
        tfList.forEach(tf -> {
            theaterFacilityService.deleteById(tf.getId());
        });
        return Messages.THEATER + Messages.ONE_TAB + Messages.DELETED + Messages.DOT;
    }


    public ReturnScreenDto saveTheaterScreen(ScreenEntryDto screenEntryDto, String theaterId) {
        screenEntryDto.validate();
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.THEATER));
        Screen screen = ScreenTransformer.ScreenEntryDtoToScreen(screenEntryDto);
        screen.setId(Utils.generateUUID(10));
        screen.setTheater(theater);
        return ScreenTransformer.ScreenToReturnScreenDto(screenService.insertTheaterScreen(screen));
    }

    public List<ReturnScreenDto> getTheaterScreens(String theaterId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.THEATER));

        List<Screen> screens = theater.getScreenList().stream()
                .filter(s -> s.getTheater().getId().equals(theaterId))
                .toList();
        if (screens.isEmpty())
            throw NotFoundException.getExceptionWithDesc(Messages.SCREEN);
        List<ReturnScreenDto> returnScreenDtos = screens.stream()
                .map(s -> ScreenTransformer.ScreenToReturnScreenDto(s))
                .toList();
        return returnScreenDtos;
    }

    public String deleteTheaterScreen(String theaterId, String screenId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.THEATER));
        screenService.existsByIdAndTheater(screenId, theater);
        screenService.deleteById(screenId);
        return Messages.SCREEN + Messages.ONE_TAB + Messages.DELETED + Messages.DOT;
    }

    public ReturnScreenDto theaterScreenActiveInactive(String theaterId, String screenId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.THEATER));

        Screen screen = theater.getScreenList().stream()
                .filter(s -> s.getId().equals(screenId))
                .findFirst()
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.SCREEN));
        return screenService.screenActiveInactive(screen);
    }
}
